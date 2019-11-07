package com.aceman.notepad

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_note.view.*

/**
 * Created by Lionel JOFFRAY - on 07/11/2019.
 */

class NoteAdapter (val notes: List<Note>, val itemClickListener: View.OnClickListener) : RecyclerView.Adapter<NoteAdapter.ViewHodler>() {

    class ViewHodler(itemview:View) : RecyclerView.ViewHolder(itemview){
        val cardView = itemView.card_view
        val titleView = itemview.title
        val excerptView = itemView.excerpt


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHodler {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_note, parent,false)
        return ViewHodler(itemView)
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    override fun onBindViewHolder(holder: ViewHodler, position: Int) {
        val note = notes[position]
        holder.cardView.setOnClickListener(itemClickListener)
        holder.cardView.tag = position
        holder.titleView.setText(note.title)
        holder.excerptView.setText(note.text)
    }


}