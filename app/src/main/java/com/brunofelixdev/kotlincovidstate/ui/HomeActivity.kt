package com.brunofelixdev.kotlincovidstate.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import com.brunofelixdev.kotlincovidstate.R
import com.brunofelixdev.kotlincovidstate.data.api.WorldDataRepository
import com.brunofelixdev.kotlincovidstate.databinding.ActivityHomeBinding
import com.brunofelixdev.kotlincovidstate.extension.fatalityRate
import com.brunofelixdev.kotlincovidstate.extension.formatNumber
import com.brunofelixdev.kotlincovidstate.extension.recoveredRate
import com.brunofelixdev.kotlincovidstate.listener.WorldDataListener
import com.brunofelixdev.kotlincovidstate.model.WorldData
import com.brunofelixdev.kotlincovidstate.viewmodel.WorldDataViewModel

class HomeActivity : AppCompatActivity(), WorldDataListener {

    private var binding: ActivityHomeBinding? = null
    private var viewModel: WorldDataViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingConfig()
    }

    private fun bindingConfig() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        viewModel = ViewModelProvider(
                this,
                WorldDataViewModel.WorldDataViewModelFactory(WorldDataRepository())
        ).get(WorldDataViewModel::class.java)

        binding?.viewModel = viewModel
        viewModel?.listener = this

        viewModel?.listWorldData()
    }

    override fun onStarted() {

    }

    override fun onCompleted(liveData: LiveData<List<WorldData>>) {
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
}