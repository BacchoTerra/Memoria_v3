package com.bacchoterra.memoriav3.view

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>

    //ViewModel
    private val categoryViewModel: CategoryViewModel by viewModels {
        CategoryViewModelFactory((application as MemoriaApplication).catRepository)
    }
    private val noteViewModel: NoteViewModel by lazy {
        ViewModelProvider(this, NoteViewModelFactory((application as MemoriaApplication).noteRepository)).get(NoteViewModel::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binder = ActivityNoteBinding.inflate(layoutInflater)
        setContentView(binder.root)
        init()
        retrieveCategory()
        initToolbar(category.name)
        setUpActivityResult()

    }

    private fun init() {

        toolbar = binder.activityNoteToolbar
        txtToolbarTitle = binder.activityNoteTxtToolbarTitle
        recyclerView = binder.activityNoteRecyclerView
        fabAddNote = binder.activityNoteFabAddNote
        fabAddNote.setOnClickListener(this)

    }

    private fun setUpActivityResult() {
        resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {

                if (it.resultCode == Activity.RESULT_OK) {
                    val data: Intent? = it.data

                }
            }
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

    override fun onNavigateUp(): Boolean {

        finish()

        return true
    }

    override fun onClick(p0: View?) {

        when (p0?.id) {

            fabAddNote.id -> resultLauncher.launch(Intent(this, AddNoteActivity::class.java))

        }
    }

}