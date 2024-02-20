package com.example.bookajobworker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class SignIn : AppCompatActivity() {
    private lateinit var btnBackToLogin: Button
    private lateinit var btnContinue: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        //For Login Button
        btnBackToLogin = findViewById(R.id.buttonBackToLogin)
        btnBackToLogin.setOnClickListener {
            navToLogin()
        }
        // For Continue Button
        btnContinue = findViewById(R.id.buttonContinue)
        btnContinue.setOnClickListener {
            navToNextStep()
        }



    }

    private fun navToNextStep() {
        val intent = Intent(this, SignUpContinue::class.java)
        startActivity(intent)
        finish()
    }

    private fun navToLogin() {
        val intent = Intent(this, Login::class.java)
        startActivity(intent)
        finish()
    }
}
