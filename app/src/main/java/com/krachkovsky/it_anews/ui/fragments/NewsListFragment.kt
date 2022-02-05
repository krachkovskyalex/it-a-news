package com.krachkovsky.it_anews.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.google.android.material.snackbar.Snackbar
import com.krachkovsky.it_anews.databinding.FragmentListBinding
import com.krachkovsky.it_anews.extention.addHorizontalSpaceDecoration
import com.krachkovsky.it_anews.extention.onRefreshListener
import com.krachkovsky.it_anews.ui.NewsViewModel
import com.krachkovsky.it_anews.ui.adapter.NewsAdapter
import com.krachkovsky.it_anews.ui.adapter.NewsLoadStateAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class NewsListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null
    private val binding
        get() = requireNotNull(_binding) {
            "View was destroyed"
        }

    private val viewModel by viewModel<NewsViewModel>()

    private val adapter by lazy(LazyThreadSafetyMode.NONE) {
        NewsAdapter(requireContext()) { article ->
            findNavController().navigate(
                NewsListFragmentDirections.actionNewsListFragmentToNewsArticleFragment(article.url)
            )
        }
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
            recyclerList.adapter = adapter.withLoadStateHeaderAndFooter(
                header = NewsLoadStateAdapter(),
                footer = NewsLoadStateAdapter()
            )
            recyclerList.addHorizontalSpaceDecoration(RECYCLER_ITEM_SPACE)

            swipeRefreshList
                .onRefreshListener()
                .onEach { adapter.refresh() }
                .launchIn(viewLifecycleOwner.lifecycleScope)

            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.pagingFlow
                    .collectLatest(adapter::submitData)
            }

            viewLifecycleOwner.lifecycleScope.launch {
                adapter
                    .loadStateFlow
                    .distinctUntilChangedBy { it.refresh }
                    .collectLatest {
                        val state = it.refresh

                        progress.isVisible =
                            !swipeRefreshList.isRefreshing && state == LoadState.Loading
                        swipeRefreshList.isRefreshing =
                            swipeRefreshList.isRefreshing && state == LoadState.Loading

                        if (state is LoadState.Error) {
                            Snackbar.make(
                                root,
                                state.error.localizedMessage ?: "Something went wrong",
                                Snackbar.LENGTH_INDEFINITE
                            ).setAction(android.R.string.ok) { adapter.retry() }
                                .show()
                        }
                    }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val RECYCLER_ITEM_SPACE = 50
    }
}