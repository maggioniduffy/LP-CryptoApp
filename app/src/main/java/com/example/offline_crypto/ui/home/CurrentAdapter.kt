package com.example.offline_crypto.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.offline_crypto.R
import com.example.offline_crypto.databinding.ListItemBinding
import com.example.offline_crypto.models.Property

// (private val data: List<Property>)
class CurrentAdapter (private val data: List<Property>) :
    RecyclerView.Adapter<CurrentAdapter.CurrentViewHolder>() {

    private var listData: MutableList<Property> = data as MutableList<Property>

    class CurrentViewHolder(val view: ListItemBinding) : RecyclerView.ViewHolder(view.root) {
        fun bind(item: Property, index: Int){
            view.tvTitle.text = item.name
            view.tvDescription.text = item.price

            Glide.with(view.root.context).load(item.image).centerCrop().into(view.imageView)
        }

        fun bindRecyclerView(data: List<Property>) {
            val manager: RecyclerView.LayoutManager =
                LinearLayoutManager(view.root.context, LinearLayoutManager.HORIZONTAL, true)
            view.recyclerView.apply {
                val data = data as MutableList<Property>
                var myAdapter = CurrentAdapter(data)
                layoutManager = manager
                adapter = myAdapter
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrentViewHolder {
        val v = LayoutInflater.from(parent.context)
        val listItemBinding = ListItemBinding.inflate(v, parent, false)
        return CurrentViewHolder(listItemBinding)
    }

    override fun onBindViewHolder(holder: CurrentViewHolder, position: Int) {
        holder.bind(listData[position], position)
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    fun setItems(items: List<Property>) {
        listData = items as MutableList<Property>
        notifyDataSetChanged()
    }

}