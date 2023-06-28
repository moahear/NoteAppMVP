package com.gamil.moahear.noteappmvp.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.gamil.moahear.noteappmvp.data.model.NoteEntity
import com.gamil.moahear.noteappmvp.data.repository.main.MainRepository
import com.gamil.moahear.noteappmvp.ui.add.NoteFragment
import com.gamil.moahear.noteappmvp.utils.Constants
import com.gamil.moahear.samplemvp.R
import com.gamil.moahear.samplemvp.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import com.jakewharton.rxbinding4.view.clicks
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), MainContracts.View {
    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var notesAdapter: NotesAdapter

    @Inject
    lateinit var mainRepository: MainRepository
    private var selectedPriorityFilter = -1

    //region mainPresenter
    @Inject
    lateinit var mainPresenter: MainPresenter

    /*private val mainPresenter by lazy{
        MainPresenter(mainRepository,this)
    }*/
    //endregion
    private val compositeDisposable by lazy {
        CompositeDisposable()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            //setSupportActionBar(toolbarNotes)
            //Add note
            compositeDisposable.add(btnAdd.clicks().observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    NoteFragment().show(supportFragmentManager, NoteFragment().tag)
                })
            //Show Notes
            mainPresenter.getNotes()

            //Filter
            toolbarNotes.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.action_filter -> {
                        filterByPriority()
                        return@setOnMenuItemClickListener true
                    }

                    else -> {
                        return@setOnMenuItemClickListener false
                    }
                }
            }
        }
        //Click popup menu item
        notesAdapter.onNoteClick { noteEntity, state ->
            when (state) {
                Constants.EDIT -> {
                    val noteFragment = NoteFragment()
                    val bundle = Bundle()
                    bundle.putInt(Constants.BUNDLE_NOTE_ID, noteEntity.id)
                    noteFragment.arguments = bundle
                    noteFragment.show(supportFragmentManager, noteFragment.tag)
                }

                Constants.DELETE -> {
                    mainPresenter.deleteNote(noteEntity)
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()
        compositeDisposable.clear()
        mainPresenter.onStop()
    }

    override fun showNotes(notes: List<NoteEntity>) {
        binding.apply {
            containerEmpty.visibility = View.GONE
            rvNotes.visibility = View.VISIBLE
            notesAdapter.submitNewNotes(notes)
            rvNotes.layoutManager =
                StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            rvNotes.adapter = notesAdapter
        }

    }

    override fun showEmpty() {
        binding.apply {
            containerEmpty.visibility = View.VISIBLE
            rvNotes.visibility = View.GONE
        }
    }

    override fun showDeleteMessage() {
        Snackbar.make(binding.root, getString(R.string.delete_message), Snackbar.LENGTH_LONG).show()
    }

    private fun filterByPriority() {
        val alertDialogBuilder = AlertDialog.Builder(this@MainActivity)
        alertDialogBuilder.setTitle("Select priority filter from below")
        val priorities = arrayOf(Constants.NO_FILTER, Constants.HIGH, Constants.NORMAL, Constants.LOW)
        alertDialogBuilder.setSingleChoiceItems(
            priorities,
            selectedPriorityFilter
        ) { dialog, index ->

            when (index) {
                0 -> {
                    mainPresenter.getNotes()
                }

                in 1..3 -> {
                    mainPresenter.filterNotesWithPriority(priorities[index])
                }
            }
            selectedPriorityFilter = index
            dialog.dismiss()
        }
        alertDialogBuilder.create().show()
    }
}