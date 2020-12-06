package com.task.fundsIndia.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.task.fundsIndia.databinding.ActivitySplashScreenBinding
import com.task.fundsIndia.utils.base.BaseAppCompactActivity

class SplashActivity : BaseAppCompactActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivitySplashScreenBinding.inflate(layoutInflater).root)
        moveNextScreen()
    }

    private fun moveNextScreen() {
        Handler().postDelayed(Runnable {
            startActivity(Intent(this, NavigationActivity::class.java))
            finish()
        }, 2000)
    }
}