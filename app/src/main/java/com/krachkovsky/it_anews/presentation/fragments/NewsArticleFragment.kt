package com.krachkovsky.it_anews.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.core.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import com.krachkovsky.it_anews.R
import com.krachkovsky.it_anews.databinding.FragmentArticleBinding

class NewsArticleFragment : Fragment() {

    private var _binding: FragmentArticleBinding? = null
    private val binding
        get() = requireNotNull(_binding) {
            "View was destroyed"
        }

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
        val article = args.url
        with(binding) {
            ViewCompat.setOnApplyWindowInsetsListener(root) { _, insets ->
                val inset = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                appBarArticle.updatePadding(
                    top = inset.top,
                )
                webView.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                    bottomMargin = inset.bottom
                }
                insets
            }

            webView.apply {
                webViewClient = WebViewClient()
                loadUrl(article)
            }
            toolbarArticle.setupWithNavController(findNavController())
            toolbarArticle.setTitle(R.string.news_article)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}