package com.example.sarah

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
//import androidx.drawerlayout.widget.DrawerLayout
//import kotlinx.android.synthetic.main.fragment_home.*

class MainActivity : AppCompatActivity() {

    private lateinit var home_layout: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        home_layout = findViewById(R.id.home_layout)

        supportFragmentManager.beginTransaction()
            .replace(R.id.frame, Home())
            .commit()

    }
}