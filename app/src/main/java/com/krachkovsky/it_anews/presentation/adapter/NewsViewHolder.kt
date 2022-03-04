package com.krachkovsky.it_anews.presentation.adapter

import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Scale
import coil.size.ViewSizeResolver
import com.krachkovsky.it_anews.databinding.NewsItemBinding

class NewsViewHolder(
    private val binding: NewsItemBinding,
    private val onArticleClicked: (krachkovsky.it_anews_domain.models.Article) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(article: krachkovsky.it_anews_domain.models.Article) {
        with(binding) {
            imageItem.load(article.urlToImage) {
                scale(Scale.FIT)
                size(ViewSizeResolver(root))
            }
            if (article.title != null)
                tvTitle.text = article.title
            if (article.description != null)
                tvDescription.text = article.description
            tvPublished.text = article.publishedAt

            root.setOnClickListener {
                onArticleClicked(article)
            }
        }
    }
}