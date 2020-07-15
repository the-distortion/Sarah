package com.example.sarah

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_menu.*


class MenuFragment : Fragment(){
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        activity?.findViewById<TextView>(R.id.toolbar_title)?.text = resources.getString(R.string.app_name)

        return inflater.inflate(R.layout.fragment_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        card_emergency.setOnClickListener {
            activity?.findViewById<TextView>(R.id.toolbar_title)?.text = resources.getString(R.string.emergency_head).trim()

            fragmentManager?.beginTransaction()?.apply {
                replace(R.id.fragSwitch, EmergencyFragment())
                addToBackStack(null)
                commit()
            }
        }

        card_notes.setOnClickListener{

            activity?.findViewById<TextView>(R.id.toolbar_title)?.text = resources.getString(R.string.notes_head).trim()

            fragmentManager?.beginTransaction()?.apply {
                replace(R.id.fragSwitch, NotesFragment())
                addToBackStack(null)
                commit()
            }
        }

        card_reminders.setOnClickListener {
            Toast.makeText(activity!!, "Test Reminders", Toast.LENGTH_SHORT).show()
        }

        card_reports.setOnClickListener {
            Toast.makeText(activity!!, "Test Reports", Toast.LENGTH_SHORT).show()
        }
    }
}