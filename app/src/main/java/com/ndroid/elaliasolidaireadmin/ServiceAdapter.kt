package com.ndroid.elaliasolidaire

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.res.Resources
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.firebase.database.FirebaseDatabase
import com.ndroid.elaliasolidaireadmin.R
import com.ndroid.elaliasolidaireadmin.ServiceDetailsActivity
import java.util.*
import kotlin.collections.ArrayList

class ServiceAdapter(var context: Context) : BaseAdapter() {

    var servicesList: ArrayList<Service> = ArrayList()

    fun setData(servicesList:ArrayList<Service> ) {
        this.servicesList = servicesList
    }


    override fun getView(position: Int, view: View?, parent: ViewGroup?): View {

        var convertView = LayoutInflater.from(parent?.context).inflate(R.layout.service_item, parent, false)
        var tvService : TextView = convertView.findViewById(R.id.tvServices)
        var tvDate : TextView = convertView.findViewById(R.id.tvDate)
        var tvUser : TextView = convertView.findViewById(R.id.tvUser)

        val selectedService = this.servicesList[position]
        tvService.text = selectedService.service
        tvDate.text = selectedService.dateDemande
        tvUser.text = selectedService.user

        convertView.setOnClickListener {
            val intent = Intent(context, ServiceDetailsActivity::class.java)
            intent.putExtra("service_data", selectedService)
            context.startActivity(intent)
        }


        return convertView
    }

    override fun getItem(position: Int): Any {
        return this.servicesList[position]
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return this.servicesList.count()
    }

    val Int.dp: Int
        get() = (this * Resources.getSystem().displayMetrics.density).toInt()

}
