package com.utkarshnew.android.courses.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.utkarshnew.android.R

import com.utkarshnew.android.address.interfaces.onItemSelected
import com.utkarshnew.android.address.model.Data
import com.utkarshnew.android.courses.Interfaces.NotesTypeItemClick
import com.utkarshnew.android.courses.modal.NotesType
import com.utkarshnew.android.databinding.AddressAdapterBinding
import com.utkarshnew.android.databinding.NotesTypeAdapterBinding

class NotesTypeAdapter(
    var context: Context,
    var noteslist: List<NotesType>,
    var notesTypeItemClick: NotesTypeItemClick
) : RecyclerView.Adapter<NotesTypeAdapter.NotesTypeAdapterholder>() {
    var oldpos: Int = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesTypeAdapterholder {

        val view = NotesTypeAdapterBinding.inflate(LayoutInflater.from(context), parent, false)
        return NotesTypeAdapterholder(view)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onBindViewHolder(holder: NotesTypeAdapterholder, position: Int) {
        holder.bind(noteslist[position])


        if (position == oldpos ||   noteslist[position].isIs_select) {
            oldpos =position
            holder.notesTypeAdapterBinding.notesLayout.setBackgroundDrawable(context.resources.getDrawable(R.drawable.round_gray_notes_yello, null))
        } else {
            noteslist[oldpos].isIs_select = false
            holder.notesTypeAdapterBinding.notesLayout.setBackgroundDrawable(context.resources.getDrawable(R.drawable.round_gray_notes, null))
        }

        holder.notesTypeAdapterBinding.notesLayout.setOnClickListener {
            val copyOfLastCheckedPosition: Int = oldpos
            if (copyOfLastCheckedPosition !=position)
            {
                oldpos = position
                notifyItemChanged(copyOfLastCheckedPosition)
                noteslist[oldpos].isIs_select = true
                notifyItemChanged(oldpos)
                notesTypeItemClick.ItemClick(position, noteslist[position])
            }


        }
    }

    override fun getItemCount(): Int {
        return noteslist.size
    }

    inner class NotesTypeAdapterholder(val notesTypeAdapterBinding: NotesTypeAdapterBinding) :
        RecyclerView.ViewHolder(notesTypeAdapterBinding.root) {
        fun bind(noteslist: NotesType) {
            notesTypeAdapterBinding.notestype = noteslist
        }
    }

}