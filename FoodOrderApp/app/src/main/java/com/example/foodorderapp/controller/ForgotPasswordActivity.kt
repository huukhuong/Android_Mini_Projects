package com.example.foodorderapp.controller

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.example.foodorderapp.R

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var btnRegister: Button
    private lateinit var txtEmail: EditText
    private lateinit var btnContinue: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        addControls()
        addEvents()
    }

    private fun addControls() {
        btnRegister = findViewById(R.id.btnRegister)
        txtEmail = findViewById(R.id.txtEmail)
        btnContinue = findViewById(R.id.btnContinue)
    }

    private fun addEvents() {
        btnRegister.setOnClickListener { startActivityRegister() }
        btnContinue.setOnClickListener { startActivityOTP() }
    }

    private fun startActivityOTP() {
        val email: String = txtEmail.text.trim().toString()
        val intent: Intent = Intent(this@ForgotPasswordActivity, OTPActivity::class.java)

        intent.putExtra("strEmail", email)
        startActivity(intent)
        finish()
    }

    private fun startActivityRegister() {
        startActivity(Intent(this@ForgotPasswordActivity, RegisterActivity::class.java))
        finish()
    }

}