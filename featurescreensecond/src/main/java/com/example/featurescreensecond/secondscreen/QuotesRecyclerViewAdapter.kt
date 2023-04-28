package com.example.featurescreensecond.secondscreen

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.featurescreensecond.BR
import com.example.featurescreensecond.R
import com.example.featurescreensecond.databinding.RecyclerViewItemBinding


// https://www.digitalocean.com/community/tutorials/android-recyclerview-data-binding
class QuotesRecyclerViewAdapter(
    val context: Context,
    private var authorModelLIst: MutableList<AuthorModel>
) :
    ListAdapter<AuthorModel, QuotesRecyclerViewAdapter.ViewHolder>(AuthorDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: RecyclerViewItemBinding = DataBindingUtil.inflate<RecyclerViewItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.recycler_view_item,
            parent,
            false
        )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val authorModel = quoteList[position]
//        holder.bind(authorModel)
        try {
            holder.bind(getItem(position))
        } catch (exception: Exception) {
            Log.e("Jimmy evaded crash", exception.toString())
        }
    }

    inner class ViewHolder(itemRowBinding: RecyclerViewItemBinding) : RecyclerView.ViewHolder(itemRowBinding.root) {
        var itemRowBinding: RecyclerViewItemBinding // TODO Needed ? Review - https://github.com/android/views-widgets-samples/blob/main/RecyclerViewKotlin/app/src/main/java/com/example/recyclersample/flowerList/FlowersAdapter.kt
        init {
            this.itemRowBinding = itemRowBinding
        }

        fun bind(obj: Any?) {
            itemRowBinding.setVariable(BR.stringToBind, obj)
            itemRowBinding.executePendingBindings()
        }
    }

}