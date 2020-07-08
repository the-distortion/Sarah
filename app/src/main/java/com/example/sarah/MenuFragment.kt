package com.example.sarah

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_menu.*


class MenuFragment : Fragment(){
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        activity?.findViewById<TextView>(R.id.toolbar_title)?.text = resources.getString(R.string.app_name)

        return inflater.inflate(R.layout.fragment_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        card_notes.setOnClickListener{

            activity?.findViewById<TextView>(R.id.toolbar_title)?.text = resources.getString(R.string.notes_head).trim()

            fragmentManager?.beginTransaction()?.apply {
                replace(R.id.fragSwitch, NotesFragment())
                addToBackStack(null)
                commit()
            }
        }
    }
}