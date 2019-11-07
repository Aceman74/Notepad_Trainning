package com.aceman.notepad

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.note_list_activity.*

class NoteListActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var notes: MutableList<Note>
    lateinit var adapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.note_list_activity)

        notes = mutableListOf<Note>()
        notes.add(Note("Note 1", "Salut toi! comment tu vas depuis le temps? On te voit" +
                " plus en soirée."))
        notes.add(Note("Liste de courses", "Du pain, du boursin, du vin, des cacahuètes " +
                "et des chouettes."))
        notes.add(Note("RAPPEL", "COUCOU, MOI J'ECRIS TOUTJOURS EN CAPS LOCK PARCEQUE JE" +
                " SAIS PAS, J'AI L'IMPRESSION QUE JE PARLE PLUS FORT ET QUE TOUT LE MONDE ME VERRA."))

        adapter = NoteAdapter(notes, this)

        val recyclerView = this.recycler_view
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    override fun onClick(view: View) {
        if (view.tag != null)
            showNoteDetail(view.tag as Int)
    }

    private fun showNoteDetail(noteIndex: Int) {
        val note = notes[noteIndex]
        val intent = Intent(this,NoteDetailActivity::class.java)
        intent.putExtra(NoteDetailActivity.EXTRA_NOTE, note)
        intent.putExtra(NoteDetailActivity.EXTRA_NOTE_INDEX,noteIndex)
        startActivity(intent)
    }

}
