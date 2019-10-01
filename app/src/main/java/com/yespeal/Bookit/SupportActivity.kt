package com.yespeal.Bookit

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.View


class SupportActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_support)

        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar

        toolbar.setTitle("Support")

        toolbar.setNavigationOnClickListener {
            var intent=Intent(this,Drawer::class.java)
            startActivity(intent)
            finish()

        }
    }
}
