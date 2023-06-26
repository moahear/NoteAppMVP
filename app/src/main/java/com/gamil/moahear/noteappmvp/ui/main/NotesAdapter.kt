package com.gamil.moahear.noteappmvp.ui.main

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
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
                imgMenu.setOnClickListener {
                    val popupMenu = PopupMenu(it.context, it)
                    popupMenu.menuInflater.inflate(R.menu.menu_popup_item, popupMenu.menu)
                    //Show popup menu with icon
                    try {
                        val fieldMPopup = PopupMenu::class.java.getDeclaredField("mPopup")
                        fieldMPopup.isAccessible = true
                        val mPopup = fieldMPopup.get(popupMenu)
                        mPopup.javaClass
                            .getDeclaredMethod("setForceShowIcon", Boolean::class.java)
                            .invoke(mPopup, true)
                    } catch (e: Exception) {
                        Log.e("Main", "Error showing menu icons.", e)
                    } finally {
                        popupMenu.show()
                    }


                    popupMenu.setOnMenuItemClickListener { menuItem ->
                        when (menuItem.itemId) {
                            R.id.delete_item -> {
                                setOnNoteClickListener?.let {
                                    it(noteEntity, Constants.DELETE)
                                }
                            }

                            R.id.edit_item -> {
                                setOnNoteClickListener?.let {
                                    it(noteEntity, Constants.EDIT)
                                }
                            }
                        }
                        return@setOnMenuItemClickListener true
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
                when (noteEntity.category) {
                    Constants.HOME -> imgCategory.setImageResource(R.drawable.home)
                    Constants.WORK -> imgCategory.setImageResource(R.drawable.work)
                    Constants.HEALTH -> imgCategory.setImageResource(R.drawable.healthcare)
                    Constants.EDUCATION -> imgCategory.setImageResource(R.drawable.education)
                    Constants.OTHER -> imgCategory.setImageResource(R.drawable.ic_other_note)
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

    private var setOnNoteClickListener: ((NoteEntity, String) -> Unit)? = null
    fun onNoteClick(listener: (NoteEntity, String) -> Unit) {
        setOnNoteClickListener = listener
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