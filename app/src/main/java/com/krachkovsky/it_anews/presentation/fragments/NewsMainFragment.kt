package com.krachkovsky.it_anews.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.krachkovsky.it_anews.R
import com.krachkovsky.it_anews.databinding.FragmentMainBinding

class NewsMainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = requireNotNull(_binding)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentMainBinding.inflate(inflater, container, false)
            .also { binding ->
                _binding = binding
            }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            val navController =
                (childFragmentManager.findFragmentById(R.id.nav_container_fragment_main) as NavHostFragment)
                    .navController
            mainBottomNavigation.setupWithNavController(navController)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}