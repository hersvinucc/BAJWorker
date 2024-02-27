package com.example.bookajobworker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView

class SignIn : AppCompatActivity() {
    private lateinit var btnBackToLogin: ImageView
    private lateinit var btnContinue: Button
    private lateinit var txtusername: EditText
    private lateinit var txtemail: EditText
    private lateinit var txtcontact: EditText
    private lateinit var txtpass: EditText
    private lateinit var txtconfirmpass: EditText

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
        // Used for Getting the Input Fields
        txtusername = findViewById(R.id.usernameSignIn)
        txtemail = findViewById(R.id.emailAddressSignIn)
        txtcontact = findViewById(R.id.contactNumberSignIn)
        txtpass = findViewById(R.id.passwordSignIn)
        txtconfirmpass = findViewById(R.id.confirmPassSignIn)

    }

    private fun navToNextStep() {
        val username = txtusername.text.toString()
        val email = txtemail.text.toString()
        val contact = txtcontact.text.toString()
        val pass = txtpass.text.toString()
        val confirmpass = txtconfirmpass.text.toString()

        val registrationData = Bundle().apply {
            putString("username", username)
            putString("email", email)
            putString("contact_number", contact)
            putString("password", pass)
            putString("confirmregisterpassword", confirmpass)
        }

        val intent = Intent(this, SignUpContinue::class.java)
        intent.putExtra("registration_data", registrationData)
        startActivity(intent)
        finish()
    }

    private fun navToLogin() {
        // initialize the contents

        val intent = Intent(this, Login::class.java)
        startActivity(intent)
        finish()
    }



}
