package com.bacchoterra.memoriav3.view

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.viewpager.widget.ViewPager
import com.bacchoterra.memoriav3.MemoriaApplication
import com.bacchoterra.memoriav3.databinding.ActivityMainBinding
import com.bacchoterra.memoriav3.utils.PrefsUtil
import com.bacchoterra.memoriav3.utils.TabLayoutUtil
import com.bacchoterra.memoriav3.viewmodel.CategoryViewModel
import com.bacchoterra.memoriav3.viewmodel.CategoryViewModelFactory
import com.ogaclejapan.smarttablayout.SmartTabLayout
import java.util.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    //Layout components
    private lateinit var binder: ActivityMainBinding
    private lateinit var txtUserName: TextView
    private lateinit var txtDate: TextView
    private lateinit var smartTabLayout: SmartTabLayout
    private lateinit var viewPager: ViewPager
    private lateinit var btnNewCategory: Button
    private lateinit var imageSettings: ImageView

    //ViewModel

    private val catViewModel: CategoryViewModel by viewModels {
        CategoryViewModelFactory((application as MemoriaApplication).catRepository)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binder = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binder.root)
        init()
        bindTabLayout()
        displayCurrentTime()
        getUserName()

    }

    private fun init() {

        txtUserName = binder.activityMainTxtUserName
        txtDate = binder.activityMainTxtDate
        smartTabLayout = binder.activityMainTabLayout
        imageSettings = binder.activityMainImageSettings
        imageSettings.setOnClickListener(this)
        viewPager = binder.activityMainViewPager


    }

    private fun bindTabLayout() {

        val tabLayoutUtil = TabLayoutUtil(supportFragmentManager, this, viewPager, smartTabLayout)
        tabLayoutUtil.createAdapter()

    }

    @SuppressLint("SetTextI18n")
    private fun displayCurrentTime() {

        val calendar = Calendar.getInstance()

        txtDate.text = "${
            calendar.getDisplayName(
                Calendar.MONTH,
                Calendar.SHORT,
                Locale.getDefault()
            )
        } , ${calendar.get(Calendar.YEAR)}"

    }

    private fun getUserName() {

        val prefsUtil = PrefsUtil(this)
        val name = prefsUtil.getSavedUserName()

        if (name != PrefsUtil.NO_NAME_VALUE) {
            txtUserName.text = name
        }

    }

    override fun onClick(p0: View?) {
        when (p0?.id) {

            imageSettings.id -> startActivity(Intent(this, SettingsActivity::class.java))

        }
    }

}