package com.example.bookajobworker

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity

class Post : AppCompatActivity() {
    private lateinit var tagChips : Spinner
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_activity)

        // Initialize the spinner
        tagChips = findViewById(R.id.tagsChips)

        // Populate the spinner with the string array
        val genderOptions = resources.getStringArray(R.array.category)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, genderOptions)

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Apply the adapter to the spinner
        tagChips.adapter = adapter
    }
}