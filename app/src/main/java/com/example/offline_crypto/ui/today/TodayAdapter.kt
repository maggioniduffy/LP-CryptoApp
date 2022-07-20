package com.example.offline_crypto.ui.today

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.offline_crypto.databinding.ListItemBinding
import com.example.offline_crypto.models.Coin

class TodayAdapter (private val data: List<Coin>) :
    RecyclerView.Adapter<TodayAdapter.CurrentViewHolder>() {

    private var listData: MutableList<Coin> = data as MutableList<Coin>

    class CurrentViewHolder(val view: ListItemBinding) : RecyclerView.ViewHolder(view.root) {
        fun bind(item: Coin, index: Int) {
            view.tvTitle.text = item.name
            view.tvDescription.text = item.price

            Glide.with(view.root.context).load(item.image).centerCrop().into(view.imageView)
        }

        fun bindRecyclerView(data: List<Coin>) {
            val manager: RecyclerView.LayoutManager =
                LinearLayoutManager(view.root.context, LinearLayoutManager.HORIZONTAL, true)
            view.recyclerView.apply {
                val data = data as MutableList<Coin>
                var myAdapter = TodayAdapter(data)
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

    fun setItems(items: List<Coin>) {
        listData = items as MutableList<Coin>
        notifyDataSetChanged()
    }
}