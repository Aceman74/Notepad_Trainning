package com.aceman.notepad

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle
import android.os.Parcelable
import android.view.View
import android.widget.Toast
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.aceman.notepad.utils.deleteNote
import com.aceman.notepad.utils.loadNotes
import com.aceman.notepad.utils.persistNote
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.note_list_activity.*

class NoteListActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var notes: MutableList<Note>
    lateinit var adapter: NoteAdapter
    lateinit var coordinatorLy: CoordinatorLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.note_list_activity)

        val toolbar = this.toolbar
        setSupportActionBar(toolbar)

        this.create_note_ab.setOnClickListener(this)

       notes = loadNotes(this)
        coordinatorLy = findViewById(R.id.coordinator_ly)

        adapter = NoteAdapter(notes, this)

        val recyclerView = this.recycler_view
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    override fun onClick(view: View) {
        if (view.tag != null)
            showNoteDetail(view.tag as Int)
        when (view.id){
            R.id.create_note_ab -> createNewNote()
        }
    }

    private fun createNewNote() {
        showNoteDetail(-1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode != Activity.RESULT_OK || data == null){
            return
        }
        when (requestCode){
            NoteDetailActivity.REQUEST_EDIT_NOTE -> processEditNoteResult(data)
        }
    }

    private fun processEditNoteResult(data: Intent) {
        val noteIndex = data.getIntExtra(NoteDetailActivity.EXTRA_NOTE_INDEX, -1)
        when (data.action){
            NoteDetailActivity.ACTION_SAVE_NOTE -> {
                val note = data.getParcelableExtra<Note>(NoteDetailActivity.EXTRA_NOTE)
                saveNote(note,noteIndex)
            }
            NoteDetailActivity.ACTION_DELETE_NOTE -> {
                deleteNote(noteIndex)
            }
        }
    }

    private fun saveNote(note: Note, noteIndex: Int) {
        persistNote(this,note)
        if (noteIndex < 0){
            notes.add(0,note)
        }else{
        notes[noteIndex] = note
        }
        adapter.notifyDataSetChanged()
    }

    private fun deleteNote(noteIndex: Int) {
        if(noteIndex < 0){
            return
        }
        val note =  notes.removeAt(noteIndex)
        deleteNote(this,note)

        adapter.notifyDataSetChanged()
        Snackbar.make(coordinatorLy,"${note.title} a été supprimée.", Snackbar.LENGTH_LONG)
                .show()
    }

    private fun showNoteDetail(noteIndex: Int) {
        val note = if (noteIndex < 0) Note() else notes[noteIndex]
        val intent = Intent(this,NoteDetailActivity::class.java)
        intent.putExtra(NoteDetailActivity.EXTRA_NOTE, note as Parcelable)
        intent.putExtra(NoteDetailActivity.EXTRA_NOTE_INDEX,noteIndex)
        startActivityForResult(intent,NoteDetailActivity.REQUEST_EDIT_NOTE)
    }

}
