package com.bacchoterra.memoriav3.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bacchoterra.memoriav3.R
import com.bacchoterra.memoriav3.model.Note
import com.bacchoterra.memoriav3.view.DialogFragmentNoteImage
import java.util.*

class NotesAdapter(val context: Context, val fragmentManager: FragmentManager) :
    ListAdapter<Note, NotesAdapter.MyViewHolder>(NotesComparator()) {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val itemList =
            LayoutInflater.from(parent.context).inflate(R.layout.row_notes, parent, false)

        return MyViewHolder(itemList)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val note = getItem(position)

        if (note.noteTitle.isBlank()) {
            holder.txtTitle.visibility = View.GONE
        } else {
            holder.txtTitle.visibility = View.VISIBLE
            holder.txtTitle.text = note.noteTitle
        }

        if (note.photoUri.isBlank()) {
            holder.imageImage.visibility = View.GONE
        } else {
            holder.imageImage.visibility = View.VISIBLE
        }

        holder.txtBody.text = note.noteBody

        generateDate(holder, note)
        defineNoteImportance(holder, note)
        openImage(holder, note)


    }


    class MyViewHolder(itemList: View) : RecyclerView.ViewHolder(itemList) {


        val background: ConstraintLayout = itemList.findViewById(R.id.note_row_background)
        val txtTitle: TextView = itemList.findViewById(R.id.row_notes_txtTitle)
        val txtBody: TextView = itemList.findViewById(R.id.row_notes_txtBody)
        val txtDate: TextView = itemList.findViewById(R.id.row_notes_txtDate)
        val imageImage: ImageView = itemList.findViewById(R.id.row_notes_imageImage)


    }

    class NotesComparator : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.noteTitle == newItem.noteTitle && oldItem.noteBody == newItem.noteBody && oldItem.category == newItem.category && oldItem.id == newItem.id && oldItem.timeStamp == newItem.timeStamp && oldItem.photoUri == newItem.photoUri
        }

    }

    @SuppressLint("SetTextI18n")
    fun generateDate(holder:MyViewHolder, note: Note) {

        val calendar = Calendar.getInstance()

        calendar.timeInMillis = note.timeStamp

        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val month = calendar.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.getDefault())
        val year = calendar.get(Calendar.YEAR)


        holder.txtDate.text = "$day , $month , $year"

    }

    private fun defineNoteImportance(holder: MyViewHolder, note: Note) {


        if (note.importance == 0) holder.background.background =
            ContextCompat.getDrawable(context, R.drawable.shape_note_imp_0)
        if (note.importance == 1) holder.background.background =
            ContextCompat.getDrawable(context, R.drawable.shape_note_imp_1)
        if (note.importance == 2) holder.background.background =
            ContextCompat.getDrawable(context, R.drawable.shape_note_imp_2)


    }

    private fun openImage(holder: MyViewHolder, note: Note) {

        holder.imageImage.setOnClickListener {

            val frag = DialogFragmentNoteImage()
            val bundle = Bundle()

            bundle.putSerializable(context.getString(R.string.image_key), note)

            frag.arguments = bundle

            frag.show(fragmentManager, null)

        }

    }

    fun getNote(position:Int):Note{

        return getItem(position)

    }


}