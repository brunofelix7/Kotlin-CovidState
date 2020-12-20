package com.brunofelixdev.kotlincovidstate.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.brunofelixdev.kotlincovidstate.R
import com.brunofelixdev.kotlincovidstate.databinding.ItemCountryBinding
import com.brunofelixdev.kotlincovidstate.extension.formatNumber
import com.brunofelixdev.kotlincovidstate.listener.DataListener
import com.brunofelixdev.kotlincovidstate.model.CountryData

class CountryDataAdapter(
    private val list: List<CountryData>,
    private val listener: DataListener
) : RecyclerView.Adapter<CountryDataAdapter.CountryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CountryViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_country,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.binding.model = list[position]
        holder.binding.layoutRoot.setOnClickListener {
            listener.onItemClick(holder.binding.layoutRoot, list[position].country)
        }
    }

    override fun getItemCount() = list.size

    inner class CountryViewHolder(val binding: ItemCountryBinding) :
        RecyclerView.ViewHolder(binding.root)

}