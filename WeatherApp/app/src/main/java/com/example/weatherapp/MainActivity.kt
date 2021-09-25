package com.example.weatherapp

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.app.ActivityCompat
import com.squareup.picasso.Picasso
import org.json.JSONObject
import java.net.URL
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import android.graphics.drawable.ColorDrawable
import android.view.Window
import androidx.appcompat.app.ActionBar
import androidx.core.content.ContextCompat

import android.view.WindowManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.adapter.WeatherAdapter
import com.example.weatherapp.model.Weather
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {

    private val lang = "vi"
    private val appID = "d90dac7e23c546bd90d24944212509"

    // Default location is Ha Noi
    private var lat = "21.027763"
    private var lon = "105.834160"

    private lateinit var listOnDay: ArrayList<Weather>
    private lateinit var adapter: WeatherAdapter
    private lateinit var rcvWeather: RecyclerView

    private lateinit var mainContainer: LinearLayout
    private lateinit var weatherIcon: ImageView
    private lateinit var txtLocation: TextView
    private lateinit var txtUpdateAt: TextView
    private lateinit var txtStatus: TextView
    private lateinit var txtTemp: TextView
    private lateinit var txtWind: TextView
    private lateinit var txtPressure: TextView
    private lateinit var txtHumidity: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var errorMessage: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkPermission()
        addControls()
    }

    private fun checkPermission() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                100
            )
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
                101
            )
        }
    }

    private fun addControls() {
        mainContainer = findViewById(R.id.mainContainer)
        weatherIcon = findViewById(R.id.weatherIcon)
        txtLocation = findViewById(R.id.txtLocation)
        txtUpdateAt = findViewById(R.id.txtUpdateAt)
        txtStatus = findViewById(R.id.txtStatus)
        txtTemp = findViewById(R.id.txtTemp)
        txtWind = findViewById(R.id.wind)
        txtPressure = findViewById(R.id.pressure)
        txtHumidity = findViewById(R.id.humidity)
        progressBar = findViewById(R.id.progressBar)
        errorMessage = findViewById(R.id.errorMessage)

        listOnDay = ArrayList()
        rcvWeather = findViewById(R.id.rcvWeather)
        adapter = WeatherAdapter(listOnDay)
        rcvWeather.adapter = adapter

        OverScrollDecoratorHelper.setUpOverScroll(
            rcvWeather,
            OverScrollDecoratorHelper.ORIENTATION_HORIZONTAL
        )

        getMyLocation()
    }

    var fusedLocationProviderClient: FusedLocationProviderClient? = null

    private fun getMyLocation() {
        checkPermission()

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        val task: Task<Location> = fusedLocationProviderClient!!.lastLocation
        task.addOnSuccessListener(OnSuccessListener<Location?> { location ->
            if (location != null) {
                lon = location.longitude.toString()
                lat = location.latitude.toString()

                WeatherTask().execute()
            }
        })
    }

    inner class WeatherTask : AsyncTask<String, Void, String>() {

        override fun onPreExecute() {
            super.onPreExecute()
            progressBar.visibility = View.VISIBLE
            mainContainer.visibility = View.GONE
            errorMessage.visibility = View.GONE
            listOnDay.clear()
        }

        @SuppressLint("SetTextI18n")
        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)

            try {
                val obj = JSONObject(result)
                val location = obj.getJSONObject("location")
                val strLocation = location.getString("name") + ", " + location.getString("country")

                val current = obj.getJSONObject("current")
                var tempValue: String =
                    DecimalFormat("#.#").format(current.getString("temp_c").toFloat())
                tempValue = tempValue.replace(",", ".")

                val condition = current.getJSONObject("condition")
                val status = condition.getString("text")
                val icon = condition.getString("icon").replace("64x64", "128x128")
                    .replace("//", "https://")

                val forecast = obj.getJSONObject("forecast")
                val forecastday = forecast.getJSONArray("forecastday").getJSONObject(0)
                val hourArray = forecastday.getJSONArray("hour")
                var scrollTo = 0
                val sdf = SimpleDateFormat("HH aa", Locale.ENGLISH)
                for (i: Int in 0 until hourArray.length()) {
                    val item = hourArray.getJSONObject(i)
                    val weather = Weather()
                    weather.time = sdf.format(Date(item.getLong("time_epoch") * 1000))
                    weather.status = item.getJSONObject("condition").getString("text")
                    weather.icon = item.getJSONObject("condition").getString("icon")
                        .replace("//", "https://")
                    listOnDay.add(weather)

                    if (current.getLong("last_updated_epoch") > item.getLong("time_epoch")) {
                        scrollTo = i
                    }
                }
                adapter.notifyDataSetChanged()
                rcvWeather.smoothScrollToPosition(scrollTo)

                // set value into view
                txtUpdateAt.text = "Update at: " + SimpleDateFormat(
                    "HH:mm aa",
                    Locale.ENGLISH
                ).format(current.getLong("last_updated_epoch") * 1000)
                txtStatus.text = status.capitalize()
                Picasso.get()
                    .load(icon)
                    .into(weatherIcon)
                txtTemp.text = "$tempValue°C"
                txtWind.text = current.getString("wind_kph")
                txtPressure.text = current.getString("pressure_in")
                txtHumidity.text = current.getString("humidity")
                txtLocation.text = strLocation

                // set up back ground day/night
//                val isDay = current.getInt("is_day")
//                val window: Window = this@MainActivity.window
//                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
//                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
//                if (isDay == 1) {
//                    window.statusBarColor = ContextCompat.getColor(
//                        this@MainActivity,
//                        R.color.gradient_end
//                    )
//                    findViewById<LinearLayout>(R.id.rootContainer).setBackgroundResource(R.drawable.bg_gradient)
//                } else {
//                    window.statusBarColor = ContextCompat.getColor(
//                        this@MainActivity,
//                        R.color.gradient_night_start
//                    )
//                    findViewById<LinearLayout>(R.id.rootContainer).setBackgroundResource(R.drawable.bg_night_gradient)
//                }
                progressBar.visibility = View.GONE
                mainContainer.visibility = View.VISIBLE
            } catch (e: Exception) {
                Log.e("ERROR", e.toString())
                progressBar.visibility = View.GONE
                errorMessage.visibility = View.VISIBLE
            }
        }

        override fun doInBackground(vararg p0: String?): String? {
            var result: String? = try {
                URL("https://api.weatherapi.com/v1/forecast.json?key=$appID&q=$lat,$lon&days=1&aqi=yes&alerts=yes&lang=vi").readText(
                    Charsets.UTF_8
                )
            } catch (e: Exception) {
                null
            }
            return result
        }

    }

}
