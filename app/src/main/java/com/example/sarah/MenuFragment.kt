package com.example.sarah

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_menu.*


class MenuFragment : Fragment(){
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        card_notes.setOnClickListener{

            val toolbar = activity?.findViewById<Toolbar>(R.id.toolbar)
            toolbar?.title = "Notes"

            fragmentManager?.beginTransaction()?.apply {
                replace(R.id.fragSwitch, NotesFragment())
                addToBackStack(null)
                commit()
            }
        }
    }
}