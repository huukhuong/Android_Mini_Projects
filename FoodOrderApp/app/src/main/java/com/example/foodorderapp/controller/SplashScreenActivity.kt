package com.example.foodorderapp.controller

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.foodorderapp.R
import java.util.Timer
import kotlin.concurrent.schedule

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {

    private val timeout = 1500L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Timer().schedule(timeout) {
            closeSplashScreen()
        }
    }

    private fun closeSplashScreen() {
        var intent: Intent = Intent()

        // if first time open app => start introdution activity
        intent = Intent(this, IntroductionActivity::class.java)
        // if not first time open app => start home activity
        // intent = Intent(this, HomeActivity::class.java)

        startActivity(intent)
        finish()
    }

}