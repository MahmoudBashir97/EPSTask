package com.mahmoudbashir.epstask.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mahmoudbashir.epstask.R
import com.mahmoudbashir.epstask.databinding.SingleItemSavedDataBinding
import com.mahmoudbashir.epstask.pojo.DataModel
import com.squareup.picasso.Picasso

class SavedDataAdapter:RecyclerView.Adapter<SavedDataAdapter.ViewHolder>() {

    lateinit var itemSavedBinding:SingleItemSavedDataBinding
    inner class ViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView)

    private val differCallback = object : DiffUtil.ItemCallback<DataModel>(){
        override fun areItemsTheSame(oldItem: DataModel, newItem: DataModel): Boolean {
            return oldItem.imgUri == newItem.imgUri
        }

        override fun areContentsTheSame(oldItem: DataModel, newItem: DataModel): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this,differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        itemSavedBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),R.layout.single_item_saved_data,parent,false)
        return ViewHolder(itemSavedBinding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentData = differ.currentList[position]
        holder.itemView.apply {
            Picasso.get().load(currentData.imgUri).into(itemSavedBinding.imgPic)
            itemSavedBinding.txtTitle.text = currentData.title
            itemSavedBinding.txtDesc.text = currentData.description

        }
    }


    override fun getItemCount(): Int =differ.currentList.size
}