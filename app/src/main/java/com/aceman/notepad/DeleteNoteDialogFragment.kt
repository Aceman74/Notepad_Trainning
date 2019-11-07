package com.aceman.notepad

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment

/**
 * Created by Lionel JOFFRAY - on 07/11/2019.
 */

class DeleteNoteDialogFragment (val noteTitle: String ="") : DialogFragment(){

interface DeleteDialogListener {

    fun onDialogPositiveCheck()
    fun onDialogNegativeCheck()
}
    var listener: DeleteDialogListener? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val builder = AlertDialog.Builder(activity)

        builder.setMessage("Etes-vous sur de vouloir supprimer la note \"$noteTitle\" ?")
                .setPositiveButton("Supprimer") { dialog, id -> listener?.onDialogPositiveCheck()}
                .setNegativeButton("Annuler") { dialog, id -> listener?.onDialogNegativeCheck()}

        return builder.create()
    }
}