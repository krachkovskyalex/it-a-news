package com.krachkovsky.it_anews.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.krachkovsky.it_anews.databinding.FragmentListBinding
import com.krachkovsky.it_anews.extention.addHorizontalSpaceDecoration
import com.krachkovsky.it_anews.extention.onPaginationScrollListener
import com.krachkovsky.it_anews.extention.onRefreshListener
import com.krachkovsky.it_anews.models.PagingData
import com.krachkovsky.it_anews.provider.ServiceProvider
import com.krachkovsky.it_anews.ui.NewsViewModel
import com.krachkovsky.it_anews.ui.adapter.NewsAdapter
import com.krachkovsky.it_anews.util.Constants
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

class NewsListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null
    private val binding
        get() = requireNotNull(_binding) {
            "View was destroyed"
        }

    private val viewModel by viewModels<NewsViewModel> {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return NewsViewModel(ServiceProvider.provideNewsApi()) as T
            }
        }
    }

    private val adapter = NewsAdapter { article ->
        findNavController().navigate(
            NewsListFragmentDirections.actionNewsListFragmentToNewsArticleFragment(article.url)
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentListBinding.inflate(inflater, container, false)
            .also { binding ->
                _binding = binding
            }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            val layoutManager = LinearLayoutManager(view.context)

            recyclerList.adapter = adapter
            recyclerList.layoutManager = layoutManager

            recyclerList.addHorizontalSpaceDecoration(RECYCLER_ITEM_SPACE)

            recyclerList.onPaginationScrollListener(layoutManager, viewModel.isLoading, ITEMS_TO_LOAD)
                .onEach {
                    viewModel.onLoadMore()
                    Log.d(Constants.TAG, "NewsFragment -> onPaginationScrollListener")
                }
                .launchIn(viewLifecycleOwner.lifecycleScope)

            swipeRefreshList
                .onRefreshListener()
                .onEach {
                    adapter.submitList(emptyList())
                    viewModel.onRefresh()
                    Log.d(Constants.TAG, "NewsFragment -> onPaginationScrollListener")
                }
                .launchIn(viewLifecycleOwner.lifecycleScope)

            viewModel
                .newsFlow
                .onEach {
                    if (swipeRefreshList.isRefreshing) {
                        swipeRefreshList.isRefreshing = false
                    }
                }
                .map { news ->
                    adapter.currentList
                        .dropLastWhile { it == PagingData.Loading }
                        .plus(news.map { PagingData.Content(it) })
                        .plus(PagingData.Loading)
                }
                .onEach(adapter::submitList)
                .launchIn(viewLifecycleOwner.lifecycleScope)

            viewModel
                .errorsFlow
                .onEach { error ->
                    Snackbar.make(
                        view,
                        error.message ?: GENERIC_ERROR_MESSAGE,
                        Snackbar.LENGTH_SHORT
                    )
                        .setAction(android.R.string.ok) {}
                        .show()
                }
                .launchIn(viewLifecycleOwner.lifecycleScope)

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val RECYCLER_ITEM_SPACE = 50
        private const val ITEMS_TO_LOAD = 15

        private const val GENERIC_ERROR_MESSAGE = "Something went wrong"
    }
}