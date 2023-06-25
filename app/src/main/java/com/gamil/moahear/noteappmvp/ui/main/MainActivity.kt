package com.gamil.moahear.noteappmvp.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.gamil.moahear.noteappmvp.data.model.NoteEntity
import com.gamil.moahear.noteappmvp.data.repository.main.MainRepository
import com.gamil.moahear.noteappmvp.ui.add.NoteFragment
import com.gamil.moahear.samplemvp.databinding.ActivityMainBinding
import com.jakewharton.rxbinding4.view.clicks
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(),MainContracts.View {
    private lateinit var binding: ActivityMainBinding
    @Inject
    lateinit var notesAdapter: NotesAdapter
    @Inject
    lateinit var mainRepository: MainRepository
    private val mainPresenter by lazy{
        MainPresenter(mainRepository,this)
    }
    private val compositeDisposable by lazy {
        CompositeDisposable()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            //Add note
            compositeDisposable.add(btnAdd.clicks().observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    NoteFragment().show(supportFragmentManager, NoteFragment().tag)
                })
            //Show Notes
            mainPresenter.getNotes()
        }
        notesAdapter.onNoteClick {
            Toast.makeText(this@MainActivity, it.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onStop() {
        super.onStop()
        compositeDisposable.clear()
    }

    override fun showNotes(notes: List<NoteEntity>) {
        binding.apply {
            containerEmpty.visibility = View.GONE
            rvNotes.visibility = View.VISIBLE
            notesAdapter.submitNewNotes(notes)
            rvNotes.layoutManager=StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
            rvNotes.adapter=notesAdapter
        }

    }

    override fun showEmpty() {
        binding.apply {
            containerEmpty.visibility = View.VISIBLE
            rvNotes.visibility = View.GONE
        }
    }

}