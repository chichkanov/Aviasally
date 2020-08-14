package com.chichkanov.aviasally.searchcity.presentation

import com.chichkanov.aviasally.R
import com.chichkanov.aviasally.core.domain.City
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_list_search_city.*

class CitySuggestItem(
    private val city: City,
    private val clickListener: () -> Unit
) : Item() {

    override fun getLayout(): Int = R.layout.item_list_search_city

    override fun bind(viewHolder: GroupieViewHolder, position: Int) = with(viewHolder) {
        cityName.text = city.city
        fullName.text = city.fullName

        itemView.setOnClickListener { clickListener() }
    }

    override fun getId(): Long = city.id

    override fun hasSameContentAs(other: com.xwray.groupie.Item<*>): Boolean {
        return other is CitySuggestItem && other.city == city
    }
}