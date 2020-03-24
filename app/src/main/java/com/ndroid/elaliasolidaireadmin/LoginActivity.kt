package com.ndroid.elaliasolidaireadmin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        supportActionBar?.hide()

        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        if(currentUser != null) {
            startActivity(Intent(this, MainActivity::class.java))
        }

        //jciadmin
        //jcielalia2020
        auth = FirebaseAuth.getInstance()

        btnLogin.setOnClickListener {

            if(editUserName.text.toString().isEmpty() || editPassword.text.toString().isEmpty()) {
                tvError.text = "يرجى ملء كل الخانات"
                tvError.visibility = View.VISIBLE
            }else {
                val login = editUserName.text.toString()
                val password = editPassword.text.toString()
                btnLogin.isEnabled = false
                btnLogin.text = "جاري التثبيت..."
                auth.signInWithEmailAndPassword(login, password)
                    .addOnCompleteListener(this) { task ->
                        btnLogin.isEnabled = true
                        btnLogin.text = "دخول"

                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("LoginActivity", "signInWithEmailAndPassword:success")

                            startActivity(Intent(this, MainActivity::class.java))
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(
                                "LoginActivity",
                                "signInWithEmailAndPassword:failure",
                                task.exception
                            )
                            tvError.text = "إسم المستخدم أو كلمة السر خاطئة"
                            tvError.visibility = View.VISIBLE
                        }

                    }
            }

        }

    }

}
