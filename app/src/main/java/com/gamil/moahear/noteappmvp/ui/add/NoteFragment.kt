package com.gamil.moahear.noteappmvp.ui.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.gamil.moahear.noteappmvp.data.model.NoteEntity
import com.gamil.moahear.noteappmvp.data.repository.add.AddNoteRepository
import com.gamil.moahear.noteappmvp.utils.Constants
import com.gamil.moahear.samplemvp.databinding.FragmentNoteBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.jakewharton.rxbinding4.view.clicks
import com.jakewharton.rxbinding4.widget.itemSelections
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import javax.inject.Inject

@AndroidEntryPoint
class NoteFragment : BottomSheetDialogFragment(), AddContracts.View {
    private lateinit var categories: Array<String>
    private var selectedCategory = ""
    private lateinit var priorities: Array<String>
    private var selectedPriority = ""
    private var noteId = 0
    private var type = ""

    @Inject
    lateinit var addNoteRepository: AddNoteRepository

    //region addPresenter
    @Inject
    lateinit var addPresenter: AddPresenter

    /* private val addPresenter by lazy {
         AddPresenter(addNoteRepository, this)
     }*/
    //endregion
    @Inject
    lateinit var noteEntity: NoteEntity
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
        //Arguments
        noteId = arguments?.getInt(Constants.BUNDLE_NOTE_ID) ?: 0

        type = if (noteId > 0) {
            Constants.EDIT
        } else {
            Constants.NEW
        }
        binding.apply {
            compositeDisposable.add(
                imgClose.clicks().observeOn(AndroidSchedulers.mainThread()).subscribe {
                    this@NoteFragment.dismiss()
                })

            categoriesSpinnerItems()
            prioritiesSpinnerItems()

            //show note to edit
            if (type == Constants.EDIT) {
                addPresenter.getNote(noteId)
            }


            btnSave.clicks().observeOn(AndroidSchedulers.mainThread()).subscribe {
                val title = edtTitle.text.toString()
                val description = edtDescription.text.toString()
                noteEntity.apply {
                    this.title = title
                    this.description = description
                    this.priority = selectedPriority
                    this.category = selectedCategory
                    this.id = noteId
                }
                //Save
                if (type == Constants.NEW) {
                    addPresenter.saveNote(noteEntity)
                } else {
                    addPresenter.updateNote(noteEntity)
                }
            }


        }
    }

    private fun FragmentNoteBinding.categoriesSpinnerItems() {
        categories = arrayOf(
            Constants.HOME,
            Constants.WORK,
            Constants.EDUCATION,
            Constants.HEALTH,
            Constants.OTHER
        )
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
        priorities = arrayOf(Constants.HIGH, Constants.NORMAL, Constants.LOW)
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
        addPresenter.onStop()
    }

    override fun close() {
        this.dismiss()
    }

    private fun getIndex(list: Array<String>, item: String): Int {
        return list.indexOf(item)
    }

    override fun showNote(noteEntity: NoteEntity) {
        if (this.isAdded) {
            requireActivity().runOnUiThread {
                binding.apply {
                    edtDescription.setText(noteEntity.description)
                    edtTitle.setText(noteEntity.title)
                    spinnerCategories.setSelection(categories.indexOf(noteEntity.category))
                    spinnerPriority.setSelection(priorities.indexOf(noteEntity.priority))
                }
            }
        }
    }

}
