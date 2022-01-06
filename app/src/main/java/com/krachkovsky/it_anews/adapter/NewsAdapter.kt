package com.krachkovsky.it_anews.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.krachkovsky.it_anews.databinding.NewsItemBinding
import com.krachkovsky.it_anews.databinding.NewsLoadingBinding
import com.krachkovsky.it_anews.models.Article
import com.krachkovsky.it_anews.models.PagingData
import com.krachkovsky.it_anews.util.Constants.TYPE_LOADING
import com.krachkovsky.it_anews.util.Constants.TYPE_USER

class NewsAdapter(
    private val onArticleClicked: (Article) -> Unit
) : ListAdapter<PagingData<Article>, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is PagingData.Content -> TYPE_USER
            PagingData.Loading -> TYPE_LOADING
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            TYPE_USER -> {
                NewsViewHolder(
                    binding = NewsItemBinding.inflate(layoutInflater, parent, false),
                    onArticleClicked = onArticleClicked
                )
            }
            TYPE_LOADING -> {
                LoadingViewHolder(
                    binding = NewsLoadingBinding.inflate(layoutInflater, parent, false)
                )
            }
            else -> error("Unsupported viewType $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val article = (getItem(position) as? PagingData.Content)?.data ?: return
        (holder as? NewsViewHolder)?.bind(article)
        Log.d("AAA onBindViewHolder", "onBindViewHolder + position $position: $article")
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<PagingData<Article>>() {
            override fun areItemsTheSame(
                oldItem: PagingData<Article>,
                newItem: PagingData<Article>
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: PagingData<Article>,
                newItem: PagingData<Article>
            ): Boolean {
                val oldNews = oldItem as? PagingData.Content
                val newNews = newItem as? PagingData.Content
                return oldNews?.data == newNews?.data
            }
        }
    }
}