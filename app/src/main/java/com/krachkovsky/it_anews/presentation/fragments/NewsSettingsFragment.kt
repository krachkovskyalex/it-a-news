package com.krachkovsky.it_anews.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.krachkovsky.it_anews.R
import com.krachkovsky.it_anews.databinding.FragmentSettingsBinding
import com.krachkovsky.it_anews.presentation.updateStatusBarInsets

class NewsSettingsFragment : Fragment(R.layout.fragment_settings) {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = requireNotNull(_binding)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentSettingsBinding.inflate(inflater, container, false)
            .also { binding ->
                _binding = binding
            }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            updateStatusBarInsets(root, appBarSettings)

            cvDisplay.setOnClickListener {
                findNavController()
                    .navigate(
                        NewsSettingsFragmentDirections
                            .actionNewsSettingsFragmentToNewsSettingsDisplayFragment()
                    )
            }

            cvLanguage.setOnClickListener {
                findNavController()
                    .navigate(
                        NewsSettingsFragmentDirections
                            .actionNewsSettingsFragmentToNewsSettingsLanguageFragment()
                    )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}