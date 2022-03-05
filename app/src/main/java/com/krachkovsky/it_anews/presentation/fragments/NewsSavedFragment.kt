package com.krachkovsky.it_anews.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.krachkovsky.it_anews.R
import com.krachkovsky.it_anews.databinding.FragmentSavedBinding
import com.krachkovsky.it_anews.presentation.adapter.NewsLoadStateAdapter
import com.krachkovsky.it_anews.presentation.adapter.SavedNewsAdapter
import com.krachkovsky.it_anews.presentation.extention.addHorizontalSpaceDecoration
import com.krachkovsky.it_anews.presentation.extention.onLoad
import com.krachkovsky.it_anews.presentation.extention.onRefreshListener
import com.krachkovsky.it_anews.presentation.viewmodel.NewsSavedViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel

class NewsSavedFragment : Fragment() {

    private var _binding: FragmentSavedBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val viewModel by viewModel<NewsSavedViewModel>()

    private val adapter by lazy(LazyThreadSafetyMode.NONE) {
        SavedNewsAdapter(requireContext()) { article ->
            findNavController().navigate(
                R.id.action_newsSavedFragment_to_newsSavedArticleFragment,
                Bundle().apply {
                    putSerializable("article", article)
                }
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentSavedBinding.inflate(inflater, container, false)
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
                appBarSaved.updatePadding(
                    top = inset.top,
                )
                insets
            }

            recyclerSaved.adapter = adapter.withLoadStateHeaderAndFooter(
                header = NewsLoadStateAdapter(),
                footer = NewsLoadStateAdapter()
            )
            recyclerSaved.addHorizontalSpaceDecoration(RECYCLER_ITEM_SPACE)

            swipeRefreshSaved
                .onRefreshListener()
                .onEach { adapter.refresh() }
                .launchIn(viewLifecycleOwner.lifecycleScope)


            viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                viewModel.savedNewsFlow
                    .collectLatest(adapter::submitData)
            }

            viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                adapter.onLoad(progressSaved, swipeRefreshSaved, root)
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