package com.bacchoterra.memoriav3.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bacchoterra.memoriav3.R
import com.bacchoterra.memoriav3.fragments.FragmentInsertPassword
import com.bacchoterra.memoriav3.model.Category
import com.bacchoterra.memoriav3.view.NoteActivity


class CategoriesAdapter(
    val context: Activity,
    private val mListener: OnMenuItemSelectedListener,
    val fragManager: FragmentManager
) : ListAdapter<Category, CategoriesAdapter.MyViewHolder>(CategoryComparator()){

    private var count = 0


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemList =
            LayoutInflater.from(parent.context).inflate(R.layout.row_category, parent, false)
        return MyViewHolder(itemList)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val cat = getItem(position)

        holder.txtTitle.text = cat.name

        setLastNoteBody(holder,cat)


        holder.imageMenu.setOnClickListener{
            showMenu(holder)
        }

        if (cat.isLocked) {
            holder.imageLocked.visibility = View.VISIBLE
        } else {
            holder.imageLocked.visibility = View.GONE
        }

        holder.background.setOnClickListener {

            checkForPassword(cat, position)

        }


    }

    private fun showMenu(holder: MyViewHolder) {

        val menu = PopupMenu(context, holder.imageMenu)
        menu.menuInflater.inflate(R.menu.menu_category_row, menu.menu)

        menu.setOnMenuItemClickListener {
            when (it.itemId) {

                R.id.menu_category_row_delete -> mListener.onDelete(getItem(holder.adapterPosition))
                R.id.menu_category_row_favorite -> Log.i("Porsche", "showMenu: favorite")

            }

            true
        }


        menu.show()


    }

    private fun openNotesActivity(position: Int) {

        val intent = Intent(context, NoteActivity::class.java).apply {
            putExtra(context.getString(R.string.category_key), getItem(position))
        }

        context.startActivity(intent)


    }

    private fun checkForPassword(cat: Category, position: Int) {

        if (cat.isLocked) {

            val fragmentInsertPassword = FragmentInsertPassword()
            val bundle = Bundle()
            bundle.putSerializable(context.getString(R.string.category_key),getItem(position))
            fragmentInsertPassword.arguments = bundle

            fragmentInsertPassword.show(fragManager, null)


        } else {
            openNotesActivity(position)
        }

    }

    @SuppressLint("SetTextI18n")
    private fun setLastNoteBody(holder: MyViewHolder, category: Category){

        if (category.lastNoteBody.isBlank()) {
            holder.txtLastNote.text = context.getString(R.string.no_last_note_body)
        } else {

            if (category.lastNoteBody.length > 100){
                holder.txtLastNote.text = "${category.lastNoteBody.substring(0, 100)} ..."
            }else{
                holder.txtLastNote.text = category.lastNoteBody
            }
        }



    }

    class MyViewHolder(itemList: View) : RecyclerView.ViewHolder(itemList) {

        val txtTitle: TextView = itemList.findViewById(R.id.row_txtTitle)
        val txtLastNote: TextView = itemList.findViewById(R.id.row_txtLastNote)
        val imageLocked: ImageView = itemList.findViewById(R.id.row_imageIsLocked)
        val imageMenu: ImageView = itemList.findViewById(R.id.row_imageMenu)
        var background: ConstraintLayout = itemList.findViewById(R.id.row_Background)

    }

    class CategoryComparator : DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {

            return oldItem.name == newItem.name && oldItem.lastNoteBody == newItem.lastNoteBody == oldItem.isLocked == newItem.isLocked && oldItem.id == newItem.id


        }

    }

    interface OnMenuItemSelectedListener {

        fun onDelete(category: Category)

        fun onFavorite(category: Category)

    }
}