package com.gamil.moahear.noteappmvp.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gamil.moahear.noteappmvp.ui.add.NoteFragment
import com.gamil.moahear.samplemvp.databinding.ActivityMainBinding
import com.jakewharton.rxbinding4.view.clicks
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val compositeDisposable by lazy {
        CompositeDisposable()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            compositeDisposable.add(btnAdd.clicks().observeOn(AndroidSchedulers.mainThread())
                .subscribe {

                    NoteFragment().show(supportFragmentManager, NoteFragment().tag)
                })
        }
    }

    override fun onStop() {
        super.onStop()
        compositeDisposable.clear()
    }
}