package tk.huukhuongit.flashlight

import android.content.Context
import android.content.res.ColorStateList
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraManager
import android.os.Build
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import java.util.TimerTask
import java.util.Timer

class MainActivity : AppCompatActivity() {

    private lateinit var btnFlash: ImageButton
    private lateinit var btnSOS: Button
    private var isFlashTurnOn = false
    private var isSOS = false
    private lateinit var cameraManager: CameraManager
    private var cameraId: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addControls()
        addEvents()
    }

    override fun onDestroy() {
        super.onDestroy()
        turnOffFlash()
    }

    private fun addControls() {
        isFlashTurnOn = false
        isSOS = false

        cameraManager = getSystemService(Context.CAMERA_SERVICE) as CameraManager
        cameraId = cameraManager.cameraIdList[0]

        btnFlash = findViewById(R.id.btnFlash)
        btnSOS = findViewById(R.id.btnSOS)

        turnOffFlash()
    }

    private fun addEvents() {
        btnFlash.setOnClickListener { turnFlash() }

        btnSOS.setOnClickListener {
            turnSOSFlash()
        }
    }

    private fun turnFlash() {
        val scaleDown: Animation = AnimationUtils.loadAnimation(this, R.anim.scale_down)
        btnFlash.startAnimation(scaleDown)

        if (!isFlashTurnOn) {
            turnOnFlash()
        } else {
            turnOffFlash()
        }

        isFlashTurnOn = !isFlashTurnOn
    }

    private fun turnSOSFlash() {
        if(isSOS)
        Timer().scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                if (isSOS) {
                    btnSOS.backgroundTintList =
                        ColorStateList.valueOf(resources.getColor(R.color.active))
                    Thread.sleep(500)
                    turnOffFlash()
                    var delay = 0L
                    for (i in 0..8) {
                        delay = if (i < 3 || i > 5) {
                            300
                        } else {
                            800
                        }
                        turnOnFlash()
                        Thread.sleep(delay)
                        turnOffFlash()
                        Thread.sleep(200)
                    }
                } else {
                    btnSOS.backgroundTintList =
                        ColorStateList.valueOf(resources.getColor(R.color.disabled))
                }
            }
        }, 0, 6000)
        isSOS = !isSOS
    }

    private fun turnOffFlash() {
        btnFlash.backgroundTintList =
            ColorStateList.valueOf(resources.getColor(R.color.disabled))
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                cameraManager.setTorchMode(cameraId, false)
            }
        } catch (e: CameraAccessException) {
        }
    }

    private fun turnOnFlash() {
        btnFlash.backgroundTintList =
            ColorStateList.valueOf(resources.getColor(R.color.active))
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                cameraManager.setTorchMode(cameraId, true)
            }
        } catch (e: CameraAccessException) {
        }
    }

}
