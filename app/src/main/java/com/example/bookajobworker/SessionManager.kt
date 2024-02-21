package com.example.bookajobworker

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

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
}