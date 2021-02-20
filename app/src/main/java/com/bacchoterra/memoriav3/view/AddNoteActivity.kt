package com.bacchoterra.memoriav3.view

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.ColorStateList
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.bacchoterra.memoriav3.R
import com.bacchoterra.memoriav3.databinding.ActivityAddNoteBinding
import com.bacchoterra.memoriav3.model.Category
import com.bacchoterra.memoriav3.model.Note
import com.vmadalin.easypermissions.EasyPermissions
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class AddNoteActivity : AppCompatActivity(), View.OnClickListener {

    //Layout components
    private lateinit var binder: ActivityAddNoteBinding
    private lateinit var toolbar: Toolbar
    private lateinit var txtDate: TextView
    private lateinit var editTitle: EditText
    private lateinit var editBody: EditText
    private lateinit var seekImportance: SeekBar
    private lateinit var imageGallery: ImageView
    private lateinit var imageCamera: ImageView
    private lateinit var btnSave: Button

    //Category from bundle
    private lateinit var cat: Category
    private var imageUri:String? = null

    //Note attributes

    private lateinit var note:Note

    //Date Components
    private var currentTimeStamp: Long? = null

    //Permissions stuff
    companion object {
        const val READ_EXTERNAL_STORAGE = android.Manifest.permission.READ_EXTERNAL_STORAGE
        const val CAMERA = android.Manifest.permission.CAMERA
        const val GALLERY_PERM_CODE = 100
        const val CAMERA_PERM_CODE = 200
    }

    private lateinit var galleryLauncher: ActivityResultLauncher<Intent>

    //Camera intent contract

    private lateinit var cameraLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binder = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binder.root)
        init()
        retrieveCategory()
        initToolbar()
        defineCurrentDate()
        handleGalleryCallback()
        handleCameraCallback()


    }

    private fun init() {

        toolbar = binder.activityAddNoteToolbar
        txtDate = binder.activityAddNoteTxtDate
        editTitle = binder.activityAddNoteEditNoteTitle
        editBody = binder.activityAddNoteEditNoteBody
        seekImportance = binder.activityAddNoteSeekBarImportance
        imageGallery = binder.activityAddNoteImageGallery
        imageGallery.setOnClickListener(this)
        imageCamera = binder.activityAddNoteImageCamera
        imageCamera.setOnClickListener(this)
        btnSave = binder.activityAddNoteBtnSaveNote
        btnSave.setOnClickListener(this)


    }

    private fun retrieveCategory() {

        cat = intent.extras?.get(getString(R.string.category_key)) as Category

    }

    private fun initToolbar() {

        setSupportActionBar(toolbar)

        supportActionBar?.let {

            it.setDisplayHomeAsUpEnabled(true)
            it.title = null

        }
    }

    @SuppressLint("SetTextI18n")
    private fun defineCurrentDate() {

        val calendar = Calendar.getInstance()
        currentTimeStamp = calendar.timeInMillis

        val month = calendar.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.getDefault())
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val year = calendar.get(Calendar.YEAR)

        txtDate.text = "$month, $day, $year"


    }

    private fun openGallery(): String {


        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        galleryLauncher.launch(intent)

        return ""
    }

    private fun handleGalleryCallback() {

        galleryLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

                if (result.resultCode == RESULT_OK) {
                    val data: Intent? = result.data
                    imageUri = data?.data?.toString()!!
                    imageGallery.imageTintList = ColorStateList.valueOf(
                        ContextCompat.getColor(
                            this,
                            R.color.successfulGreenColor
                        )
                    )
                    imageCamera.imageTintList = ColorStateList.valueOf(
                        ContextCompat.getColor(
                            this,
                            R.color.darkBlueBackground
                        )
                    )

                }else{
                    imageUri = null
                }

            }

    }

    private fun handleGalleryPerm() {

        if (EasyPermissions.hasPermissions(this, READ_EXTERNAL_STORAGE)) {
            openGallery()
        } else {

            EasyPermissions.requestPermissions(
                this,
                getString(R.string.rationale_ask),
                GALLERY_PERM_CODE,
                READ_EXTERNAL_STORAGE
            )

        }


    }

    private fun handleCameraPerm() {
        if (EasyPermissions.hasPermissions(this, CAMERA)) {
            dispatchTakePictureIntent()
        } else {
            EasyPermissions.requestPermissions(
                this, getString(R.string.rationale_ask),
                CAMERA_PERM_CODE, CAMERA
            )
        }

    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String =
            SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir: File = getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            imageUri = absolutePath
        }
    }

    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            // Ensure that there's a camera activity to handle the intent
            takePictureIntent.resolveActivity(packageManager)?.also {
                // Create the File where the photo should go
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    // Error occurred while creating the File
                    null
                }
                // Continue only if the File was successfully created
                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        this,
                        "com.bacchoterra.memoriav3.fileprovider",
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    cameraLauncher.launch(takePictureIntent)
                }
            }
        }
    }

    private fun handleCameraCallback() {

        cameraLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

                if (result.resultCode == RESULT_OK) {
                    Log.i("Rola", ": $imageUri")
                    imageCamera.imageTintList = ColorStateList.valueOf(
                        ContextCompat.getColor(
                            this,
                            R.color.successfulGreenColor
                        )
                    )
                    imageGallery.imageTintList = ColorStateList.valueOf(
                        ContextCompat.getColor(
                            this,
                            R.color.darkBlueBackground
                        )
                    )
                }else{
                    imageUri = null
                }


            }


    }

    private fun createNote(){

        val title = editTitle.text.toString().trim()
        val body = editBody.text.toString().trim()
        val importance = seekImportance.progress
        val timeStamp = System.currentTimeMillis()
        //ImageUri is being defined inside handles for imagesCallback

        note = Note(title,body,cat.name,timeStamp,importance,imageUri)
        Log.i("Porsche", "createNote: ${note.noteTitle} , ${note.photoUri} , ${note.timeStamp} , ${note.noteBody}, ${note.importance}")

    }

    override fun onNavigateUp(): Boolean {
        finish()
        return true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {

            imageGallery.id -> handleGalleryPerm()

            imageCamera.id -> handleCameraPerm()

            btnSave.id -> {

                if (editBody.text.trim().isNotEmpty()){
                    createNote()
                }else{
                    editBody.error = "*"
                }

            }
        }
    }

}