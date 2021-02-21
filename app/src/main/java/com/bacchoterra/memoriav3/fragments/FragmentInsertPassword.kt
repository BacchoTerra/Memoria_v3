package com.bacchoterra.memoriav3.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.ViewGroup
import com.bacchoterra.memoriav3.R
import com.bacchoterra.memoriav3.databinding.FragmentInsertPasswordBinding
import com.bacchoterra.memoriav3.model.Category
import com.bacchoterra.memoriav3.utils.PrefsUtil
import com.bacchoterra.memoriav3.view.NoteActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.*

class FragmentInsertPassword : BottomSheetDialogFragment() {

    //Layout components
    private lateinit var binder: FragmentInsertPasswordBinding

    //Password components
    private lateinit var prefsUtil: PrefsUtil

    //Category from arguments
    private lateinit var cat: Category


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binder = FragmentInsertPasswordBinding.inflate(inflater)

        initPrefsUtil()
        retrieveCategory()

        binder.fragInsertPasswordBtnAccess.setOnClickListener {

            checkPassword()

        }

        return binder.root
    }

    private fun retrieveCategory() {
        cat = arguments?.get(getString(R.string.category_key)) as Category
    }

    private fun initPrefsUtil() {


        prefsUtil = PrefsUtil(requireContext())


    }

    private fun checkPassword() {


        if (binder.fragInsertPasswordEditPassword.text.toString().isBlank()) {
            binder.fragInsertPasswordInputLayout.error = "*"
        } else {

            val inputPassword =
                Integer.valueOf(binder.fragInsertPasswordEditPassword.text.toString().trim())
            val password = prefsUtil.getSavedPassword()

            if (inputPassword == password) {

                val intent = Intent(requireActivity(), NoteActivity::class.java)
                intent.putExtra(getString(R.string.category_key), cat)
                startActivity(intent)


            } else {

                binder.fragInsertPasswordInputLayout.error = getString(R.string.wrong_password)
            }


        }

    }


}