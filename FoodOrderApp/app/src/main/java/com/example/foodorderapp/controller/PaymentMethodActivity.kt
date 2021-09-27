package com.example.foodorderapp.controller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.TextView
import com.example.foodorderapp.R
import com.example.foodorderapp.model.District
import com.example.foodorderapp.model.Province
import com.example.foodorderapp.model.Ward
import org.json.JSONArray
import java.io.IOException
import java.io.InputStream

class PaymentMethodActivity : AppCompatActivity() {

    private var payOnline = true
    private lateinit var btnChangeAddress: TextView
    private lateinit var btnChangePhoneNumber: TextView
    private lateinit var txvAddress: TextView
    private lateinit var txvPhoneNumber: TextView
    private lateinit var btnPayOnline: RadioButton
    private lateinit var btnPayOnArrival: RadioButton
    private lateinit var containerPayOnline: LinearLayout
    private lateinit var containerPayOnArrival: LinearLayout
    private lateinit var btnPayVisa: ImageButton
    private lateinit var btnPayPaypal: ImageButton
    private lateinit var btnPayStripe: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_method)

        addControls()
        addEvents()
    }

    private fun addControls() {
        btnChangeAddress = findViewById(R.id.btnChangeAddress)
        btnChangePhoneNumber = findViewById(R.id.btnChangePhoneNumber)
        txvAddress = findViewById(R.id.txvAddress)
        txvPhoneNumber = findViewById(R.id.txvPhoneNumber)
        btnPayOnline = findViewById(R.id.btnPayOnline)
        btnPayOnArrival = findViewById(R.id.btnPayOnArrival)
        containerPayOnline = findViewById(R.id.containerPayOnline)
        containerPayOnArrival = findViewById(R.id.containerPayOnArrival)
        btnPayVisa = findViewById(R.id.btnPayVisa)
        btnPayPaypal = findViewById(R.id.btnPayPaypal)
        btnPayStripe = findViewById(R.id.btnPayStripe)
    }

    private fun addEvents() {
        btnChangeAddress.setOnClickListener { processChangeAddress() }
        btnChangePhoneNumber.setOnClickListener { processChangePhoneNumber() }
        btnPayOnline.setOnClickListener { setMethodToPayOnline() }
        btnPayOnArrival.setOnClickListener { setMethodToPayOnArrival() }
        containerPayOnline.setOnClickListener { btnPayOnline.performClick() }
        containerPayOnArrival.setOnClickListener { btnPayOnArrival.performClick() }
        btnPayVisa.setOnClickListener { setMethodToPayVisa() }
        btnPayPaypal.setOnClickListener { setMethodToPayPaypal() }
        btnPayStripe.setOnClickListener { setMethodToPayStripe() }
    }


    private fun processChangeAddress() {
        val provinces = ArrayList<Province>()
        val json: JSONArray = readJson("vietnam.json")
        for (i in 0 until json.length()) {
            val province = json.getJSONObject(i)
            val name = province.getString("name")
            val districtsJSON = province.getJSONArray("districts")
            val districtsArray = ArrayList<District>()
            for (j in 0 until districtsJSON.length()) {
                val district = districtsJSON.getJSONObject(j)
                val districtName = district.getString("name")
                val wardsJSON = district.getJSONArray("wards")
                val wardsArray = ArrayList<Ward>()
                for (k in 0 until wardsJSON.length()) {
                    val ward = wardsJSON.getJSONObject(k)
                    val wardName = ward.getString("name")
                    val wardPrefix = ward.getString("prefix")
                    wardsArray.add(Ward(wardName, wardPrefix))
                }
                districtsArray.add(District(districtName, wardsArray))
            }
            provinces.add(Province(name, districtsArray))
        }
    }

    private fun processChangePhoneNumber() {
    }

    private fun setMethodToPayOnline() {
        btnPayOnline.isChecked = true
        btnPayOnArrival.isChecked = false
        payOnline = true
    }

    private fun setMethodToPayOnArrival() {
        btnPayOnline.isChecked = false
        btnPayOnArrival.isChecked = true
        payOnline = false
    }

    private fun setMethodToPayVisa() {
        setMethodToPayOnline()
        btnPayVisa.setImageResource(R.drawable.ic_btn_pay_visa_selected)
        btnPayPaypal.setImageResource(R.drawable.ic_btn_pay_paypal)
        btnPayStripe.setImageResource(R.drawable.ic_btn_pay_stripe)
    }

    private fun setMethodToPayPaypal() {
        setMethodToPayOnline()
        btnPayVisa.setImageResource(R.drawable.ic_btn_pay_visa)
        btnPayPaypal.setImageResource(R.drawable.ic_btn_pay_paypal_selected)
        btnPayStripe.setImageResource(R.drawable.ic_btn_pay_stripe)
    }

    private fun setMethodToPayStripe() {
        setMethodToPayOnline()
        btnPayVisa.setImageResource(R.drawable.ic_btn_pay_visa)
        btnPayPaypal.setImageResource(R.drawable.ic_btn_pay_paypal)
        btnPayStripe.setImageResource(R.drawable.ic_btn_pay_stripe_selected)
    }

    private fun readJson(name: String): JSONArray {
        val bufferReader = application.assets.open(name).bufferedReader()
        val data = bufferReader.use {
            it.readText()
        }
        return JSONArray(data)
    }

}