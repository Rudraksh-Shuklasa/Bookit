package com.yespeal.Bookit

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.LinearLayout
import com.yespeal.Bookit.Adapter.ShareAdapter
import kotlinx.android.synthetic.main.activity_refer.*
import java.util.ArrayList

class ReferActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_refer)

        val users = ArrayList<Int>()
        users.add(1)
        users.add(1)
        users.add(1)
        users.add(1)
        users.add(1)
        users.add(1)
        users.add(1)
        users.add(1)
        users.add(1)

        backHomeScreen.setOnClickListener {
            var intent= Intent(this,Drawer::class.java)
            startActivity(intent)
            finish()

        }
        val recyclerView = findViewById(R.id.idRecyclerViewHorizontalList) as RecyclerView

        //adding a layoutmanager
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.HORIZONTAL, false)



        val adapter = ShareAdapter(users, this);
        recyclerView.adapter = adapter
    }
}
