package com.dicoding.githubuserapp.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dicoding.githubuserapp.ui.fragment.FollowersFragment
import com.dicoding.githubuserapp.ui.fragment.FollowingFragment

class SectionsPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {

    var unameAdapter: String = ""

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = FollowersFragment()
            1 -> fragment = FollowingFragment()
        }
        fragment?.arguments = Bundle().apply {
           if (position == 0) {
               putString(FollowersFragment.username, unameAdapter)
           }
           else if (position == 1) {
               putString(FollowingFragment.username, unameAdapter)
           }
        }
        return fragment as Fragment
    }

}