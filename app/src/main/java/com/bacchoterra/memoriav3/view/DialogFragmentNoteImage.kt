package com.bacchoterra.memoriav3.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.bacchoterra.memoriav3.R
import com.bacchoterra.memoriav3.databinding.FragDialogImageBinding
import com.bacchoterra.memoriav3.model.Note
import com.bumptech.glide.Glide

class DialogFragmentNoteImage : DialogFragment() {

    //Layout components
    private lateinit var binder:FragDialogImageBinding

    //Note from arguments
    private lateinit var note:Note


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binder = FragDialogImageBinding.inflate(inflater)

        note = arguments?.get(getString(R.string.image_key)) as Note

        Glide.with(this).load(note.photoUri).into(binder.imageView)



        return binder.root
    }


}