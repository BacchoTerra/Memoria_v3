package com.bacchoterra.memoriav3.view

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bacchoterra.memoriav3.databinding.ActivityMainBinding
import com.bacchoterra.memoriav3.utils.TabLayoutUtil
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems

class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binder = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binder.root)
        bindTabLayout(binder)

    }

    private fun bindTabLayout(binder:ActivityMainBinding){

        val tabLayoutUtil = TabLayoutUtil(supportFragmentManager,this,binder.ViewPager,binder.TabLayout)
        tabLayoutUtil.createAdapter()

    }

}