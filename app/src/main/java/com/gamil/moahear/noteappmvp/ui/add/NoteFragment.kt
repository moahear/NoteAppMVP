package com.gamil.moahear.noteappmvp.ui.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.gamil.moahear.samplemvp.databinding.FragmentNoteBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.jakewharton.rxbinding4.view.clicks
import com.jakewharton.rxbinding4.widget.itemSelections
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.functions.Consumer

class NoteFragment : BottomSheetDialogFragment() {
    private lateinit var categories: Array<String>
    private var selectedCategory = ""
    private lateinit var priorities: Array<String>
    private var selectedPriority = ""
    private lateinit var binding: FragmentNoteBinding
    private val compositeDisposable by lazy {
        CompositeDisposable()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            compositeDisposable.add(
                imgClose.clicks().observeOn(AndroidSchedulers.mainThread()).subscribe {
                    this@NoteFragment.dismiss()
                })
            categoriesSpinnerItems()
            prioritiesSpinnerItems()
        }
    }

    private fun FragmentNoteBinding.categoriesSpinnerItems() {
        categories = arrayOf("Home", "Work", "Education", "Health", "Other")
        val categoriesAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, categories)
        categoriesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCategories.adapter = categoriesAdapter
        /*spinnerCategories.onItemSelectedListener=object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedCategory=categories[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }*/
        compositeDisposable.add(
            spinnerCategories.itemSelections().skipInitialValue()
                .observeOn(AndroidSchedulers.mainThread()).subscribe {
                selectedCategory = categories[it]
            }
        )
    }

    private fun FragmentNoteBinding.prioritiesSpinnerItems() {
        priorities = arrayOf("High", "Normal", "Low")
        val prioritiesAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, priorities)
        prioritiesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerPriority.adapter = prioritiesAdapter
        /* spinnerPriority.onItemSelectedListener=object : AdapterView.OnItemSelectedListener {
             override fun onItemSelected(
                 parent: AdapterView<*>?,
                 view: View?,
                 position: Int,
                 id: Long
             ) {
                 selectedPriority=priorities[position]
             }

             override fun onNothingSelected(parent: AdapterView<*>?) {

             }
         }*/
        compositeDisposable.add(
            spinnerPriority.itemSelections().observeOn(AndroidSchedulers.mainThread()).subscribe {
                selectedPriority = priorities[it]
            }
        )
    }

    override fun onStop() {
        super.onStop()
        compositeDisposable.clear()
    }
}
