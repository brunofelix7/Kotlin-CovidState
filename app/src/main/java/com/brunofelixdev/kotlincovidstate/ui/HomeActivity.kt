package com.brunofelixdev.kotlincovidstate.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.brunofelixdev.kotlincovidstate.R
import com.brunofelixdev.kotlincovidstate.databinding.ActivityHomeBinding
import com.brunofelixdev.kotlincovidstate.fragment.MapsFragment
import com.brunofelixdev.kotlincovidstate.fragment.RecentFragment
import org.koin.android.ext.android.inject

class HomeActivity : AppCompatActivity() {

    //  ViewBinding
    private lateinit var binding: ActivityHomeBinding

    //  DI - Koin inject
    private val recentFragment: RecentFragment by inject()
    private val mapsFragment: MapsFragment by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeViews()
        initializeFragments()
    }

    private fun initializeViews() {
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    private fun initializeFragments() {
        makeCurrentFragment(recentFragment)
        binding.navMenu.setOnNavigationItemSelectedListener { item ->
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