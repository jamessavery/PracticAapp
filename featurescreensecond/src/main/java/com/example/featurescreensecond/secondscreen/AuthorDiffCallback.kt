package com.example.featurescreensecond.secondscreen

import androidx.recyclerview.widget.DiffUtil

object AuthorDiffCallback : DiffUtil.ItemCallback<AuthorModel>() {
   override fun areItemsTheSame(oldItem: AuthorModel, newItem: AuthorModel): Boolean {
      return oldItem.author == newItem.author
   }
   
   override fun areContentsTheSame(oldItem: AuthorModel, newItem: AuthorModel): Boolean {
      return oldItem == newItem
   }
}