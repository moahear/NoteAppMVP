package com.gamil.moahear.noteappmvp.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.gamil.moahear.noteappmvp.data.model.NoteEntity
import com.gamil.moahear.noteappmvp.utils.Constants
import com.gamil.moahear.samplemvp.R
import com.gamil.moahear.samplemvp.databinding.LayoutNoteItemBinding
import javax.inject.Inject

class NotesAdapter @Inject constructor() : RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {
    private val notes = ArrayList<NoteEntity>()

    inner class NotesViewHolder(private val binding: LayoutNoteItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindNote(noteEntity: NoteEntity) {
            binding.apply {
                root.setOnClickListener {
                    setOnNoteClickListener?.let {
                        it(noteEntity)
                    }
                }
                txtTitle.text = noteEntity.title
                txtDescription.text = noteEntity.description
                when (noteEntity.priority) {
                    Constants.HIGH -> priorityColor.setBackgroundColor(
                        ContextCompat.getColor(
                            binding.root.context,
                            R.color.red
                        )
                    )

                    Constants.NORMAL -> priorityColor.setBackgroundColor(
                        ContextCompat.getColor(
                            priorityColor.context,
                            R.color.yellow
                        )
                    )

                    Constants.LOW -> priorityColor.setBackgroundColor(
                        ContextCompat.getColor(
                            priorityColor.context,
                            R.color.aqua
                        )
                    )

                }
                //Category
                when(noteEntity.category){
                    Constants.HOME->imgCategory.setImageResource(R.drawable.home)
                    Constants.WORK->imgCategory.setImageResource(R.drawable.work)
                    Constants.HEALTH->imgCategory.setImageResource(R.drawable.healthcare)
                    Constants.EDUCATION->imgCategory.setImageResource(R.drawable.education)
                    Constants.OTHER->imgCategory.setImageResource(R.drawable.ic_other_note)
                }

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val binding =
            LayoutNoteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NotesViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        holder.bindNote(notes[position])
    }

    private var setOnNoteClickListener:((NoteEntity)->Unit)?=null
    fun onNoteClick(listener:(NoteEntity)->Unit){
        setOnNoteClickListener=listener
    }
    private class NotesDiffUtilCallback(
        val oldNotes: List<NoteEntity>,
        val newNotes: List<NoteEntity>
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int {
            return oldNotes.size
        }

        override fun getNewListSize(): Int {
            return newNotes.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldNotes[oldItemPosition] === newNotes[newItemPosition]
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldNotes[oldItemPosition] === newNotes[newItemPosition]
        }
    }

    fun submitNewNotes(newNotes: List<NoteEntity>) {
        val notesDiffResult = DiffUtil.calculateDiff(NotesDiffUtilCallback(notes, newNotes))
        notesDiffResult.dispatchUpdatesTo(this)
        notes.clear()
        notes.addAll(newNotes)
    }
}