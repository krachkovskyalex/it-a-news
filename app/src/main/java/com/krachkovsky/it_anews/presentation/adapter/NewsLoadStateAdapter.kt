package com.krachkovsky.it_anews.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.krachkovsky.it_anews.databinding.NewsErrorBinding
import com.krachkovsky.it_anews.databinding.NewsLoadingBinding

class NewsLoadStateAdapter : LoadStateAdapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (loadState) {
            LoadState.Loading -> LoadingViewHolder(
                binding = NewsLoadingBinding.inflate(inflater, parent, false)
            )
            is LoadState.Error -> ErrorViewHolder(
                binding = NewsErrorBinding.inflate(inflater, parent, false),
            )
            is LoadState.NotLoading -> error("Not supported")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, loadState: LoadState) {}
}