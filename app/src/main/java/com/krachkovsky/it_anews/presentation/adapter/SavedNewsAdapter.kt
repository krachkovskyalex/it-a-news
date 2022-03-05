package com.krachkovsky.it_anews.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.krachkovsky.it_anews.databinding.NewsItemBinding
import krachkovsky.it_anews_domain.models.Article

class SavedNewsAdapter(
    context: Context,
    private val onArticleClicked: (Article) -> Unit
) : PagingDataAdapter<Article, SavedNewsViewHolder>(DIFF_CALLBACK) {

    private val layoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedNewsViewHolder {
        return SavedNewsViewHolder(
            binding = NewsItemBinding.inflate(layoutInflater, parent, false),
            onArticleClicked = onArticleClicked
        )
    }

    override fun onBindViewHolder(holder: SavedNewsViewHolder, position: Int) {
        getItem(position)?.let(holder::bind)
    }

    companion object {

        private val DIFF_CALLBACK =
            object : DiffUtil.ItemCallback<Article>() {
                override fun areItemsTheSame(
                    oldItem: Article,
                    newItem: Article
                ): Boolean {
                    return oldItem.url == newItem.url
                }

                override fun areContentsTheSame(
                    oldItem: Article,
                    newItem: Article
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }
}