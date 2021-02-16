package com.bacchoterra.memoriav3.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bacchoterra.memoriav3.R
import com.bacchoterra.memoriav3.databinding.BtmSheetPasswordBinding
import com.bacchoterra.memoriav3.utils.PrefsUtil
import com.bacchoterra.memoriav3.utils.SnackBarUtil
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import java.util.*

class PasswordBottomSheet : BottomSheetDialogFragment() {

    //Context
    private lateinit var mContext:Context

    //Layout comp
    private lateinit var binder: BtmSheetPasswordBinding

    //SnackBar
    private lateinit var snackBarUtil: SnackBarUtil

    //PrefsUtil
    private lateinit var prefsUtil: PrefsUtil

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binder = BtmSheetPasswordBinding.inflate(inflater)
        prefsUtil = PrefsUtil(mContext)
        snackBarUtil = SnackBarUtil.getInstance()

        binder.btmBtnChangePassword.setOnClickListener {
            checkFavAnimal()
        }

        binder.btmBtnSaveNewPassword.setOnClickListener {
            saveNewPassword()
        }

        return binder.root

    }

    private fun checkFavAnimal() {

        val favAnimal = prefsUtil.getSavedFavAnimal()

        if (favAnimal == binder.btmEditFavAnimal.text.toString().toLowerCase(Locale.ROOT)) {
            changeLayoutsVisibility()
        }else{
            binder.btmInputFavAnimal.error = activity?.getString(R.string.wrong_security_word)
        }

    }

    private fun saveNewPassword(){

        val newPassword = binder.btmEditNewPassword.text.toString().trim()

        if (newPassword.length >= 4){
            prefsUtil.savePassword(newPassword)
            Toast.makeText(mContext,R.string.password_change_successful,Toast.LENGTH_LONG).show()
            dismiss()
        }else{
            binder.btmInputPassword.error = getString(R.string.min_4_chars)
        }


    }

    private fun changeLayoutsVisibility() {

        binder.btmLinearLayoutAnimal.visibility = View.GONE
        binder.btmLinearLayoutPassword.visibility = View.VISIBLE



    }


}



