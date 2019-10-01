package com.yespeal.Bookit

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_splash_screen.*
import android.content.Intent
import android.content.SharedPreferences
import android.view.WindowManager
import  com.yespeal.Bookit.SplashScreen



class SplashScreen : AppCompatActivity() {
    lateinit var pref : SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        pref=applicationContext.getSharedPreferences("BookIt", 0)
        if(pref.getBoolean("isLogin",false)){
            val i2 = Intent(this, Drawer::class.java)
            startActivity(i2)
            finish()
        }
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        editText_carrierNumber.setOnClickListener {
            val i2 = Intent(this, MobileNumberOtp::class.java)
            startActivity(i2)
            overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up)
            finish()
        }

    }
}
