package com.krachkovsky.it_anews.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.snackbar.Snackbar
import com.krachkovsky.it_anews.R
import com.krachkovsky.it_anews.databinding.FragmentArticleBinding
import com.krachkovsky.it_anews.presentation.viewmodel.NewsSavedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class NewsArticleFragment : Fragment() {

    private var _binding: FragmentArticleBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val viewModel by viewModel<NewsSavedViewModel>()

    private val args: NewsArticleFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentArticleBinding.inflate(inflater, container, false)
            .also { binding ->
                _binding = binding
            }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val article = args.article

        with(binding) {
            ViewCompat.setOnApplyWindowInsetsListener(root) { _, insets ->
                val inset = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                appBarArticle.updatePadding(
                    top = inset.top,
                )
                insets
            }

            webView.apply {
                webViewClient = WebViewClient()
                loadUrl(article.url)
            }

            btnSaveArticle.setOnClickListener {
                viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                    viewModel.insertArticleToDB(article)
                    Snackbar.make(view, getString(R.string.save_article), Snackbar.LENGTH_LONG)
                        .apply {
                            setAction(getString(R.string.undo)) {
                                viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                                    viewModel.deleteArticleFromDB(article)
                                }
                            }
                            show()
                        }
                }
            }
            toolbarArticle.setupWithNavController(findNavController())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}