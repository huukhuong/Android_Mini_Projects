package com.example.foodorderapp.controller

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.foodorderapp.R

class RegisterActivity : AppCompatActivity() {

    private lateinit var btnLogin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        addControls()
        addEvents()
    }

    private fun addControls() {
        btnLogin = findViewById(R.id.btnLogin)
    }

    private fun addEvents() {
        btnLogin.setOnClickListener { startActivityLogin() }
    }

    private fun startActivityLogin() {
        startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
        finish()
    }

}