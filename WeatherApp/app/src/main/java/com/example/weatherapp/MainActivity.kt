package com.example.weatherapp

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
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

class MainActivity : AppCompatActivity() {

    private val appID = "9d072d3e64c6c4646c32d94bcd9f25ac"
    private val author = "huukhuong"

    // Default location is Ha Noi
    private var lon = "105.834160"
    private var lat = "21.027763"

    private lateinit var mainContainer: LinearLayout
    private lateinit var weatherIcon: ImageView
    private lateinit var txtLocation: TextView
    private lateinit var txtUpdateAt: TextView
    private lateinit var txtStatus: TextView
    private lateinit var txtTemp: TextView
    private lateinit var txtMinTemp: TextView
    private lateinit var txtMaxTemp: TextView
    private lateinit var txtSunrise: TextView
    private lateinit var txtSunset: TextView
    private lateinit var txtWind: TextView
    private lateinit var txtPressure: TextView
    private lateinit var txtHumidity: TextView
    private lateinit var createdBy: TextView
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
        txtMinTemp = findViewById(R.id.txtMinTemp)
        txtMaxTemp = findViewById(R.id.txtMaxTemp)
        txtSunrise = findViewById(R.id.sunrise)
        txtSunset = findViewById(R.id.sunset)
        txtWind = findViewById(R.id.wind)
        txtPressure = findViewById(R.id.pressure)
        txtHumidity = findViewById(R.id.humidity)
        createdBy = findViewById(R.id.createdBy)
        progressBar = findViewById(R.id.progressBar)
        errorMessage = findViewById(R.id.errorMessage)

        createdBy.text = author
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
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)

            try {
                val jsonObj = JSONObject(result)
                val main = jsonObj.getJSONObject("main")
                val sys = jsonObj.getJSONObject("sys")
                val wind = jsonObj.getJSONObject("wind")
                val weather = jsonObj.getJSONArray("weather").getJSONObject(0)
                val updateAt = jsonObj.getLong("dt")
                val iconURL =
                    "https://openweathermap.org/img/wn/" + weather.getString("icon") + "@4x.png"
                val strUpdateAt =
                    "Update at: " + SimpleDateFormat("dd/MM/yyyy hh:mm:ss").format(Date(updateAt * 1000))
                val temp = main.getDouble("temp") - 273.15
                val minTemp = main.getDouble("temp_min") - 273.15
                val maxTemp = main.getDouble("temp_max") - 273.15
                val pressure = main.getString("pressure")
                val humidity = main.getString("humidity")
                val sunrise = sys.getLong("sunrise")
                val sunset = sys.getLong("sunset")
                val windSpeed = wind.getString("speed")
                val weatherDescription = weather.getString("description")
                val location = jsonObj.getString("name") + ", " + sys.getString("country")

                Picasso.get()
                    .load(iconURL)
                    .into(weatherIcon)

                txtLocation.text = location
                txtUpdateAt.text = strUpdateAt
                txtStatus.text = weatherDescription.capitalize()

                val dcf = DecimalFormat("#.#")
                txtTemp.text = "${dcf.format(temp)}°C"
                txtMinTemp.text = "${dcf.format(minTemp)}°C"
                txtMaxTemp.text = "${dcf.format(maxTemp)}°C"

                val sdf = SimpleDateFormat("hh:mm aa")
                txtSunrise.text = sdf.format(sunrise * 1000)
                txtSunset.text = sdf.format(sunset * 1000)
                txtWind.text = windSpeed
                txtPressure.text = pressure
                txtHumidity.text = humidity

                progressBar.visibility = View.GONE
                mainContainer.visibility = View.VISIBLE
            } catch (e: Exception) {
                progressBar.visibility = View.GONE
                errorMessage.visibility = View.VISIBLE
            }
        }

        override fun doInBackground(vararg p0: String?): String? {
            var result: String? = try {
                URL("https://api.openweathermap.org/data/2.5/weather?lat=$lat&lon=$lon&lang=vi&appid=$appID").readText(
                    Charsets.UTF_8
                )
            } catch (e: Exception) {
                null
            }
            return result
        }

    }

}
