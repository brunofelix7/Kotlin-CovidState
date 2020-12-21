package com.brunofelixdev.kotlincovidstate.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.brunofelixdev.kotlincovidstate.R
import com.brunofelixdev.kotlincovidstate.adapter.CountryDataAdapter
import com.brunofelixdev.kotlincovidstate.data.api.repository.DataRepository
import com.brunofelixdev.kotlincovidstate.databinding.ActivityHomeBinding
import com.brunofelixdev.kotlincovidstate.extension.dateFormatted
import com.brunofelixdev.kotlincovidstate.extension.fatalityRate
import com.brunofelixdev.kotlincovidstate.extension.formatNumber
import com.brunofelixdev.kotlincovidstate.extension.recoveredRate
import com.brunofelixdev.kotlincovidstate.fragment.MapsFragment
import com.brunofelixdev.kotlincovidstate.fragment.RecentFragment
import com.brunofelixdev.kotlincovidstate.listener.DataListener
import com.brunofelixdev.kotlincovidstate.model.CountryData
import com.brunofelixdev.kotlincovidstate.model.WorldData
import com.brunofelixdev.kotlincovidstate.util.EXTRAS_KEY_COUNTRY_NAME
import com.brunofelixdev.kotlincovidstate.viewmodel.DataViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.*
import kotlin.collections.ArrayList


class HomeActivity : AppCompatActivity(), DataListener {

    private var binding: ActivityHomeBinding? = null
    private var viewModel: DataViewModel? = null
    private var isSearched: Boolean = false
    private var displayCountryList = ArrayList<CountryData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingConfig()
        toolbarConfig()
        searchConfig()
        fetchData()
    }

    private fun fragmentsConfig() {
        val recentFragment = RecentFragment()
        val mapsFragment = MapsFragment()
        binding?.navMenu?.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_recent -> {

                }
            }
            true
        }
    }

//    private fun makeCurrentFragment(fragment: Fragment) {
//        supportFragmentManager.beginTransaction().apply {
//            replace(R.id., fragment)
//            commit()
//        }
//    }

    private fun bindingConfig() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        viewModel = ViewModelProvider(
            this,
            DataViewModel.DataViewModelFactory(DataRepository())
        ).get(DataViewModel::class.java)

        binding?.viewModel = viewModel
        viewModel?.listener = this
    }

    private fun searchConfig() {
        binding?.countrySearch?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val adapter = CountryDataAdapter(
                    displayCountryList.sortedByDescending { item -> item.confirmed },
                    this@HomeActivity
                )
                adapter.filter.filter(newText)
                binding?.rvCountries?.adapter = adapter
                binding?.rvCountries?.adapter?.notifyDataSetChanged()
                return false
            }

        })
    }

    private fun toolbarConfig() {
        binding?.includeToolbar?.toolbar?.title = resources.getString(R.string.app_name)
        binding?.includeToolbar?.toolbar?.inflateMenu(R.menu.action_menu)
        binding?.includeToolbar?.toolbar?.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.menu_search -> {
                    if (isSearched) {
                        isSearched = false
                        binding?.countrySearch?.visibility = View.GONE
                    } else {
                        isSearched = true
                        binding?.countrySearch?.visibility = View.VISIBLE
                    }
                    true
                }
                R.id.menu_sync -> {
                    //  TODO: buscar da api novamente
                    true
                }
                else -> super.onOptionsItemSelected(item)
            }
        }
    }

    private fun fetchData() {
        viewModel?.listAllData()
    }

    override fun onStarted() {
        //  TODO: Progress
    }

    override fun onCompletedWorldData(liveData: LiveData<List<WorldData>>) {
        liveData.observe(this, { data ->
            if (data != null && data.isNotEmpty()) {
                binding?.includeCards?.confirmedValue?.text = data[0].confirmed?.formatNumber()
                binding?.includeCards?.criticalValue?.text = data[0].critical?.formatNumber()
                binding?.includeCards?.deathValue?.text = data[0].deaths?.formatNumber()
                binding?.includeCards?.recoveredValue?.text = data[0].recovered?.formatNumber()
                binding?.includeCards?.deathRateValue?.text =
                    data[0].deaths?.fatalityRate(data[0].confirmed)
                binding?.includeCards?.recoveredRateValue?.text = data[0].recovered?.recoveredRate(
                    data[0].confirmed
                )
                binding?.includeFooter?.lastUpdateValue?.text = data[0].lastUpdate?.dateFormatted()
            }
        })
    }

    override fun onCompletedCountriesData(liveData: LiveData<List<CountryData>>) {
        liveData.observe(this, { data ->
            if (data != null && data.isNotEmpty()) {
                displayCountryList.addAll(data)
                binding?.rvCountries?.layoutManager = LinearLayoutManager(
                    this,
                    LinearLayoutManager.VERTICAL,
                    false
                )
                binding?.rvCountries?.setHasFixedSize(true)
                binding?.rvCountries?.adapter =
                    CountryDataAdapter(displayCountryList.sortedByDescending { field ->
                        field.confirmed
                    }, this)

                val searchItem = binding?.includeToolbar?.toolbar?.menu?.findItem(R.id.menu_search)
                searchItem?.isVisible = true
            }
        })
    }

    override fun onItemClick(view: View, country: String?) {
        startActivity(
            Intent(this, DetailsActivity::class.java).apply {
                putExtra(EXTRAS_KEY_COUNTRY_NAME, country)
            }
        )
    }
}