package com.aceman.notepad

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_note_detail.*
import kotlinx.android.synthetic.main.activity_note_detail.toolbar
import kotlinx.android.synthetic.main.note_list_activity.*

class NoteDetailActivity : AppCompatActivity() {
    companion object{
        val EXTRA_NOTE = "note"
        val EXTRA_NOTE_INDEX = "noteIndex"
    }
    lateinit var note: Note
    var noteIndex = -1
    lateinit var titleView: TextView
    lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_detail)

        val toolbar = this.toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        note = intent.getParcelableExtra(EXTRA_NOTE)
        noteIndex = intent.getIntExtra(EXTRA_NOTE_INDEX,-1)

        titleView = this.title_detail
        textView = this.text_detail
        titleView.text = note.title
        textView.text = note.text
    }
}
