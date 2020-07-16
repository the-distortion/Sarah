package com.example.sarah

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.contact_items.*
import kotlinx.android.synthetic.main.contact_items.view.*
import kotlinx.android.synthetic.main.fragment_emergency.*
import java.lang.Exception
import java.util.jar.Manifest

class EmergencyFragment : Fragment() {

//    var arr = arrayListOf<String>(*context?.fileList()!!)
    var contactSharedPreference = activity?.getSharedPreferences(getString(R.string.emergency_head), Context.MODE_PRIVATE)
    val REQUEST_PHONE_NUMBER = 1
    val REQUEST_CODE = 1
    lateinit var contactUri: Uri
    lateinit var adapterArr: MutableList<String>
    lateinit var adapter: ArrayAdapter<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        contactSharedPreference = activity?.getSharedPreferences(getString(R.string.emergency_head), Context.MODE_PRIVATE)
        adapterArr = contactSharedPreference!!.all.keys.toMutableList()
        return inflater.inflate(R.layout.fragment_emergency, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requestPermission(arrayOf(android.Manifest.permission.CALL_PHONE,
            android.Manifest.permission.READ_CONTACTS), REQUEST_CODE)

        println(contactSharedPreference)
        if(contactSharedPreference != null) {
            adapter = ArrayAdapter<String>(context!!, R.layout.contact_items, R.id.contact_name, adapterArr)
            emergency_contact_list.adapter = adapter
        }

        btn_add_emergency_contact.setOnClickListener {

            val intent = Intent(Intent.ACTION_PICK)

            intent.type = ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE

            if(intent.resolveActivity(context!!.packageManager) != null){
                startActivityForResult(intent, REQUEST_PHONE_NUMBER)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == REQUEST_PHONE_NUMBER && resultCode == Activity.RESULT_OK){
            contactUri = data!!.data!!

            getPhoneNumber()
//            emergency_contact_list.invalidateViews()
        }
    }

    private fun getPhoneNumber() {
        val projection = arrayOf(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER)
        val cursor = context!!.contentResolver.query(contactUri, projection, null, null, null)

        if(cursor!!.moveToFirst()){
            val phoneNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
            val contactName= cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
            println(contactName)
            println(phoneNumber)
            /*if(cursor.moveToNext()) {
                phoneNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                println(phoneNumber)
                with(contactSharedPreference!!.edit()) {
                    putLong(contactName, phoneNumber.toLong())
                    commit()
                }
                Toast.makeText(context, "$contactName : $phoneNumber", Toast.LENGTH_LONG).show()
            }*/
            println("contactSharedPreference : $contactSharedPreference")
            with(contactSharedPreference!!.edit()) {
                try {
                    putLong(contactName, phoneNumber.toLong())
                }catch (exception : Exception) {
                    Toast.makeText(context, "Invalid Contact Number!", Toast.LENGTH_LONG).show()
                    return
                }
                commit()
            }
            adapterArr = contactSharedPreference!!.all.keys.toMutableList().sorted() as MutableList<String>
//            adapterArr.forEach { println(">$it<")}
            adapter = ArrayAdapter(context!!, R.layout.contact_items, R.id.contact_name, adapterArr)
            emergency_contact_list.adapter = adapter
//            emergency_contact_list.refreshDrawableState()
            Toast.makeText(context, "$contactName >> ${contactSharedPreference!!.getLong(contactName, -1)}", Toast.LENGTH_LONG).show()
        }
        cursor.close()
        /*contactSharedPreference!!.all.forEach {
            println("${it.key} => ${it.value}")
        }*/
    }

    private fun requestPermission(permissions: Array<String>, requestCode: Int)
    {
        var permissionsNotGranted: String = ""
        permissions.forEach {
            if (ContextCompat.checkSelfPermission(context!!, it) == PackageManager.PERMISSION_DENIED) {
                permissionsNotGranted += "$it "
                println("$it asked :)")
                /*@RequiresApi(23)
                if(shouldShowRequestPermissionRationale(permission)) {
                    // In an educational UI, explain to the user why your app requires this
                    // permission for a specific feature to behave as expected. In this UI,
                    // include a "cancel" or "no thanks" button that allows the user to
                    // continue using your app without granting the permission.
                    Toast.makeText(context, "Give me the permission to make calls to your emergency contacts!", Toast.LENGTH_LONG).show()
                    ActivityCompat.requestPermissions(activity!!, arrayOf(permission), REQUEST_CALL)
                }*/
            }
        }
        ActivityCompat.requestPermissions(activity!!, permissionsNotGranted.split(" ")
            .toTypedArray(), requestCode)
    }

}