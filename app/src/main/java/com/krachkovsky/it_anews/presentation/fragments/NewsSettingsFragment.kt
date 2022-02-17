package com.krachkovsky.it_anews.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import com.krachkovsky.it_anews.R
import com.krachkovsky.it_anews.databinding.FragmentSettingsBinding
import com.krachkovsky.it_anews.presentation.settings.manager.SharedPrefsManager
import com.krachkovsky.it_anews.presentation.settings.model.DayNightMode
import com.krachkovsky.it_anews.presentation.settings.model.LanguageMode
import org.koin.android.ext.android.inject

class NewsSettingsFragment : Fragment(R.layout.fragment_settings) {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val prefsManager: SharedPrefsManager by inject()

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
            ViewCompat.setOnApplyWindowInsetsListener(root) { _, insets ->
                val inset = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                appBarSettings.updatePadding(
                    top = inset.top,
                )
                insets
            }

            when (prefsManager.dayNightMode) {
                DayNightMode.DAY -> radiobtnDay
                DayNightMode.NIGHT -> radiobtnNight
                DayNightMode.SYSTEM -> radiobtnSystem
            }.isChecked = true

            radiogDayNightMode.setOnCheckedChangeListener { _, buttonId ->
                when (buttonId) {
                    radiobtnDay.id -> {
                        prefsManager.dayNightMode = DayNightMode.DAY
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    }
                    radiobtnNight.id -> {
                        prefsManager.dayNightMode = DayNightMode.NIGHT
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    }
                    radiobtnSystem.id -> {
                        prefsManager.dayNightMode = DayNightMode.SYSTEM
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                    }
                    else -> error("incorrect button $buttonId")
                }
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
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}