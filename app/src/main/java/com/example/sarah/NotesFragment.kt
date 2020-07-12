package com.example.sarah

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_notes.*
import java.io.File

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

        File("/").walk().forEach {
            val itPtr = it
            val tmpNoteNames = TextView(activity)
            tmpNoteNames.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            tmpNoteNames.setOnClickListener {
                Toast.makeText(activity!!, itPtr.readText(), Toast.LENGTH_SHORT).show()
            }
            tmpNoteNames.text = it.name
            tmpNoteNames.height
            notes_list_layout.addView(tmpNoteNames)
        }

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