package com.bacchoterra.memoriav3.utils

import android.content.Context
import android.view.View
import androidx.core.content.res.ResourcesCompat
import com.bacchoterra.memoriav3.R
import com.google.android.material.snackbar.Snackbar

class SnackBarUtil {

    companion object {

        private var instance: SnackBarUtil? = null

        public fun getInstance(): SnackBarUtil {
            return instance ?: SnackBarUtil()
        }
    }

    fun showSimpleSnackBar(view: View, message: String, length: Int) {
        Snackbar.make(view, message, length).show()

    }

    fun showSimpleSnackBar(view: View, message: Int, length: Int) {
        Snackbar.make(view, message, length).show()
    }

    fun showPositiveSnackBar(context:Context,view: View, message: Int, length: Int) {

        val snackBar = Snackbar.make(view, message, length)
        snackBar.setBackgroundTint(
            ResourcesCompat.getColor(
                context.resources,
                R.color.successfulGreenColor,
                null
            )
        )
        snackBar.show()
    }

    fun showPositiveSnackBar(context:Context,view: View, message: String, length: Int) {

        val snackBar = Snackbar.make(view, message, length)
        snackBar.setBackgroundTint(
            ResourcesCompat.getColor(
                context.resources,
                R.color.successfulGreenColor,
                null
            )
        )
        snackBar.show()
    }

}