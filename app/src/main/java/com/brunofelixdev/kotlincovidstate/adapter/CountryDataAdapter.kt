package com.brunofelixdev.kotlincovidstate.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.brunofelixdev.kotlincovidstate.R
import com.brunofelixdev.kotlincovidstate.databinding.ItemCountryBinding
import com.brunofelixdev.kotlincovidstate.data.api.response.CountryResponse
import com.brunofelixdev.kotlincovidstate.listener.CountryListener
import java.util.*
import kotlin.collections.ArrayList

class CountryDataAdapter(
    private val countryList: List<CountryResponse>,
    private val listener: CountryListener
) : RecyclerView.Adapter<CountryDataAdapter.CountryViewHolder>(), Filterable {

    var countryFilterList = ArrayList<CountryResponse>()

    init {
        countryFilterList.addAll(countryList)
    }

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
        if (countryFilterList.size > 0) {
            holder.binding.model = countryFilterList[position]
        }
        holder.binding.layoutRoot.setOnClickListener {
            listener.onCountryItemClick(holder.binding.layoutRoot, countryFilterList[position].country)
        }
    }

    override fun getItemCount() = countryFilterList.size

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.trim().isEmpty()) {
                    countryFilterList.addAll(countryList)
                } else {
                    countryFilterList.clear()
                    countryList.forEach {
                        if (it.country?.toLowerCase(Locale.getDefault())
                                ?.startsWith(charSearch.toLowerCase(Locale.getDefault()))!!
                        ) {
                            countryFilterList.add(it)
                        }
                    }
                }
                val filterResults = FilterResults()
                filterResults.values = countryFilterList
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                countryFilterList = results?.values as ArrayList<CountryResponse>
                notifyDataSetChanged()
            }
        }
    }

    inner class CountryViewHolder(val binding: ItemCountryBinding) :
        RecyclerView.ViewHolder(binding.root)

}