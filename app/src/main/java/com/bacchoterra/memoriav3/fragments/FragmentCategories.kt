package com.bacchoterra.memoriav3.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bacchoterra.memoriav3.MemoriaApplication
import com.bacchoterra.memoriav3.R
import com.bacchoterra.memoriav3.adapter.CategoriesAdapter
import com.bacchoterra.memoriav3.databinding.FragmentCategoriesBinding
import com.bacchoterra.memoriav3.model.Category
import com.bacchoterra.memoriav3.viewmodel.CategoryViewModel
import com.bacchoterra.memoriav3.viewmodel.CategoryViewModelFactory


class FragmentCategories : Fragment(), NewCategoryBottomSheet.CategoryCreatedListener,
    CategoriesAdapter.OnMenuItemSelectedListener {

    //ViewModel
    private val catViewModel by lazy {
        ViewModelProvider(
            this,
            CategoryViewModelFactory((requireActivity().application as MemoriaApplication).catRepository)
        ).get(CategoryViewModel::class.java)
    }


    //Layout
    private lateinit var binder: FragmentCategoriesBinding

    //Recycler adapter
    private lateinit var adapter: CategoriesAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binder = FragmentCategoriesBinding.inflate(inflater)

        initRecyclerView()

        binder.fabAddCategory.setOnClickListener {

            val categoryBottomSheet = NewCategoryBottomSheet()
            categoryBottomSheet.show(childFragmentManager, null)

        }

        observeData()


        return binder.root
    }

    private fun initRecyclerView() {

        //binder.categoriesRecyclerView.layoutManager = GridLayoutManager(requireContext(),2,GridLayoutManager.VERTICAL,false)
        binder.categoriesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = CategoriesAdapter(requireContext(), this)
        binder.categoriesRecyclerView.adapter = adapter
        binder.categoriesRecyclerView.setHasFixedSize(true)

    }

    private fun observeData() {

        catViewModel.getAllCategories().observe(viewLifecycleOwner, {
            it?.let {
                if (it.isEmpty()) {
                    binder.imageNoItens.visibility = View.VISIBLE
                    adapter.submitList(it)
                } else {
                    adapter.submitList(it)
                    binder.imageNoItens.visibility = View.GONE
                }

            }
        })

    }

    override fun onCategoryCreated(title: String, isLocked: Boolean) {
        val cat = Category(title, isLocked)
        catViewModel.insert(cat)
    }

    override fun onDelete(category: Category) {
        catViewModel.delete(category)
        Toast.makeText(requireContext(), "excluded", Toast.LENGTH_LONG).show()
    }

    override fun onFavorite(category: Category) {

    }
}