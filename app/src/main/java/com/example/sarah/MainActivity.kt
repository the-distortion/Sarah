package com.example.sarah

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
//import androidx.drawerlayout.widget.DrawerLayout
//import kotlinx.android.synthetic.main.fragment_home.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val menufrag = menuFrag()

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragSwitch, menufrag)
            commit()

        }
    }
}