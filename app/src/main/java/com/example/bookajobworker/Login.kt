package com.example.bookajobworker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Login : AppCompatActivity() {
    private lateinit var buttonLogin: Button
    private lateinit var buttonSignIn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //For Login Button
        buttonLogin = findViewById(R.id.buttonLogin)
        buttonLogin.setOnClickListener {
            navigateToMainActivity()
        }

        //For SignIn Button
        buttonSignIn = findViewById(R.id.buttonSignIn)
        buttonSignIn.setOnClickListener {
            navigateToSignInActivity()
        }
    }

    private fun navigateToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun navigateToSignInActivity() {
        val intent = Intent(this, SignIn::class.java)
        startActivity(intent)
        finish()
    }
}