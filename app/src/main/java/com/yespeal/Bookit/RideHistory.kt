package com.yespeal.Bookit

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.LinearLayout
import com.yespeal.Bookit.Adapter.HistoryAdapter
import com.yespeal.Bookit.model.DataAdapterCarSelection
import kotlinx.android.synthetic.main.activity_ride_history.*
import java.util.ArrayList

class RideHistory : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ride_history)
        toolbar.setTitle("History")

        toolbar.setNavigationOnClickListener {
            var intent= Intent(this,Drawer::class.java)
            startActivity(intent)
            finish()

        }
        val recyclerView = findViewById(R.id.idRecycleHistory) as RecyclerView

        //adding a layoutmanager
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)


        val users = ArrayList<DataAdapterCarSelection>()

        //adding some dummy data to the list
        users.add(DataAdapterCarSelection(R.drawable.ic_car, "MIcro", "5 Min", false))
        users.add(DataAdapterCarSelection(R.drawable.ic_car, "MIcro", "5 Min", false))
        users.add(DataAdapterCarSelection(R.drawable.ic_car, "MIcro", "5 Min", false))
        users.add(DataAdapterCarSelection(R.drawable.ic_car, "MIcro", "5 Min", false))
        users.add(DataAdapterCarSelection(R.drawable.ic_car, "MIcro", "5 Min", false))
        users.add(DataAdapterCarSelection(R.drawable.ic_car, "MIcro", "5 Min", false))
        users.add(DataAdapterCarSelection(R.drawable.ic_car, "MIcro", "5 Min", false))
        users.add(DataAdapterCarSelection(R.drawable.ic_car, "MIcro", "5 Min", false))
        users.add(DataAdapterCarSelection(R.drawable.ic_car, "MIcro", "5 Min", false))
        users.add(DataAdapterCarSelection(R.drawable.ic_car, "MIcro", "5 Min", false))
        users.add(DataAdapterCarSelection(R.drawable.ic_car, "MIcro", "5 Min", false))
        users.add(DataAdapterCarSelection(R.drawable.ic_car, "MIcro", "5 Min", false))
        users.add(DataAdapterCarSelection(R.drawable.ic_car, "MIcro", "5 Min", false))
        users.add(DataAdapterCarSelection(R.drawable.ic_car, "MIcro", "5 Min", false))
        users.add(DataAdapterCarSelection(R.drawable.ic_car, "MIcro", "5 Min", false))
        users.add(DataAdapterCarSelection(R.drawable.ic_car, "MIcro", "5 Min", false))
        users.add(DataAdapterCarSelection(R.drawable.ic_car, "MIcro", "5 Min", false))
        users.add(DataAdapterCarSelection(R.drawable.ic_car, "MIcro", "5 Min", false))


        val adapter = HistoryAdapter(users, this);
        recyclerView.adapter = adapter
    }
}
