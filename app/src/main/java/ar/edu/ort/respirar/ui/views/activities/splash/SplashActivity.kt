package ar.edu.ort.respirar.ui.views.activities.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import ar.edu.ort.respirar.R
import ar.edu.ort.respirar.ui.views.activities.MainActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val handler= Handler(Looper.getMainLooper())
        handler.postDelayed({
            val intent= Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        },4000)
    }
}