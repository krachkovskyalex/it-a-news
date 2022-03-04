package com.krachkovsky.it_anews.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.krachkovsky.it_anews.R
import com.krachkovsky.it_anews.databinding.FragmentSettingsLanguageBinding
import com.krachkovsky.it_anews.presentation.settings.manager.SharedPrefsManager
import com.krachkovsky.it_anews.presentation.settings.model.LanguageMode
import org.koin.android.ext.android.inject

class NewsSettingsLanguageFragment : Fragment(R.layout.fragment_settings_language) {

    private var _binding: FragmentSettingsLanguageBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val prefsManager: SharedPrefsManager by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentSettingsLanguageBinding.inflate(inflater, container, false)
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
                appBarSettingsLanguage.updatePadding(
                    top = inset.top,
                )
                insets
            }

            when (prefsManager.languageMode) {
                LanguageMode.EN -> radiobtnEnglish
                LanguageMode.RU -> radiobtnRussian
            }.isChecked = true

            radiogLanguage.setOnCheckedChangeListener { _, buttonId ->
                when (buttonId) {
                    radiobtnEnglish.id -> {
                        prefsManager.languageMode = LanguageMode.EN
                    }
                    radiobtnRussian.id -> {
                        prefsManager.languageMode = LanguageMode.RU
                    }
                    else -> error("incorrect buttonId $buttonId")
                }

                activity?.recreate()
            }

            toolbarSettingsLanguage.setupWithNavController(findNavController())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}