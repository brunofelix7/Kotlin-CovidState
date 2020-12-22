package com.brunofelixdev.kotlincovidstate.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.brunofelixdev.kotlincovidstate.R
import com.brunofelixdev.kotlincovidstate.databinding.ActivityHomeBinding
import com.brunofelixdev.kotlincovidstate.fragment.MapsFragment
import com.brunofelixdev.kotlincovidstate.fragment.RecentFragment

class HomeActivity : AppCompatActivity() {

    private var binding: ActivityHomeBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentsConfig()
    }

    private fun fragmentsConfig() {
        val recentFragment = RecentFragment()
        val mapsFragment = MapsFragment()

        makeCurrentFragment(recentFragment)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        binding?.navMenu?.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_recent -> makeCurrentFragment(recentFragment)
                R.id.nav_maps -> makeCurrentFragment(mapsFragment)
            }
            true
        }
    }

    private fun makeCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frame_layout, fragment)
            commit()
        }
    }

}