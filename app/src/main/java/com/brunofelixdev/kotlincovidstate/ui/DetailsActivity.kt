package com.brunofelixdev.kotlincovidstate.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.brunofelixdev.kotlincovidstate.R
import com.brunofelixdev.kotlincovidstate.data.api.response.CountryStatisticsData
import com.brunofelixdev.kotlincovidstate.databinding.ActivityDetailsBinding
import com.brunofelixdev.kotlincovidstate.extension.*
import com.brunofelixdev.kotlincovidstate.listener.CountryDetailsListener
import com.brunofelixdev.kotlincovidstate.viewmodel.CountryDetailsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailsActivity : AppCompatActivity(), CountryDetailsListener {

    //  ViewBinding
    private lateinit var binding: ActivityDetailsBinding

    //  DI - Koin inject
    private val viewModel : CountryDetailsViewModel by viewModel()

    private var country: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeViews()

        country = intent.extras?.getString(this.resources.getString(R.string.extras_key_country_name))

        fetchData()
    }

    private fun initializeViews() {
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        viewModel.listener = this

        initializeToolbar()
    }

    private fun fetchData() {
        viewModel.getStatistics(country ?: "Unknown")
    }

    private fun initializeToolbar() {
        binding.includeToolbar.toolbar.title = country ?: "Unknown"
        binding.includeToolbar.toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        binding.includeToolbar.toolbar.setOnClickListener {
            finish()
        }
    }

    override fun onStarted() {
        //  TODO: Progress
    }

    override fun onError(message: String) {
        toast(message)
    }

    @SuppressLint("SetTextI18n")
    override fun onCompletedStatisticsData(data: CountryStatisticsData?) {
        if (data?.response != null) {
            binding.confirmed.text = data.response[0].cases?.total?.formatNumber()
            binding.activeCases.text = data.response[0].cases?.active?.formatNumber()
            binding.newCases.text = "+ ${data.response[0].cases?.new?.toLong()?.formatNumber()}"
            binding.recovered.text = data.response[0].cases?.recovered?.formatNumber()
            binding.critical.text = data.response[0].cases?.critical?.formatNumber()
            binding.deaths.text = data.response[0].deaths?.total?.formatNumber()
            binding.newDeaths.text = "+ ${data.response[0].deaths?.new?.toLong()?.formatNumber()}"
            binding.testsDone.text = data.response[0].tests?.total?.formatNumber()
            binding.fatalityRate.text = "${data.response[0].deaths?.total?.fatalityRate(data.response[0].cases?.total)} %"
            binding.recoveredRate.text = "${data.response[0].cases?.recovered?.recoveredRate(data.response[0].cases?.total)} %"
            binding.includeFooter.lastUpdateValue.text = data.response[0].time?.dateFormatted()
        }
    }
}