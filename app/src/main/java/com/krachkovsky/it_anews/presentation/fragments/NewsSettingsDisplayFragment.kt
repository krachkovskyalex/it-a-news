package com.krachkovsky.it_anews.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.krachkovsky.it_anews.R
import com.krachkovsky.it_anews.databinding.FragmentSettingsDisplayBinding
import com.krachkovsky.it_anews.presentation.settings.manager.SharedPrefsManager
import com.krachkovsky.it_anews.presentation.settings.model.DayNightMode
import com.krachkovsky.it_anews.presentation.updateStatusBarInsets
import org.koin.android.ext.android.inject

class NewsSettingsDisplayFragment : Fragment(R.layout.fragment_settings_display) {

    private var _binding: FragmentSettingsDisplayBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val prefsManager: SharedPrefsManager by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentSettingsDisplayBinding.inflate(inflater, container, false)
            .also { binding ->
                _binding = binding
            }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            updateStatusBarInsets(root, appBarSettingsDisplay)

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

            toolbarSettingsDisplay.setupWithNavController(findNavController())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}