package com.example.comickufinal.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.comickufinal.R
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        // persistence database
        try {
            Firebase.database.setPersistenceEnabled(true)
        } catch (e: RuntimeException) {
            // mengatasi error jika user logout dan login kembali
            // dikarenakan method ini hanya dieksekusi pas pertama kali aplikasi dimulai
            // dan tidak boleh dieksekusi kembali
        }
    }
}