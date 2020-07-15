package com.example.sarah

import android.app.Activity
import android.content.Context
import android.content.Intent
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
import kotlinx.android.synthetic.main.fragment_emergency.*

class EmergencyFragment : Fragment() {

//    var arr = arrayListOf<String>(*context?.fileList()!!)
    var contactSharedPreference = activity?.getSharedPreferences(getString(R.string.emergency_head), Context.MODE_PRIVATE)
    val REQUEST_PHONE_NUMBER = 1
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
//            if(cursor.moveToNext()) {
//                phoneNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
//                println(phoneNumber)
//                with(contactSharedPreference!!.edit()) {
//                    putLong(contactName, phoneNumber.toLong())
//                    commit()
//                }
//                Toast.makeText(context, "$contactName : $phoneNumber", Toast.LENGTH_LONG).show()
//            }
            println("contactSharedPreference : $contactSharedPreference")
            with(contactSharedPreference!!.edit()) {
                putLong(contactName, phoneNumber.toLong())
                commit()
            }
            Toast.makeText(context, "$contactName >> ${contactSharedPreference!!.getLong(contactName, -1)}", Toast.LENGTH_LONG).show()
            adapterArr.add(contactName)
            adapter.notifyDataSetChanged()
        }
        cursor.close()
        contactSharedPreference!!.all.forEach {
            println("${it.key} => ${it.value}")
        }
    }

    public fun callContact(view: TextView)
    {
        Toast.makeText(context, view.text, Toast.LENGTH_LONG).show()
    }

    public fun deleteContact(view: TextView)
    {
        Toast.makeText(context, "${view.text} deleted!", Toast.LENGTH_LONG).show()
        view.visibility = View.GONE //false delete
    }

}