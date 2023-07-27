package com.example.coolnote.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.coolnote.viewHolder.NoteViewHolder
import com.example.coolnote.databinding.NoteItemBinding
import com.example.coolnote.model.NoteModel

class NotesAdapter : Adapter<NoteViewHolder>() {
    private var data = ArrayList<NoteModel>()
    private lateinit var context: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        context = parent.context
        val binding =
            NoteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.binding.noteTitle.text = data[position].noteTitle
        holder.binding.noteInfo.text = data[position].noteInfo

    }

    fun setData(newData: ArrayList<NoteModel>) {
        val noteDiffUtil = NoteDiffUtil(data, newData)
        val noteDiff = DiffUtil.calculateDiff(noteDiffUtil)
        data = newData
        noteDiff.dispatchUpdatesTo(this)

    }

    class NoteDiffUtil(
        private val oldData: ArrayList<NoteModel>,
        private val newData: ArrayList<NoteModel>
    ) :
        DiffUtil.Callback() {
        override fun getOldListSize(): Int {
            return oldData.size
        }

        override fun getNewListSize(): Int {
            return newData.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldData[oldItemPosition].noteId == newData[newItemPosition].noteId
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldData[oldItemPosition] == newData[newItemPosition]
        }

    }
}