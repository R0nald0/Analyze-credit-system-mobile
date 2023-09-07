package com.example.analyze_credit_system_mobile.view.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.analyze_credit_system_mobile.view.activity.fragments.tbafragments.AllOrderAccountListFragment
import com.example.analyze_credit_system_mobile.view.activity.fragments.tbafragments.LastMovimentsFragment


class PageViewListAdapter(maneger : FragmentManager,lifeCycle : Lifecycle) :FragmentStateAdapter(maneger,lifeCycle) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        when(position){
            0 ->return LastMovimentsFragment()
            1 -> return AllOrderAccountListFragment()
            else -> return LastMovimentsFragment()
        }
    }
}