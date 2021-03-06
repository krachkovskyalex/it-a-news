package com.krachkovsky.it_anews.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.krachkovsky.it_anews.R
import com.krachkovsky.it_anews.databinding.FragmentSearchBinding
import com.krachkovsky.it_anews.presentation.adapter.NewsAdapter
import com.krachkovsky.it_anews.presentation.adapter.NewsLoadStateAdapter
import com.krachkovsky.it_anews.presentation.extention.addHorizontalSpaceDecoration
import com.krachkovsky.it_anews.presentation.extention.onLoad
import com.krachkovsky.it_anews.presentation.extention.onRefreshListener
import com.krachkovsky.it_anews.presentation.extention.onTextChanged
import com.krachkovsky.it_anews.presentation.viewmodel.NewsSearchViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class NewsSearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val viewModel by viewModel<NewsSearchViewModel>()

    private val adapter by lazy(LazyThreadSafetyMode.NONE) {
        NewsAdapter(requireContext()) { article ->
            findNavController().navigate(
                NewsSearchFragmentDirections.actionNewsSearchFragmentToNewsArticleFragment2(
                    article.url
                )
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentSearchBinding.inflate(inflater, container, false)
            .also { binding ->
                _binding = binding
            }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            ViewCompat.setOnApplyWindowInsetsListener(root) { _, insets ->
                val inset = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                appBarSearch.updatePadding(
                    top = inset.top,
                )
                insets
            }

            recyclerSearch.adapter = adapter.withLoadStateHeaderAndFooter(
                header = NewsLoadStateAdapter(),
                footer = NewsLoadStateAdapter()
            )
            recyclerSearch.addHorizontalSpaceDecoration(RECYCLER_ITEM_SPACE)

            swipeRefreshSearch
                .onRefreshListener()
                .onEach { adapter.refresh() }
                .launchIn(viewLifecycleOwner.lifecycleScope)

            toolbarSearch.menu
                .findItem(R.id.news_search)
                .let { it.actionView as SearchView }
                .onTextChanged()
                .onEach { query ->
                    viewModel.onQueryChanger(query)
                }
                .launchIn(viewLifecycleOwner.lifecycleScope)

            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.searchNews
                    .collectLatest(adapter::submitData)
            }

            viewLifecycleOwner.lifecycleScope.launch {
                adapter.onLoad(progressSearch, swipeRefreshSearch, root)
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