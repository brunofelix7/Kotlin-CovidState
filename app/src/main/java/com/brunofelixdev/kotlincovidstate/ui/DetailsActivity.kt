package com.brunofelixdev.kotlincovidstate.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import com.brunofelixdev.kotlincovidstate.R
import com.brunofelixdev.kotlincovidstate.data.api.interceptor.NetworkConnectionInterceptor
import com.brunofelixdev.kotlincovidstate.data.api.repository.StatisticsDataRepository
import com.brunofelixdev.kotlincovidstate.databinding.ActivityDetailsBinding
import com.brunofelixdev.kotlincovidstate.extension.dateFormatted
import com.brunofelixdev.kotlincovidstate.extension.fatalityRate
import com.brunofelixdev.kotlincovidstate.extension.formatNumber
import com.brunofelixdev.kotlincovidstate.extension.recoveredRate
import com.brunofelixdev.kotlincovidstate.listener.StatisticsListener
import com.brunofelixdev.kotlincovidstate.model.CountryStatisticsData
import com.brunofelixdev.kotlincovidstate.util.EXTRAS_KEY_COUNTRY_NAME
import com.brunofelixdev.kotlincovidstate.util.toast
import com.brunofelixdev.kotlincovidstate.viewmodel.DetailsViewModel

class DetailsActivity : AppCompatActivity(), StatisticsListener {

    private var country: String? = null
    private var binding: ActivityDetailsBinding? = null
    private var viewModel: DetailsViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        country = intent.extras?.getString(EXTRAS_KEY_COUNTRY_NAME)

        initObjects()
        fetchData()
        toolbarConfig()
    }

    private fun initObjects() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_details)
        viewModel = ViewModelProvider(
            this,
            DetailsViewModel.DetailsViewModelFactory(StatisticsDataRepository(this, NetworkConnectionInterceptor(this)))
        ).get(DetailsViewModel::class.java)

        binding?.viewModel = viewModel
        viewModel?.listener = this
    }

    private fun fetchData() {
        viewModel?.getStatistics(country ?: "Unknown")
    }

    private fun toolbarConfig() {
        binding?.includeToolbar?.toolbar?.title = country ?: "Unknown"
        binding?.includeToolbar?.toolbar?.setNavigationIcon(R.drawable.ic_arrow_back)
        binding?.includeToolbar?.toolbar?.setOnClickListener {
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
    override fun onCompletedStatisticsData(liveData: LiveData<CountryStatisticsData>) {
        liveData.observe(this, { data ->
            if (data?.response != null) {
                binding?.confirmed?.text = data.response[0].cases?.total?.formatNumber()
                binding?.activeCases?.text = data.response[0].cases?.active?.formatNumber()
                binding?.newCases?.text = "+ ${data.response[0].cases?.new?.toLong()?.formatNumber()}"
                binding?.recovered?.text = data.response[0].cases?.recovered?.formatNumber()
                binding?.critical?.text = data.response[0].cases?.critical?.formatNumber()
                binding?.deaths?.text = data.response[0].deaths?.total?.formatNumber()
                binding?.newDeaths?.text = "+ ${data.response[0].deaths?.new?.toLong()?.formatNumber()}"
                binding?.testsDone?.text = data.response[0].tests?.total?.formatNumber()
                binding?.fatalityRate?.text = "${data.response[0].deaths?.total?.fatalityRate(data.response[0].cases?.total)} %"
                binding?.recoveredRate?.text = "${data.response[0].cases?.recovered?.recoveredRate(data.response[0].cases?.total)} %"
                binding?.includeFooter?.lastUpdateValue?.text = data.response[0].time?.dateFormatted()
            }
        })
    }
}