package com.example.featurescreensecond.secondscreen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.featurescreensecond.R

class FlowerAdapter(val flowerList: List<String>) :
    RecyclerView.Adapter<FlowerAdapter.FlowerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlowerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.flower_item, parent, false)

        return FlowerViewHolder(view)
    }

    override fun onBindViewHolder(holder: FlowerViewHolder, position: Int) {
        holder.bind(flowerList[position])
    }

    override fun getItemCount(): Int {
        return flowerList.size
    }

    inner class FlowerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val flowerTextView: TextView = itemView.findViewById(R.id.testing_items)

        fun bind(word: String) {
            flowerTextView.text = word
        }
    }
}