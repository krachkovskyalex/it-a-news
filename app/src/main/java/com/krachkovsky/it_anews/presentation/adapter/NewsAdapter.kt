package com.krachkovsky.it_anews.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.krachkovsky.it_anews.databinding.NewsItemBinding
import java.io.Serializable

class NewsAdapter(
    context: Context,
    private val onArticleClicked: (krachkovsky.it_anews_domain.models.Article) -> Unit
) : PagingDataAdapter<krachkovsky.it_anews_domain.models.Article, NewsViewHolder>(DIFF_CALLBACK),
    Serializable {

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

        private val DIFF_CALLBACK =
            object : DiffUtil.ItemCallback<krachkovsky.it_anews_domain.models.Article>() {
                override fun areItemsTheSame(
                    oldItem: krachkovsky.it_anews_domain.models.Article,
                    newItem: krachkovsky.it_anews_domain.models.Article
                ): Boolean {
                    return oldItem.url == newItem.url
                }

                override fun areContentsTheSame(
                    oldItem: krachkovsky.it_anews_domain.models.Article,
                    newItem: krachkovsky.it_anews_domain.models.Article
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }
}