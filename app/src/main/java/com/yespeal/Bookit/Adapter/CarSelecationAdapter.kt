package com.yespeal.Bookit.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import com.yespeal.Bookit.model.DataAdapterCarSelection
import com.yespeal.Bookit.R


class CarSelecationAdapter(val DataList : List<DataAdapterCarSelection>, val context:Context): RecyclerView.Adapter<CarSelecationAdapter.ViewHolder>() {

   var LastSelected : Int = 0
    lateinit var contextActivity :Context
    init {
        contextActivity=context
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val v = LayoutInflater.from(context).inflate(R.layout.layout_car,p0,false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return DataList.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.bindItems(DataList[p1])

        p0.itemView.setOnClickListener {
            DataList[LastSelected].isSelected=false
            notifyItemChanged(LastSelected)
            DataList[p1].isSelected=true
            LastSelected=p1;
            notifyItemChanged(p1)
        }



    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(user: DataAdapterCarSelection) {
            val textViewName = itemView.findViewById(R.id.txt_car_type_name) as TextView
            val txtWaitingTime  = itemView.findViewById(R.id.txt_waiting_time) as TextView
            val Car_img=itemView.findViewById(R.id.Car_img) as ImageButton
            itemView.setOnClickListener {

            }
            if(user.isSelected)
            {
                Car_img.setImageResource(R.drawable.ic_car_selected)
            }
            else{
                Car_img.setImageResource(R.drawable.ic_car)
            }

            textViewName.text = user.NameService
            txtWaitingTime.text = user.TimeTake
        }


    }
}