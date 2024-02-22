package com.example.bookajobworker

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject


object SessionManager {
    private lateinit var sharedPreferences: SharedPreferences

    fun initialize(context: Context) {
        sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
    }

    fun getAccessToken(): String? {
        return sharedPreferences.getString("access_token", null)
    }

    fun getExpiresIn(): Long {
        return sharedPreferences.getLong("expiresIn", 0)
    }

    fun redirectToLogin(context: Context) {
        val intent = Intent(context, Login::class.java)
        context.startActivity(intent)
        if (context is AppCompatActivity) {
            context.finish()
        }
    }

    fun redirectToLanding(context: Context) {
        val intent = Intent(context, Landing::class.java)
        context.startActivity(intent)
        if (context is AppCompatActivity) {
            context.finish()
        }
    }

    fun isTokenExpired(): Boolean {
        val expiryTime = getExpiresIn()
        val token = getAccessToken()
        return System.currentTimeMillis() >= expiryTime
    }

    fun saveAccessToken(accessToken: String, expiresIn: Long) {
        val editor = sharedPreferences.edit()
        editor.putString("access_token", accessToken)
        editor.putLong("expiresIn", expiresIn)
        editor.apply()
    }

    fun saveCredentials(username: String, password: String) {
        val editor = sharedPreferences.edit()
        editor.putString("username", username)
        editor.putString("password", password)
        editor.apply()
    }

    fun getUsername(): String? {
        return sharedPreferences.getString("username", null)
    }

    fun getPassword(): String? {
        return sharedPreferences.getString("password", null)
    }
    fun clearSharedPreferences(context: Context) {
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }

    fun logout(context: Context) {
        val token = getAccessToken() ?: ""
        val username = getUsername() ?: ""
        val password = getPassword() ?: ""

        Log.d("token", token)

        val jsonData = JSONObject()
        jsonData.put("username", username)
        jsonData.put("password", password)

        val logoutUrl = "http://192.168.1.12:8000/api/logout"

        val requestQueue: RequestQueue = Volley.newRequestQueue(context)
        val jsonObjectRequest = object : JsonObjectRequest(
            Request.Method.POST, logoutUrl, jsonData,
            Response.Listener { _ ->
                    clearSharedPreferences(context)
                    redirectToLanding(context)
            },
            Response.ErrorListener { error ->
                // Handle error
                Log.e("LogoutError", "Logout request failed: $error")
            }) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                // Add Bearer token to the request headers
                headers["Authorization"] = "Bearer $token"
                return headers
            }
        }
        requestQueue.add(jsonObjectRequest)
    }
}