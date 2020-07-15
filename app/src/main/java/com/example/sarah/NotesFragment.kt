package com.example.sarah

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_notes.*

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

        context?.fileList()?.forEach {
            println("line 34>> $it")

            val itPtr = it
            val tmpNoteNames = TextView(activity)
            tmpNoteNames.setPadding(10, 10, 10, 10)
            tmpNoteNames.textSize = 22f
            tmpNoteNames.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            tmpNoteNames.setOnClickListener {
                val itFile = context!!.openFileInput(itPtr)
//                Toast.makeText(activity!!,
//                    itFile.bufferedReader().readText() , Toast.LENGTH_SHORT).show()

                val alertDialog: AlertDialog? = activity?.let {
                    val builder = AlertDialog.Builder(it)
                    builder.apply {
                        setTitle(itPtr)
                        setMessage("${itFile.bufferedReader().readText()}\nDelete note?")
                        setPositiveButton(R.string.ok,
                            DialogInterface.OnClickListener { _, _ ->
                                Toast.makeText(activity!!, "Deleting Note $itPtr", Toast.LENGTH_LONG).show()
                                context.deleteFile(itPtr)
                                tmpNoteNames.visibility = View.GONE
                            })
                        setNegativeButton(R.string.dont_reset_note,null)
                    }
                    val create = builder.create()
                    create
                }

                alertDialog?.show()

            }
            tmpNoteNames.text = it
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