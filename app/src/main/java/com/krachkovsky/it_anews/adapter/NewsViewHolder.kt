package com.krachkovsky.it_anews.adapter

import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Scale
import coil.size.ViewSizeResolver
import com.krachkovsky.it_anews.databinding.NewsItemBinding
import com.krachkovsky.it_anews.models.Article

class NewsViewHolder(
    private val binding: NewsItemBinding,
    private val onArticleClicked: (Article) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(article: Article) {
        with(binding) {
            imageItem.load(article.urlToImage) {
                scale(Scale.FIT)
                size(ViewSizeResolver(root))
            }
            if (article.author != null) {
                tvAuthor.text = article.author
            }
            tvPublished.text = article.publishedAt
            tvTitle.text = article.title
            tvSource.text = article.source.name

            root.setOnClickListener {
                onArticleClicked(article)
            }
        }
    }
}