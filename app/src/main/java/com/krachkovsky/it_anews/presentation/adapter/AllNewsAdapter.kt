package com.krachkovsky.it_anews.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.krachkovsky.it_anews.databinding.NewsItemBinding
import krachkovsky.it_anews_domain.models.Article
import java.io.Serializable

class AllNewsAdapter(
    context: Context,
    private val onArticleClicked: (Article) -> Unit
) : PagingDataAdapter<Article, AllNewsViewHolder>(DIFF_CALLBACK), Serializable {

    private val layoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllNewsViewHolder {
        return AllNewsViewHolder(
            binding = NewsItemBinding.inflate(layoutInflater, parent, false),
            onArticleClicked = onArticleClicked
        )
    }

    override fun onBindViewHolder(holder: AllNewsViewHolder, position: Int) {
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