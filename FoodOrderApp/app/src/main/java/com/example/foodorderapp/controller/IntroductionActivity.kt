package com.example.foodorderapp.controller

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.foodorderapp.R
import com.synnapps.carouselview.CarouselView
import com.synnapps.carouselview.ImageListener


class IntroductionActivity : AppCompatActivity() {

    private lateinit var carouselView: CarouselView
    private var images = intArrayOf(
        R.drawable.slider_1,
        R.drawable.slider_2,
        R.drawable.slider_3
    )
    private lateinit var btnRegister: Button
    private lateinit var btnLogin: Button
    private lateinit var txvSkip: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_introduction)

        addControls()
        addEvents()
    }

    private fun addControls() {
        carouselView = findViewById(R.id.carousel)
        carouselView.pageCount = images.size
        carouselView.setImageListener(imageListener);

        btnRegister = findViewById(R.id.btnRegister)
        btnLogin = findViewById(R.id.btnLogin)
        txvSkip = findViewById(R.id.txvSkip)
    }

    private fun addEvents() {
        btnRegister.setOnClickListener { goTORegisterActivity() }
        btnLogin.setOnClickListener { goToLoginActivity() }
        txvSkip.setOnClickListener { goToHomePage() }
    }

    private fun goTORegisterActivity() {
        startActivity(Intent(this, RegisterActivity::class.java))
    }

    private fun goToLoginActivity() {
        startActivity(Intent(this, LoginActivity::class.java))
    }

    private fun goToHomePage() {
//        startActivity(Intent(this, HomeActivity::class.java))
    }

    private var imageListener =
        ImageListener { position, imageView -> imageView.setImageResource(images.get(position)) }

}