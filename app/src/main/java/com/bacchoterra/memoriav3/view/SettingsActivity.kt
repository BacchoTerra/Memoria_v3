package com.bacchoterra.memoriav3.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.Toast
import com.bacchoterra.memoriav3.R
import com.bacchoterra.memoriav3.databinding.ActivitySettingsBinding
import com.bacchoterra.memoriav3.databinding.BtmSheetPasswordBinding
import com.bacchoterra.memoriav3.fragments.PasswordBottomSheet
import com.bacchoterra.memoriav3.utils.PrefsUtil
import com.bacchoterra.memoriav3.utils.SnackBarUtil
import com.google.android.material.snackbar.Snackbar
import java.util.*

class SettingsActivity : AppCompatActivity() {

    //Layout binder
    private lateinit var binder: ActivitySettingsBinding

    //SharedPreferences util
    private lateinit var prefsUtil: PrefsUtil

    //SnackBar
    private lateinit var snackBarUtil: SnackBarUtil


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binder = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binder.root)
        prefsUtil = PrefsUtil(this)
        snackBarUtil = SnackBarUtil.getInstance()
        //prefsUtil.nukePrefs()


        initToolbar()
        handleSavedUserNameFetch()
        handleUserPasswordFetch()
        binder.btnSave.setOnClickListener {
            saveUserInfo()
        }
        binder.btnForgotPassword.setOnClickListener {
            val bottomSheet = PasswordBottomSheet()
            bottomSheet.show(supportFragmentManager,null)

        }



    }

    private fun initToolbar(){

        val toolbar =  binder.toolbar

        setSupportActionBar(toolbar)
        supportActionBar?.let {

            title = null
            it.setDisplayHomeAsUpEnabled(true)

        }


    }

    private fun handleSavedUserNameFetch() {

        val name = prefsUtil.getSavedUserName()
        val hasNameSaved = name != PrefsUtil.NO_NAME_VALUE

        if (hasNameSaved) {
            binder.editName.setText(name)
            Log.i("Rola", "handleSavedUserName: Porsche")
        } else {
            Log.i("Rola", "handleSavedUserName: ${prefsUtil.getSavedUserName()}")

        }

    }

    private fun handleUserPasswordFetch() {

        val password = prefsUtil.getSavedPassword()
        val hasPasswordSaved = password != PrefsUtil.NO_PASSWORD_VALUE


        if (hasPasswordSaved) {
           hidePasswordLayout()
        }

    }

    private fun hidePasswordLayout(){
        binder.passLayout.visibility = View.GONE
        binder.btnForgotPassword.visibility = View.VISIBLE
    }

    private fun canSaveName(): Boolean {

        val name = binder.editName.text.toString().trim()
        return name.isNotEmpty()
    }

    private fun canSavePassword(): Boolean {

        val password = binder.editPassword.text.toString().trim()
        return password.length >= 4

    }

    private fun canSaveFavAnimal(): Boolean {

        val favAnimal = binder.editFavAnimal.text.toString().trim()
        return favAnimal.length >= 4

    }

    private fun saveUserInfo() {

        val hasToPassPassword = binder.passLayout.visibility == View.VISIBLE

        when {
            hasToPassPassword -> checkAndSaveNameAndPassword()

            !hasToPassPassword -> checkAndSaveNameOnly()

        }

    }

    private fun checkAndSaveNameOnly() {
        if (canSaveName()) {
            prefsUtil.saveUserName(binder.editName.text.toString())
            snackBarUtil.showPositiveSnackBar(this,binder.root,R.string.name_updated,Snackbar.LENGTH_LONG)
        } else {
            binder.editName.error = "*"
        }
    }

    private fun checkAndSaveNameAndPassword() {
        if (canSaveName()) {
            if (canSavePassword()) {
                if (canSaveFavAnimal()) {
                    prefsUtil.saveUserName(binder.editName.text.toString())
                    prefsUtil.savePassword(binder.editPassword.text.toString())
                    prefsUtil.saveFavAnimal(binder.editFavAnimal.text.toString().toLowerCase(Locale.ROOT))
                    snackBarUtil.showPositiveSnackBar(this,binder.root,R.string.settings_updated,Snackbar.LENGTH_LONG)
                    hidePasswordLayout()


                } else {
                    binder.editFavAnimal.error = "*"
                }

            } else {
                binder.editPassword.error = "Minimum 4 chars"
            }

        } else {
            binder.editName.error = "*"

        }

    }

    override fun onSupportNavigateUp(): Boolean {

        finish()

        return true
    }


}
