package com.krachkovsky.it_anews.presentation.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.LocationSource
import com.google.android.gms.maps.model.LatLng
import com.krachkovsky.it_anews.databinding.FragmentMapBinding
import com.krachkovsky.it_anews.presentation.LocationService
import com.krachkovsky.it_anews.presentation.updateStatusBarInsets
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class NewsMapFragment : Fragment() {

    private var _binding: FragmentMapBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val locationService by lazy {
        LocationService(requireContext())
    }

    private val permissionLocation = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isEnabled ->
        setLocationEnabled(isEnabled)
        if (isEnabled) {
            viewLifecycleOwner.lifecycleScope.launch {
                locationService.getLocation()?.let(::moveCameraToLocation)
            }
            locationService
                .locationFlow()
                .onEach { location ->
                    locationListener?.onLocationChanged(location)
                }.launchIn(viewLifecycleOwner.lifecycleScope)
        }
    }

    private var myGoogleMap: GoogleMap? = null
    private var locationListener: LocationSource.OnLocationChangedListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentMapBinding.inflate(inflater, container, false)
            .also { binding ->
                _binding = binding
            }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        permissionLocation.launch(Manifest.permission.ACCESS_FINE_LOCATION)

        with(binding) {
            updateStatusBarInsets(root, appBarMap)

            mapView.getMapAsync { googleMap ->

                googleMap.apply {
                    uiSettings.isCompassEnabled = true
                    uiSettings.isZoomControlsEnabled = true
                    uiSettings.isMyLocationButtonEnabled = true
                }

                googleMap.isMyLocationEnabled =
                    ContextCompat.checkSelfPermission(
                        view.context,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED

                googleMap.setLocationSource(object : LocationSource {
                    override fun activate(listener: LocationSource.OnLocationChangedListener) {
                        locationListener = listener
                    }

                    override fun deactivate() {
                        locationListener = null
                    }
                })

                myGoogleMap = googleMap
            }
            mapView.onCreate(savedInstanceState)
        }
    }

    override fun onResume() {
        binding.mapView.onResume()
        super.onResume()
    }

    override fun onPause() {
        binding.mapView.onPause()
        super.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        binding.mapView.onSaveInstanceState(outState)
        super.onSaveInstanceState(outState)
    }

    override fun onDestroyView() {
        binding.mapView.onDestroy()
        _binding = null
        super.onDestroyView()
    }

    @SuppressLint("MissingPermission")
    private fun setLocationEnabled(enabled: Boolean) {
        myGoogleMap?.isMyLocationEnabled = enabled
        myGoogleMap?.uiSettings?.isMyLocationButtonEnabled = enabled
    }

    private fun moveCameraToLocation(location: Location) {
        val current = LatLng(location.latitude, location.longitude)
        myGoogleMap?.animateCamera(
            CameraUpdateFactory.newLatLngZoom(current, DEFAULT_CAMERA_ZOOM)
        )
    }

    companion object {
        private const val DEFAULT_CAMERA_ZOOM = 17f
    }
}