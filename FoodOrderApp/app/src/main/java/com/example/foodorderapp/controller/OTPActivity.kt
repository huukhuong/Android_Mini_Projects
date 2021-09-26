package com.example.foodorderapp.controller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import com.example.foodorderapp.R
import android.text.Editable

import android.text.TextWatcher
import android.view.View
import android.widget.*

class OTPActivity : AppCompatActivity() {

    private lateinit var txvCancel: TextView
    private lateinit var otp1: EditText
    private lateinit var otp2: EditText
    private lateinit var otp3: EditText
    private lateinit var otp4: EditText
    private lateinit var txvCountdown: TextView
    private lateinit var containerResend: LinearLayout
    private lateinit var txvResendOTP: TextView
    private lateinit var btnResetPassword: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp)

        addControls()
        addEvents()
    }

    private fun addControls() {
        txvCancel = findViewById(R.id.txvCancel)
        otp1 = findViewById(R.id.otp1)
        otp2 = findViewById(R.id.otp2)
        otp3 = findViewById(R.id.otp3)
        otp4 = findViewById(R.id.otp4)
        txvCountdown = findViewById(R.id.txvCountdown)
        containerResend = findViewById(R.id.containerResend)
        txvResendOTP = findViewById(R.id.txvResendOTP)
        btnResetPassword = findViewById(R.id.btnResetPassword)

        otp1.requestFocus()
    }

    private fun addEvents() {
        otp1.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (otp1.text.trim().isNotEmpty())
                    otp2.requestFocus()
            }
        })
        otp2.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (otp2.text.trim().isNotEmpty())
                    otp3.requestFocus()
                else
                    otp1.requestFocus()
            }
        })
        otp3.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (otp3.text.trim().isNotEmpty())
                    otp4.requestFocus()
                else
                    otp2.requestFocus()
            }
        })
        otp4.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (otp4.text.trim().isNotEmpty())
                    btnResetPassword.performClick()
                else
                    otp3.requestFocus()
            }
        })
        btnResetPassword.setOnClickListener { processValidateOTP() }
        txvResendOTP.setOnClickListener { processResendOTP() }

        var countdown = 10
        object : CountDownTimer(11000, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                txvCountdown.text = getString(R.string.resend_otp_in) + " $countdown" + "s"
                countdown--
            }

            override fun onFinish() {
                txvCountdown.visibility = View.GONE
                containerResend.visibility = View.VISIBLE
            }
        }.start()
    }

    private fun processResendOTP() {

    }

    private fun processValidateOTP() {
        Toast.makeText(this, "Click button", Toast.LENGTH_SHORT).show()
    }

}