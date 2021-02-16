package com.bacchoterra.memoriav3.view

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bacchoterra.memoriav3.databinding.ActivityMainBinding
import com.bacchoterra.memoriav3.utils.PrefsUtil
import com.bacchoterra.memoriav3.utils.TabLayoutUtil
import java.util.*

class MainActivity : AppCompatActivity(){

    private lateinit var binder: ActivityMainBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binder = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binder.root)
        bindTabLayout()
        displayCurrentTime()
        getUserName()

        binder.txtSettings.setOnClickListener{



            startActivity(Intent(this,SettingsActivity::class.java))

        }

    }

    private fun bindTabLayout(){

        val tabLayoutUtil = TabLayoutUtil(supportFragmentManager,this,binder.ViewPager,binder.TabLayout)
        tabLayoutUtil.createAdapter()

    }

    @SuppressLint("SetTextI18n")
    private fun displayCurrentTime(){

        val calendar = Calendar.getInstance()

        binder.TxtDate.text = "${calendar.getDisplayName(Calendar.MONTH,Calendar.SHORT,Locale.getDefault())} , ${calendar.get(Calendar.YEAR)}"

    }

    private fun getUserName(){

        val prefsUtil = PrefsUtil(this)
        val name = prefsUtil.getSavedUserName()

        if (name != PrefsUtil.NO_NAME_VALUE){
            binder.txtUserName.text = name
        }

    }

}