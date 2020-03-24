package com.ndroid.elaliasolidaireadmin

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import com.google.firebase.database.FirebaseDatabase
import com.ndroid.elaliasolidaire.Service
import kotlinx.android.synthetic.main.activity_service_details.*

class ServiceDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service_details)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val serviceData = intent.getSerializableExtra("service_data") as Service
        tvUserName.text = serviceData.user
        tvAdresse.text = serviceData.adresse
        tvTel.text = serviceData.tel
        tvServices.text = serviceData.service

        Log.e("etat: ", "${serviceData.etat}")

        if (serviceData.etat == 0) {
            tvState.text = "في إنتظار التأكيد"
            // pas encore confirmé
            btnDone.visibility = View.GONE
        } else if (serviceData.etat == 1) {
            // confirmé
            tvState.text = "مؤكد"
            btnConfirm.visibility = View.GONE
            btnCancel.visibility = View.GONE
        } else if (serviceData.etat == -1) {
            tvState.text = "ملغي"
            tvState.setTextColor(Color.parseColor("#FF0000"))
            btnConfirm.visibility = View.GONE
            btnCancel.visibility = View.GONE
            btnDone.visibility = View.GONE
        }else if (serviceData.etat == 2) {
            tvState.text = "تم الإصال"
            btnConfirm.visibility = View.GONE
            btnCancel.visibility = View.GONE
            btnDone.visibility = View.GONE
        }

        val database = FirebaseDatabase.getInstance().reference.child(serviceData.userKey).child("services").child(serviceData.databaseKey)
        btnConfirm.setOnClickListener {
            tvState.text = "مؤكد"
            database.child("etat").setValue(1)
            btnConfirm.visibility = View.GONE
            btnCancel.visibility = View.GONE
            btnDone.visibility = View.VISIBLE
        }

        btnDone.setOnClickListener {
            tvState.text = "تم الإصال"
            database.child("etat").setValue(2)
            btnConfirm.visibility = View.GONE
            btnCancel.visibility = View.GONE
            btnDone.visibility = View.GONE
        }

        btnCancel.setOnClickListener {
            tvState.text = "ملغي"
            tvState.setTextColor(Color.parseColor("#FF0000"))
            database.child("etat").setValue(-1)
            finish()
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home)
            finish()
        return super.onContextItemSelected(item)
    }

}
