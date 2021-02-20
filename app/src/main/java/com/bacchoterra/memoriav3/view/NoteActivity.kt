package com.bacchoterra.memoriav3.view

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.bacchoterra.memoriav3.MemoriaApplication
import com.bacchoterra.memoriav3.R
import com.bacchoterra.memoriav3.databinding.ActivityNoteBinding
import com.bacchoterra.memoriav3.model.Category
import com.bacchoterra.memoriav3.model.Note
import com.bacchoterra.memoriav3.viewmodel.CategoryViewModel
import com.bacchoterra.memoriav3.viewmodel.CategoryViewModelFactory
import com.bacchoterra.memoriav3.viewmodel.NoteViewModel
import com.bacchoterra.memoriav3.viewmodel.NoteViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton

class NoteActivity : AppCompatActivity(), View.OnClickListener {

    //Layout components
    private lateinit var binder: ActivityNoteBinding
    private lateinit var toolbar: Toolbar
    private lateinit var txtToolbarTitle: TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var fabAddNote: FloatingActionButton

    //Category from bundle
    private lateinit var category: Category

    //ActivityResult stuff
    lateinit var returnedNote: Note
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>

    //ViewModel
    private lateinit var noteViewModel:NoteViewModel
    private lateinit var catViewModel:CategoryViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binder = ActivityNoteBinding.inflate(layoutInflater)
        setContentView(binder.root)
        init()
        retrieveCategory()
        initToolbar(category.name)
        initViewModels()
        handleCreatedNoteCallback()

    }

    private fun init() {

        toolbar = binder.activityNoteToolbar
        txtToolbarTitle = binder.activityNoteTxtToolbarTitle
        recyclerView = binder.activityNoteRecyclerView
        fabAddNote = binder.activityNoteFabAddNote
        fabAddNote.setOnClickListener(this)

    }

    private fun insertNewNote(note: Note) {

        noteViewModel.insert(note)

    }

    private fun retrieveCategory() {

        val bundle = intent.extras

        bundle?.let {
            category = it.get(getString(R.string.category_key)) as Category
        }

    }

    private fun initToolbar(catName: String?) {

        setSupportActionBar(toolbar)

        supportActionBar?.let {
            title = null
            it.setDisplayHomeAsUpEnabled(true)
            it.setHomeAsUpIndicator(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_round_arrow_light_24
                )
            )
        }

        txtToolbarTitle.text = catName ?: "debug"

    }

    private fun handleCreatedNoteCallback() {

        resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

                if (result.resultCode == RESULT_OK) {
                    returnedNote = result.data?.extras?.get(getString(R.string.note_key)) as Note
                    Log.i("Porsche", ": ${returnedNote.photoUri}")
                    insertNewNote(returnedNote)
                }

            }

    }

    private fun initViewModels(){

        noteViewModel = ViewModelProvider(this,NoteViewModelFactory((application as MemoriaApplication).noteRepository)).get(NoteViewModel::class.java)
        catViewModel = ViewModelProvider(this,CategoryViewModelFactory((application as MemoriaApplication).catRepository)).get(CategoryViewModel::class.java)


    }

    override fun onNavigateUp(): Boolean {

        finish()

        return true
    }

    override fun onClick(p0: View?) {

        when (p0?.id) {

            fabAddNote.id -> {

                val intent = Intent(this, AddNoteActivity::class.java)
                intent.putExtra(getString(R.string.category_key), category)
                resultLauncher.launch(intent)
            }

        }
    }

}