package com.krachkovsky.it_anews.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.krachkovsky.it_anews.databinding.NewsItemBinding
import com.krachkovsky.it_anews.domain.models.Article
import java.io.Serializable

class NewsAdapter(
    context: Context,
    private val onArticleClicked: (Article) -> Unit
) : PagingDataAdapter<Article, NewsViewHolder>(DIFF_CALLBACK), Serializable {

    private val layoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(
            binding = NewsItemBinding.inflate(layoutInflater, parent, false),
            onArticleClicked = onArticleClicked
        )
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        getItem(position)?.let(holder::bind)
    }

    companion object {

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Article>() {
            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem.url == newItem.url
            }

            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem == newItem
            }
        }
    }
}