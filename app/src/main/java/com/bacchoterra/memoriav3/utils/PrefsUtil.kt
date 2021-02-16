package com.bacchoterra.memoriav3.utils

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.bacchoterra.memoriav3.R

class PrefsUtil(private val context: Context) {

    companion object {
        const val NO_PASSWORD_VALUE = 0
        const val NO_NAME_VALUE = "no_name"
        const val NO_ANIMAL_VALUE = "no_animal"
    }

    private var prefsArchive: SharedPreferences = context.getSharedPreferences(
        context.getString(R.string.preference_arch_key),
        Context.MODE_PRIVATE
    )


    fun getSavedUserName(): String? {
        return prefsArchive.getString(context.getString(R.string.saved_user_name_key), NO_NAME_VALUE)
    }

    fun getSavedPassword(): Int {

        return prefsArchive.getInt(
            context.getString(R.string.saved_password_key),
            NO_PASSWORD_VALUE
        )
    }

    fun getSavedFavAnimal(): String? {
        return prefsArchive.getString(context.getString(R.string.saved_fav_animal_key), NO_ANIMAL_VALUE)
    }

    fun saveUserName(name: String) {

        prefsArchive.edit()?.apply {
            putString(context.getString(R.string.saved_user_name_key), name)
            apply()
        }

        Log.i("Porsche", "saveName: $name")
    }

    fun savePassword(password: String) {

        val intValue = Integer.valueOf(password)

        prefsArchive.edit().apply {
            putInt(context.getString(R.string.saved_password_key), intValue)
            apply()
        }
        Log.i("Porsche", "savePassword: $password")
    }

    fun saveFavAnimal(animal: String) {
        prefsArchive.edit().apply {

            putString(context.getString(R.string.saved_fav_animal_key), animal)
            apply()
        }
        Log.i("Porsche", "saveAnimal: $animal")

    }

    fun nukePrefs() {

        prefsArchive.edit().apply {

            clear()
            apply()

        }
    }

}