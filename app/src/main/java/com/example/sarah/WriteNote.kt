package com.example.sarah

import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_notes.*
import kotlinx.android.synthetic.main.fragment_write_note.*

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
            println("line 45 reached")
            val note = Note(noteTitle, noteBody)
            println("line 47 reached")
            saveNote(note)
            println("line 49 reached")
        }
    }

    private fun saveNote(note: Note)
    {
        class SaveNote: AsyncTask<Void, Void, Void>()
        {
            override fun doInBackground(vararg params: Void?): Void? {
                NoteDatabase(activity!!).getNoteDao().addNote(note)
                return null
            }

            override fun onPostExecute(result: Void?) {
                super.onPostExecute(result)

                Toast.makeText(activity, "note.title", Toast.LENGTH_LONG).show()
            }
        }

        SaveNote().execute()
    }

}