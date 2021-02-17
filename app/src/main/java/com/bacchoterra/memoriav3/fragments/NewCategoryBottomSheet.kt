package com.bacchoterra.memoriav3.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.bacchoterra.memoriav3.R
import com.bacchoterra.memoriav3.databinding.SheetNewCategoryBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.lang.ClassCastException

class NewCategoryBottomSheet : BottomSheetDialogFragment() {

    //Context
    private lateinit var mContext:Context

    //Layout components
    private lateinit var binder: SheetNewCategoryBinding

    //Listener
    private lateinit var mListener: CategoryCreatedListener

    override fun onAttach(context: Context) {
        super.onAttach(context)

        mContext = context


        try {
            mListener = parentFragment as CategoryCreatedListener
        } catch (e: ClassCastException) {
            throw ClassCastException("Must implement CategoryCreatedListener")
        }

    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binder = SheetNewCategoryBinding.inflate(inflater)

        binder.btnSaveCategory.background = ContextCompat.getDrawable(mContext, R.color.lightBlueBackground)
        createCategory()

        return binder.root
    }

    private fun createCategory() {

        binder.btnSaveCategory.setOnClickListener {
            if (binder.editCategoryTitle.text.toString().trim().isNotBlank()) {
                mListener.onCategoryCreated(
                    binder.editCategoryTitle.text.toString(),
                    binder.switchIsLocked.isChecked)

                dismiss()
            } else {
                binder.inputCategoryTitle.error = "*"
            }
        }

    }

    interface CategoryCreatedListener {

        fun onCategoryCreated(title: String, isLocked: Boolean)

    }

}