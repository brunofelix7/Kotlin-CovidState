package com.brunofelixdev.kotlincovidstate.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.brunofelixdev.kotlincovidstate.R
import com.brunofelixdev.kotlincovidstate.databinding.ActivityHomeBinding
import com.brunofelixdev.kotlincovidstate.fragment.MapsFragment
import com.brunofelixdev.kotlincovidstate.fragment.RecentFragment
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class HomeActivity : AppCompatActivity(), KodeinAware {

    //  DI - Kodein initialize
    override val kodein by kodein()

    //  ViewBinding
    private lateinit var binding: ActivityHomeBinding

    //  Inject
    private val recentFragment: RecentFragment by instance()
    private val mapsFragment: MapsFragment by instance()

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