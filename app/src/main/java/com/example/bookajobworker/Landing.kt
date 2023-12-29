package com.example.bookajobworker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Landing : AppCompatActivity() {
    private lateinit var btnGetStarted: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing)
        //For Login Button
        btnGetStarted = findViewById(R.id.btnGetStarted)
        btnGetStarted.setOnClickListener {
            navToLogin()
        }
    }

    private fun navToLogin() {
        val intent = Intent(this, Login::class.java)
        startActivity(intent)
        finish()
    }
}