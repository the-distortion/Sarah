package com.example.sarah

import android.content.Context
import android.content.DialogInterface
import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.fragment_notes.*
import kotlinx.android.synthetic.main.fragment_write_note.*
import java.io.File
import java.io.FileOutputStream

class WriteNote : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_write_note, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        img_btn_reset.setOnClickListener {

            val alertDialog: AlertDialog? = activity?.let {
                val builder = AlertDialog.Builder(it)
                builder.apply {
                    setTitle(R.string.reset_note_dialog_head)
                    setMessage(R.string.reset_note_dialog_message)
                    setPositiveButton(R.string.ok,
                        DialogInterface.OnClickListener { _, _ ->
                            Toast.makeText(activity!!, "Resetting Note Body", Toast.LENGTH_LONG).show()
                            activity!!.findViewById<EditText>(R.id.note_body).text.clear()
                        })
                    setNegativeButton(R.string.dont_reset_note,
                        DialogInterface.OnClickListener { _, _ ->
                            Toast.makeText(activity!!, "Better be careful from the next time!", Toast.LENGTH_LONG).show()
                        })
                }
                val create = builder.create()
                create
            }

            alertDialog?.show()
        }

        btn_save_note.setOnClickListener {

            val noteTitle = note_title.text.toString().trim()
            val noteBody = note_body.text.toString().trim()

            if(noteTitle.isEmpty())
            {
                note_title.error = "Title Required"
                note_title.requestFocus()
                return@setOnClickListener
            }

            if(noteBody.isEmpty())
            {
                note_body.error = "Body Required"
                note_body.requestFocus()
                return@setOnClickListener
            }

            val note : FileOutputStream = context!!.openFileOutput(noteTitle, Context.MODE_PRIVATE)
//            if(!noteCreated) Toast.makeText(activity!!, "$noteTitle already exists and might be overwritten!", Toast.LENGTH_LONG).show()
            note.write(noteBody.toByteArray())
            Toast.makeText(activity!!, "$noteTitle is saved.", Toast.LENGTH_LONG).show()
            note.close()
            context!!.fileList().forEach {
                println(it)
            }
            /*println("line 45 reached")
            val note = Note(noteTitle, noteBody)
            println("line 47 reached")
            saveNote(note)
            println("line 49 reached")*/
        }
    }
}