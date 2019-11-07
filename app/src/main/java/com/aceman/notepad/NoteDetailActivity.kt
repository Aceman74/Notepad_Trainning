package com.aceman.notepad

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.note_detail_activity.*
import kotlinx.android.synthetic.main.note_detail_activity.toolbar
import kotlinx.android.synthetic.main.note_list_activity.*

class NoteDetailActivity : AppCompatActivity() {
    companion object {
        val EXTRA_NOTE = "note"
        val EXTRA_NOTE_INDEX = "noteIndex"
        val REQUEST_EDIT_NOTE = 101
        val ACTION_SAVE_NOTE = "com.aceman.notepad.actions.ACTION_SAVE_NOTE"
        val ACTION_DELETE_NOTE = "com.aceman.notepad.actions.ACTION_DELETE_NOTE"
    }

    lateinit var note: Note
    var noteIndex = -1
    lateinit var titleView: TextView
    lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.note_detail_activity)

        val toolbar = this.toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        note = intent.getParcelableExtra(EXTRA_NOTE)
        noteIndex = intent.getIntExtra(EXTRA_NOTE_INDEX, -1)

        titleView = this.title_detail
        textView = this.text_detail
        titleView.text = note.title
        textView.text = note.text
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_note_detail, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.option_save -> {
                saveNote()
                return true
            }
            R.id.option_delete -> {
                showConfirmDeleteDialog()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun showConfirmDeleteDialog() {
        val confirmFragment = DeleteNoteDialogFragment(note.title)
        confirmFragment.listener = object : DeleteNoteDialogFragment.DeleteDialogListener {
            override fun onDialogPositiveCheck() {
                deleteNote()
            }

            override fun onDialogNegativeCheck() {
            }
        }
        confirmFragment.show(supportFragmentManager, "confirmDelete")
    }

    fun saveNote(){
        note.title = titleView.text.toString()
        note.text = textView.text.toString()
        intent = Intent(ACTION_SAVE_NOTE)
        intent.putExtra(EXTRA_NOTE, note as Parcelable)
        intent.putExtra(EXTRA_NOTE_INDEX,noteIndex)
        setResult(Activity.RESULT_OK,intent)
        finish()
    }

    fun deleteNote(){
        intent = Intent(ACTION_DELETE_NOTE)
        intent.putExtra(EXTRA_NOTE_INDEX,noteIndex)
        setResult(Activity.RESULT_OK,intent)
        finish()
    }
}
