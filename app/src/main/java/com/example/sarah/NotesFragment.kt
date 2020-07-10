package com.example.sarah

import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_menu.*
import kotlinx.android.synthetic.main.fragment_notes.*
import kotlinx.android.synthetic.main.fragment_write_note.*

class NotesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notes, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        btn_add_notes.setOnClickListener{
            activity?.findViewById<TextView>(R.id.toolbar_title)?.text = resources.getString(R.string.add_note_head).trim()

            fragmentManager?.beginTransaction()?.apply {
                replace(R.id.fragSwitch, WriteNote())
                addToBackStack(null)
                commit()
            }
        }

    }

}