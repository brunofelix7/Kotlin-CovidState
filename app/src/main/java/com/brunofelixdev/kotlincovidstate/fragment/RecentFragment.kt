package com.brunofelixdev.kotlincovidstate.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.brunofelixdev.kotlincovidstate.R
import com.brunofelixdev.kotlincovidstate.adapter.CountryDataAdapter
import com.brunofelixdev.kotlincovidstate.data.api.interceptor.NetworkConnectionInterceptor
import com.brunofelixdev.kotlincovidstate.data.api.repository.WorldTotalRepository
import com.brunofelixdev.kotlincovidstate.databinding.FragmentRecentBinding
import com.brunofelixdev.kotlincovidstate.extension.dateFormatted
import com.brunofelixdev.kotlincovidstate.extension.fatalityRate
import com.brunofelixdev.kotlincovidstate.extension.formatNumber
import com.brunofelixdev.kotlincovidstate.extension.recoveredRate
import com.brunofelixdev.kotlincovidstate.listener.WorldTotalListener
import com.brunofelixdev.kotlincovidstate.data.api.dto.CountryDto
import com.brunofelixdev.kotlincovidstate.data.api.dto.WorldTotalDto
import com.brunofelixdev.kotlincovidstate.data.api.repository.CountryRepository
import com.brunofelixdev.kotlincovidstate.ui.DetailsActivity
import com.brunofelixdev.kotlincovidstate.util.EXTRAS_KEY_COUNTRY_NAME
import com.brunofelixdev.kotlincovidstate.extension.toast
import com.brunofelixdev.kotlincovidstate.listener.CountryListener
import com.brunofelixdev.kotlincovidstate.viewmodel.CountryViewModel
import com.brunofelixdev.kotlincovidstate.viewmodel.WorldTotalViewModel

class RecentFragment : Fragment(), WorldTotalListener, CountryListener {

    private var binding: FragmentRecentBinding? = null
    private var viewModel: CountryViewModel? = null
    private var viewModelWorldTotal: WorldTotalViewModel? = null
    private var isSearched: Boolean = false
    private var displayCountryList = ArrayList<CountryDto>()
    private lateinit var appContext: Context

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_recent, container, false)
        initObjects()
        toolbarConfig()
        searchConfig()
        return binding?.root
    }

    private fun initObjects() {
        this.appContext = activity?.applicationContext!!
        viewModel = ViewModelProvider(
            this,
            CountryViewModel.CountryViewModelFactory(CountryRepository(NetworkConnectionInterceptor(appContext)))
        ).get(CountryViewModel::class.java)

        viewModelWorldTotal = ViewModelProvider(
            this,
            WorldTotalViewModel.WorldTotalViewModelFactory(WorldTotalRepository(NetworkConnectionInterceptor(appContext)))
        ).get(WorldTotalViewModel::class.java)

        binding?.viewModel = viewModel
        viewModel?.listener = this
        viewModelWorldTotal?.listener = this

        viewModel?.listCountries()
        viewModelWorldTotal?.listWorldTotal()
    }

    private fun searchConfig() {
        binding?.countrySearch?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val adapter = CountryDataAdapter(
                    displayCountryList.sortedByDescending { item -> item.confirmed },
                    this@RecentFragment
                )
                adapter.filter.filter(newText)
                binding?.rvCountries?.adapter = adapter
                binding?.rvCountries?.adapter?.notifyDataSetChanged()
                return false
            }

        })
    }

    private fun toolbarConfig() {
        binding?.includeToolbar?.toolbar?.title = activity?.resources?.getString(R.string.app_name)
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
                    viewModel?.listCountries()
                    viewModelWorldTotal?.listWorldTotal()
                    true
                }
                else -> super.onOptionsItemSelected(item)
            }
        }
    }

    private fun clearProperties() {
        binding?.includeCards?.confirmedValue?.text = "0.0"
        binding?.includeCards?.criticalValue?.text = "0.0"
        binding?.includeCards?.deathValue?.text = "0.0"
        binding?.includeCards?.recoveredValue?.text = "0.0"
        binding?.includeCards?.deathRateValue?.text = "0.0"
        binding?.includeCards?.recoveredRateValue?.text = "0.0"
        binding?.includeFooter?.lastUpdateValue?.text = "0.0"
    }

    override fun onStarted() {
        clearProperties()
    }

    override fun onSuccess(data: List<WorldTotalDto>?) {
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
    }

    override fun onError(message: String) {

    }

    override fun onCountriesStarted() {
        binding?.progress?.visibility = View.VISIBLE
        binding?.rvCountries?.visibility = View.GONE
        binding?.includeOops?.root?.visibility = View.GONE
    }

    override fun onCountriesSuccess(data: List<CountryDto>?) {
        if (data != null && data.isNotEmpty()) {
            binding?.progress?.visibility = View.GONE
            binding?.includeOops?.root?.visibility = View.GONE
            binding?.rvCountries?.visibility = View.VISIBLE

            displayCountryList.clear()
            displayCountryList.addAll(data)

            binding?.rvCountries?.layoutManager = LinearLayoutManager(
                activity?.applicationContext,
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
        } else {
            binding?.progress?.visibility = View.GONE
            binding?.rvCountries?.visibility = View.GONE
            binding?.includeOops?.root?.visibility = View.VISIBLE
        }
    }

    override fun onCountriesError(message: String) {
        binding?.progress?.visibility = View.GONE
        binding?.rvCountries?.visibility = View.GONE
        binding?.includeOops?.root?.visibility = View.VISIBLE
        binding?.includeOops?.message?.text = message
    }

    override fun onCountryItemClick(view: View, country: String?) {
        startActivity(Intent(activity, DetailsActivity::class.java).apply {
            putExtra(EXTRAS_KEY_COUNTRY_NAME, country)
        })
    }
}