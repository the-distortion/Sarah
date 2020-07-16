package com.example.sarah

//import android.widget.LinearLayout
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.fragment_emergency.*

//import androidx.drawerlayout.widget.DrawerLayout
//import kotlinx.android.synthetic.main.fragment_home.*

class MainActivity : AppCompatActivity() {

    lateinit var contactSharedPreference: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val menufrag = MenuFragment()

        contactSharedPreference = getSharedPreferences(getString(R.string.emergency_head), 0)

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragSwitch, menufrag)
            commit()
        }
    }


    public fun callContact(view: View)
    {
        val parent = view.parent as ViewGroup
        val contactName = parent.getChildAt(0) as TextView
//        println(getSharedPreferences(getString(R.string.emergency_head), 0)
//            .getLong(contactName.text.toString(), -1))
        Toast.makeText(this, "Calling ${contactSharedPreference.getLong(contactName.text.toString(),
            -1).toString()}", Toast.LENGTH_SHORT).show()
        val callIntent =Intent(Intent.ACTION_CALL, Uri.parse("tel:${contactSharedPreference.getLong(contactName.text.toString(),
            -1).toString()}"))
        if(ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.CALL_PHONE ) == PackageManager.PERMISSION_GRANTED)
            startActivity(callIntent)
    }

    public fun deleteContact(view: View)
    {
        val parent = view.parent as ViewGroup
        val contactName = parent.getChildAt(0) as TextView
        Toast.makeText(this, "${contactName.text} deleted!", Toast.LENGTH_LONG).show()
        parent.visibility = View.GONE //false delete
        println("Deleted ${contactName.text}")
        contactSharedPreference.edit().apply {
            remove(contactName.text.toString())
            commit()
        }

        /*println("After deletion:")
        contactSharedPreference.all.forEach {
            println("${it.key} -> ${it.value}")
        }*/

        emergency_contact_list.adapter = ArrayAdapter<String>(this, R.layout.contact_items, R.id.contact_name,
            contactSharedPreference.all.keys.toMutableList().sorted())
        /*emergency_contact_list.refreshDrawableState()*/
    }

}