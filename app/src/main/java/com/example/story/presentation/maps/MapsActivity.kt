package com.example.story.presentation.maps

import android.content.res.Resources
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.story.R
import com.example.story.data.Result
import com.example.story.data.response.Story
import com.example.story.databinding.ActivityMapsBinding
import com.example.story.utils.ViewModelFactory
import com.example.story.widget.CustomAlertDialog
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : AppCompatActivity() {

    private var _binding: ActivityMapsBinding? = null
    private val binding get() = _binding!!
    private lateinit var factory: ViewModelFactory
    private val mapsViewModel: MapViewModel by viewModels { factory }
    private val boundsBuilder = LatLngBounds.Builder()

    private val callback = OnMapReadyCallback { googleMap ->
        googleMap.uiSettings.isZoomControlsEnabled = true
        googleMap.uiSettings.isIndoorLevelPickerEnabled = true
        googleMap.uiSettings.isCompassEnabled = true
        googleMap.uiSettings.isMapToolbarEnabled = true

        setupViewModel()

        val dummyLocation = LatLng(-7.315478, 112.725754)
        googleMap.addMarker(
            MarkerOptions()
                .position(dummyLocation)
                .title("Marker in dummyLocation")
        )
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(dummyLocation, 15f))

        setMapStyle(googleMap)
        getStoryWithLocation(googleMap)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    private fun setupViewModel() {
        factory = ViewModelFactory.getInstance(binding.root.context)
    }

    private fun getStoryWithLocation(googleMap: GoogleMap) {
        mapsViewModel.getStories().observe(this) { result ->
            if (result != null) {
                when(result) {
                    is Result.Loading -> {
                        loadingHandler(true)
                    }
                    is Result.Error -> {
                        loadingHandler(false)
                        errorHandler()
                    }
                    is Result.Success -> {
                        loadingHandler(false)
                        showMarker(result.data.listStory, googleMap)
                    }
                }
            }
        }
    }

    private fun showMarker(listStory: List<Story>, googleMap: GoogleMap) {
        listStory.forEach { story ->
            val latLng = LatLng(story.lat, story.lon)
            googleMap.addMarker(
                MarkerOptions()
                    .position(latLng)
                    .title(story.name)
                    .snippet(StringBuilder("Created at: ")
                        .append(story.createdAt.subSequence(11, 16).toString())
                        .toString()
                    )
            )
            boundsBuilder.include(latLng)
        }
    }

    private fun errorHandler() {
        CustomAlertDialog(binding.root.context, R.string.error_message, R.drawable.error).show()
    }

    private fun setMapStyle(googleMap: GoogleMap) {
        try {
            val success =
                googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(binding.root.context, R.raw.map_style))
            if (!success) {
                CustomAlertDialog(binding.root.context, R.string.error_message, R.drawable.error).show()
            }
        } catch (exception: Resources.NotFoundException) {
            CustomAlertDialog(binding.root.context, R.string.error_message, R.drawable.error).show()
        }
    }

    private fun loadingHandler(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBarMap.visibility = View.VISIBLE
            binding.root.visibility = View.INVISIBLE
        } else {
            binding.progressBarMap.visibility = View.INVISIBLE
            binding.root.visibility = View.VISIBLE
        }
    }
}