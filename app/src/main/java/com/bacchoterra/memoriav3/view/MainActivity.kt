package com.bacchoterra.memoriav3.view

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bacchoterra.memoriav3.databinding.ActivityMainBinding
import com.bacchoterra.memoriav3.utils.TabLayoutUtil
import java.util.*

class MainActivity : AppCompatActivity(){



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binder = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binder.root)
        bindTabLayout(binder)
        displayCurrentTime(binder)

        binder.txtSettings.setOnClickListener{



            startActivity(Intent(this,SettingsActivity::class.java))

        }

    }

    private fun bindTabLayout(binder:ActivityMainBinding){

        val tabLayoutUtil = TabLayoutUtil(supportFragmentManager,this,binder.ViewPager,binder.TabLayout)
        tabLayoutUtil.createAdapter()

    }

    @SuppressLint("SetTextI18n")
    private fun displayCurrentTime(binder:ActivityMainBinding){

        val calendar = Calendar.getInstance()

        binder.TxtDate.text = "${calendar.getDisplayName(Calendar.MONTH,Calendar.SHORT,Locale.getDefault())} , ${calendar.get(Calendar.YEAR)}"

    }

}