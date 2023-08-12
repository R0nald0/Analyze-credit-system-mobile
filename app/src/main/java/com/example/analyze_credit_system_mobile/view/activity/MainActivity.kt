package com.example.analyze_credit_system_mobile.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.analyze_credit_system_mobile.databinding.ActivityMainBinding
import com.example.analyze_credit_system_mobile.view.adapter.PageViewListAdapter
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    lateinit var viewPagerAdapter: PageViewListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        /*viewPagerAdapter = PageViewListAdapter(this.supportFragmentManager,this.lifecycle)
        binding.viewPagerList.adapter = viewPagerAdapter*/
        //configTabLayout()
    }

    fun configTabLayout(){

        TabLayoutMediator(binding.tabLayout,binding.viewPagerList){ tab,position ->
             when(position){
                 0 ->tab.text ="tba1"
                 1 ->tab.text ="tba1"
                 2 ->tab.text ="tba1"
                 else -> tab.text ="tba1"
             }
         }.attach()
    }

}