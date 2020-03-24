package com.ndroid.elaliasolidaireadmin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import com.google.firebase.database.*
import com.ndroid.elaliasolidaire.Service
import com.ndroid.elaliasolidaire.ServiceAdapter
import kotlinx.android.synthetic.main.activity_services_list.*

class ServicesListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_services_list)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        var servicesData = arrayListOf<Service>()
        val servicesAdapter = ServiceAdapter(this)
        servicesAdapter.setData(servicesData)
        servicesList.adapter = servicesAdapter
        val database = FirebaseDatabase.getInstance().reference

        database.addChildEventListener(object : ChildEventListener {
            override fun onCancelled(error: DatabaseError) { // Failed to read value

            }

            override fun onChildMoved(dataSnapshot: DataSnapshot, previousChildName: String?) {

            }

            override fun onChildChanged(dataSnapshot: DataSnapshot, previousChildName: String?) {

            }

            override fun onChildAdded(dataSnapshot: DataSnapshot, previousChildName: String?) {
                val userKey = dataSnapshot.key
                dataSnapshot.child("services").children.forEach {
                    val value = it.getValue(Service::class.java)!!

                    progressBar.visibility = View.GONE
                    tvNoService.visibility = View.GONE
                    servicesList.visibility = View.VISIBLE
                    // -1 canceled
                    if (value.etat != -1)
                        servicesData.add(
                            Service(
                                user = value.user,
                                adresse = value.adresse,
                                service = value.service,
                                tel = value.tel,
                                dateDemande = value.dateDemande,
                                databaseKey = it.key!!,
                                etat = value.etat,
                                userKey = userKey!!
                            )
                        )
                    servicesAdapter.notifyDataSetChanged()
                }
            }

            override fun onChildRemoved(dataSnapshot: DataSnapshot) {
                for (i in 0 until servicesData.size) {
                    if (servicesData[i].databaseKey == dataSnapshot.key)
                        servicesData.removeAt(i)
                }
                servicesAdapter.notifyDataSetChanged()
                if (servicesData.size == 0) {
                    servicesList.visibility = View.GONE
                    tvNoService.visibility = View.VISIBLE
                }
            }
        })

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home)
            finish()
        return super.onContextItemSelected(item)
    }
}
