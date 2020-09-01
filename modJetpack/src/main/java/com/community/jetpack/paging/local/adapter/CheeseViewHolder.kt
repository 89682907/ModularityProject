package com.community.jetpack.paging.local.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.community.jetpack.R
import com.community.jetpack.paging.local.db.Cheese

class CheeseViewHolder(parent: ViewGroup) : androidx.recyclerview.widget.RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.jet_cheese_item, parent, false)) {
    private val nameView = itemView.findViewById<TextView>(R.id.name)
    var cheese: Cheese? = null

    fun bindTo(cheese: Cheese?) {
        this.cheese = cheese
        nameView.text = cheese?.name
    }
}