package com.example.task1

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnShowApps = findViewById<Button>(R.id.btnShowApps)
        btnShowApps.setOnClickListener {
            startActivity(Intent(this@MainActivity, AppsListActivity::class.java))
        }
    }
}