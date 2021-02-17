package com.bacchoterra.memoriav3.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bacchoterra.memoriav3.R
import com.bacchoterra.memoriav3.model.Category
import com.bacchoterra.memoriav3.viewmodel.CategoryViewModel

class CategoriesAdapter(val context:Context, private val mListener:OnMenuItemSelectedListener): ListAdapter<Category, CategoriesAdapter.MyViewHolder>(CategoryComparator()) {

    private var count = 0



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemList = LayoutInflater.from(parent.context).inflate(R.layout.row_category,parent,false)
        return MyViewHolder(itemList)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val cat = getItem(position)

        holder.txtTitle.text = cat.name

        if (cat.lastNoteBody.isBlank()){
            holder.txtLastNote.text = context.getString(R.string.no_last_note_body)
        }else{
            holder.txtLastNote.text = cat.lastNoteBody
        }

        if (cat.isLocked){
            holder.imageLocked.visibility = View.VISIBLE
        }else{
            holder.imageLocked.visibility = View.GONE
        }

        dynamicChangeBackground(holder)

        holder.background.setOnLongClickListener {
            showMenu(holder)
            Log.i("Porsche", "onBindViewHolder: Long click")
            true
        }


    }

    private fun dynamicChangeBackground(holder: MyViewHolder){

        if (count ==0){
            holder.background.background = ContextCompat.getDrawable(context,R.drawable.shape_cat_1)
            count ++
        }else if(count ==1){
            holder.background.background = ContextCompat.getDrawable(context,R.drawable.shape_cat_2)
            count ++
        }else{
            holder.background.background = ContextCompat.getDrawable(context,R.drawable.shape_cat_3)
            count = 0
        }

    }

    private fun showMenu(holder: MyViewHolder){

        val menu = PopupMenu(context,holder.background)
        menu.menuInflater.inflate(R.menu.menu_category_row,menu.menu)

        menu.setOnMenuItemClickListener {
            when(it.itemId){

                R.id.menu_category_row_delete -> mListener.onDelete(getItem(holder.adapterPosition))
                R.id.menu_category_row_favorite -> Log.i("Porsche", "showMenu: favorite")

            }

            true
        }


        menu.show()


    }

    class MyViewHolder(itemList:View): RecyclerView.ViewHolder(itemList) {

        val txtTitle: TextView = itemList.findViewById(R.id.row_txtTitle)
        val txtLastNote: TextView = itemList.findViewById(R.id.row_txtLastNote)
        val imageLocked: ImageView = itemList.findViewById(R.id.row_imageIsLocked)
        var background: ConstraintLayout = itemList.findViewById(R.id.row_Background)

    }

    class CategoryComparator: DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {

            return oldItem.name == newItem.name && oldItem.lastNoteBody == newItem.lastNoteBody == oldItem.isLocked == newItem.isLocked && oldItem.id == newItem.id


        }

    }

    interface OnMenuItemSelectedListener{

        fun onDelete(category: Category)

        fun onFavorite(category: Category)

    }

}