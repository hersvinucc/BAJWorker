package com.example.bookajobworker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class Login : AppCompatActivity() {
    private lateinit var buttonLogin: Button
    private lateinit var buttonSignIn: Button
    private lateinit var txtusername: EditText
    private lateinit var txtpassword: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        SessionManager.initialize(this)

        if(!SessionManager.isTokenExpired()){
            SessionManager.redirectToLanding(this)
        } else {
            login()
        }

        //For Login Button
        buttonLogin = findViewById(R.id.buttonLogin)
        buttonLogin.setOnClickListener {
            autoLogin()
        }

        //For SignIn Button
        buttonSignIn = findViewById(R.id.buttonSignIn)
        buttonSignIn.setOnClickListener {
            navigateToSignInActivity()
        }

    }

    private fun login() {
        txtpassword = findViewById(R.id.editTextPassword)
        txtusername = findViewById(R.id.editTextUsername)

        val username = txtusername.text.toString()
        val password = txtpassword.text.toString()

        if (username.isNullOrEmpty() || password.isNullOrEmpty()) {
            Toast.makeText(this, "Username or password cannot be empty", Toast.LENGTH_SHORT).show()
            return
        }
        val loginData = LoginData(username, password)

        val jsonData = JSONObject()
        jsonData.put("username", loginData.username)
        jsonData.put("password", loginData.password)

        val queue: RequestQueue = Volley.newRequestQueue(this)
        val url = "http://192.168.1.12:8000/api/login"
//        val url ="https://baj.ucc-bscs.com/api/login"

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST, url, jsonData,
            { response ->
                // Check if the response contains the "user_role" field
                if (response.has("user_role")) {
                    SessionManager.saveCredentials(username, password)
                    val accessToken = response.getString("access_token")
                    val expiresIn = response.getLong("expires_in")
                    SessionManager.saveAccessToken(accessToken, expiresIn)
                    val userRole = response.getInt("user_role")
                    if (userRole == 1) {
                        navigateToMainActivity()
                    } else {
                        Toast.makeText(this, "User role is not authorized", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Invalid response from server", Toast.LENGTH_SHORT).show()
                }
            },
            { error ->
                // Handle error
                Toast.makeText(this, "Error occurred during login: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        )

        // Add the request to the RequestQueue
        queue.add(jsonObjectRequest)
    }

    private fun autoLogin() {
        val username = SessionManager.getUsername()
        val password = SessionManager.getPassword()
        LoginData(username.toString(), password.toString())
        Log.d("badtrip", username.toString())
        Log.d("yoko na", password.toString())
        login()
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