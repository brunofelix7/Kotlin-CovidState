package com.brunofelixdev.kotlincovidstate.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.brunofelixdev.kotlincovidstate.R
import com.brunofelixdev.kotlincovidstate.adapter.CountryDataAdapter
import com.brunofelixdev.kotlincovidstate.data.api.repository.DataRepository
import com.brunofelixdev.kotlincovidstate.databinding.ActivityHomeBinding
import com.brunofelixdev.kotlincovidstate.extension.fatalityRate
import com.brunofelixdev.kotlincovidstate.extension.formatNumber
import com.brunofelixdev.kotlincovidstate.extension.recoveredRate
import com.brunofelixdev.kotlincovidstate.listener.DataListener
import com.brunofelixdev.kotlincovidstate.model.CountryData
import com.brunofelixdev.kotlincovidstate.model.WorldData
import com.brunofelixdev.kotlincovidstate.util.EXTRAS_KEY_COUNTRY_NAME
import com.brunofelixdev.kotlincovidstate.viewmodel.DataViewModel

class HomeActivity : AppCompatActivity(), DataListener {

    private var binding: ActivityHomeBinding? = null
    private var viewModel: DataViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingConfig()
        fetchData()
    }

    private fun bindingConfig() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        viewModel = ViewModelProvider(
                this,
                DataViewModel.DataViewModelFactory(DataRepository())
        ).get(DataViewModel::class.java)

        binding?.viewModel = viewModel
        viewModel?.listener = this
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
                binding?.includeCards?.deathRateValue?.text = data[0].deaths?.fatalityRate(data[0].confirmed)
                binding?.includeCards?.recoveredRateValue?.text = data[0].recovered?.recoveredRate(data[0].confirmed)
            }
        })
    }

    override fun onCompletedCountriesData(liveData: LiveData<List<CountryData>>) {
        liveData.observe(this, { data ->
            if (data != null && data.isNotEmpty()) {
                binding?.rvCountries?.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
                binding?.rvCountries?.setHasFixedSize(true)
                binding?.rvCountries?.adapter = CountryDataAdapter(data.sortedByDescending { field ->
                    field.confirmed
                }, this)
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