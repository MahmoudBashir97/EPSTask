package com.mahmoudbashir.epstask.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mahmoudbashir.epstask.R
import com.mahmoudbashir.epstask.databinding.ItemArticlePreviewBinding

import com.mahmoudbashir.epstask.pojo.Article
import com.squareup.picasso.Picasso


class NewsAdapter : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {
    lateinit var articleBinding:ItemArticlePreviewBinding
    inner class ViewHolder(item: View) : RecyclerView.ViewHolder(item) {

    }

    private val differCallback = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        articleBinding = DataBindingUtil.inflate( LayoutInflater.from(parent.context),
                R.layout.item_article_preview,parent,false)
        return ViewHolder(
                articleBinding.root
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = differ.currentList[position]

        holder.itemView.apply {

            Picasso.get().load(article.urlToImage).into(articleBinding.ivArticleImage)
            articleBinding.tvSource.text = article.source?.name
            articleBinding.tvTitle.text = article.title
            articleBinding.tvDescription.text = article.description
            articleBinding.tvPublishedAt.text = article.publishedAt

        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}