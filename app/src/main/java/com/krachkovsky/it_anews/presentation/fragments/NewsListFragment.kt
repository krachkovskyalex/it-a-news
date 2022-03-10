package com.krachkovsky.it_anews.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.krachkovsky.it_anews.R
import com.krachkovsky.it_anews.databinding.FragmentListBinding
import com.krachkovsky.it_anews.presentation.adapter.NewsAdapter
import com.krachkovsky.it_anews.presentation.adapter.NewsLoadStateAdapter
import com.krachkovsky.it_anews.presentation.extention.addHorizontalSpaceDecoration
import com.krachkovsky.it_anews.presentation.extention.onCategoryChanged
import com.krachkovsky.it_anews.presentation.extention.onLoad
import com.krachkovsky.it_anews.presentation.extention.onRefreshListener
import com.krachkovsky.it_anews.presentation.viewmodel.NewsListViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class NewsListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val viewModel by viewModel<NewsListViewModel>()

    private val adapter by lazy(LazyThreadSafetyMode.NONE) {
        NewsAdapter(requireContext()) { article ->
            findNavController().navigate(
                NewsListFragmentDirections.actionNewsListFragmentToNewsArticleFragment(
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
        return FragmentListBinding.inflate(inflater, container, false)
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
                appBarList.updatePadding(
                    top = inset.top,
                )
                insets
            }

            ArrayAdapter.createFromResource(
                requireContext(),
                R.array.news_category_list,
                R.layout.spinner_news_category_item
            ).also { adapter ->
                adapter.setDropDownViewResource(R.layout.spinner_news_category_dropdown_item)
                spinnerCategory.adapter = adapter
            }

            spinnerCategory
                .onCategoryChanged()
                .onEach { category ->
                    viewModel.onCategoryChanged(category)
                }
                .launchIn(viewLifecycleOwner.lifecycleScope)

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
                viewModel.newsFlow
                    .collectLatest(adapter::submitData)
            }

            viewLifecycleOwner.lifecycleScope.launch {
                adapter.onLoad(progress, swipeRefreshList, root)
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