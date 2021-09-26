package com.example.foodorderapp.controller

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.foodorderapp.R

class LoginActivity : AppCompatActivity() {

    private lateinit var btnForgotPassword:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        addControls()
        addEvents()
    }

    private fun addControls() {
        btnForgotPassword = findViewById(R.id.btnForgotPassword)
    }

    private fun addEvents() {
        btnForgotPassword.setOnClickListener { startActitvityForgotPassword() }
    }

    private fun startActitvityForgotPassword() {
        startActivity(Intent(this@LoginActivity, ForgotPasswordActivity::class.java))
    }

}