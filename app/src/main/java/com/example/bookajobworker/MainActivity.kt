package com.example.bookajobworker

import com.example.bookajobworker.SessionManager
import android.content.Context
import android.content.Intent
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import android.os.Bundle;
import android.util.Log
import android.view.MenuItem;
import android.widget.Toast;
import com.google.android.material.navigation.NavigationView;

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

       SessionManager.initialize(this)

        if (!SessionManager.isTokenExpired()) {
            SessionManager.redirectToLogin(this)
        }

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        drawerLayout = findViewById(R.id.drawer_layout)
        val navigationView: NavigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)

        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.open,
            R.string.close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, Home())
                .commit()
            navigationView.setCheckedItem(R.id.nav_home)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_home -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, Home())
                    .commit()
            }

            R.id.nav_settings -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, Activity())
                    .commit()
            }

            R.id.nav_share -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, Settings())
                    .commit()
            }

            R.id.nav_logout -> {
                SessionManager.logout(this)
            }
//
//            R.id.nav_about -> {
//                supportFragmentManager.beginTransaction()
//                    .replace(R.id.fragment_container, AboutFragment())
//                    .commit()
//            }

//            R.id.nav_logout -> {
//                Toast.makeText(this, "Logout!", Toast.LENGTH_SHORT).show()
//            }
        }

        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}