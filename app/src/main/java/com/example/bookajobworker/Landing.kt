package com.example.bookajobworker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.net.ConnectivityManager
import android.widget.Toast
import android.content.Context
import android.content.IntentFilter
import android.net.NetworkCapabilities
import android.os.Build

class Landing : AppCompatActivity() {
    private lateinit var btnGetStarted: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing)

        val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)

        // Check for internet connectivity
        if (!isNetworkAvailable(this)) {
            Toast.makeText(this, "No internet connection detected", Toast.LENGTH_SHORT).show()
        } else {
            //For Login Button
            btnGetStarted = findViewById(R.id.btnGetStarted)
            btnGetStarted.setOnClickListener {
                navToLogin()
            }
        }
    }

    private fun navToLogin() {
        val intent = Intent(this, Login::class.java)
        startActivity(intent)
        finish()
    }
    private fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false
            val networkCapabilities = connectivityManager.getNetworkCapabilities(network) ?: return false

            return networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                    networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                    networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
        } else {
            val networkInfo = connectivityManager.activeNetworkInfo
            return networkInfo != null && networkInfo.isConnected
        }
    }

}