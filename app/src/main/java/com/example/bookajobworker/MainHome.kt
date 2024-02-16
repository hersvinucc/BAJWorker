package com.example.bookajobworker

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity

class MainHome : AppCompatActivity() {
    private lateinit var buttonRequest: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_home)

        //For Login Button
        buttonRequest = findViewById(R.id.requestButton)
        buttonRequest.setOnClickListener {
            navigateToRequestBook()
        }


    }
    private fun navigateToRequestBook() {
        val intent = Intent(this, Request_Booker_Detail::class.java)
        startActivity(intent)
        finish()
    }
}