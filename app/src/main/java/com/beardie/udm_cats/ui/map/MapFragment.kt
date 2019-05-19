package com.beardie.udm_cats.ui.map

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProviders
import com.beardie.udm_cats.BaseApplication
import com.beardie.udm_cats.R
import com.beardie.udm_cats.di.ViewModelFactory
import com.beardie.udm_cats.local.models.Event
import com.beardie.udm_cats.navigation.INavigation
import com.beardie.udm_cats.ui.event.EventFragment
import com.beardie.udm_cats.ui.profile.ProfileFragment
import com.beardie.udm_cats.view_models.map.MapViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.android.support.DaggerFragment
import java.util.*
import javax.inject.Inject


class MapFragment : DaggerFragment(), GoogleMap.OnMapLoadedCallback, OnMapReadyCallback,
  GoogleMap.OnMyLocationButtonClickListener, GoogleMap.OnMapLongClickListener {

  private lateinit var googleMap: GoogleMap
  private lateinit var mapView: MapView
  private lateinit var locationManager: LocationManager
  private lateinit var userAvatar: ImageView
  /**
   * Координаты Ижевска
   */
  private val startPosition = LatLng(56.87273200, 53.22717200)
  private val startZoom = 13.0f
  private var mapMarkerToEvent: TreeMap<String, Event> = TreeMap()

  @Inject
  lateinit var viewModelFactory: ViewModelFactory

  private lateinit var viewModel: MapViewModel

  companion object {
    fun newInstance() = MapFragment()
  }

  private fun checkGeolocationEnabled(): Boolean {
    if (activity == null)
      return false
    if (ActivityCompat.checkSelfPermission(
        activity!!,
        Manifest.permission.ACCESS_FINE_LOCATION
      ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
        activity!!,
        Manifest.permission.ACCESS_COARSE_LOCATION
      ) != PackageManager.PERMISSION_GRANTED
    )
      return false
    return true
  }

  @SuppressLint("MissingPermission")
  private fun initMap(map: GoogleMap) {
    googleMap = map
    if (checkGeolocationEnabled())
      googleMap.isMyLocationEnabled = true
    googleMap.uiSettings.isMyLocationButtonEnabled = true
    googleMap.uiSettings.isZoomControlsEnabled = true
    googleMap.setOnInfoWindowClickListener {

      val eventFragment = EventFragment.newInstance(
        mapMarkerToEvent[it.id]!!
      )
      eventFragment.show(activity?.supportFragmentManager, "NavigateToEventFragment")
    }
    googleMap.setOnMapLongClickListener(this)
    googleMap.setOnMapLoadedCallback(this)
    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(startPosition, startZoom))
  }

  private fun initMarkersByEvent(event: Event): MarkerOptions {
    val markerOptions = MarkerOptions()
    markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker))
    markerOptions.position(LatLng(event.altiitude, event.longitude))
    markerOptions.title(event.name)
    markerOptions.snippet(event.about)
    return markerOptions
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    locationManager =
      BaseApplication.getInstance().applicationContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    checkGeolocationEnabled()
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    val view = inflater.inflate(R.layout.map_fragment, container, false)
    userAvatar = view.findViewById(R.id.iv_user_avatar) as ImageView
    userAvatar.setOnClickListener { (activity as INavigation).navigateTo(ProfileFragment.newInstance()) }
    mapView = view.findViewById(R.id.mv_map) as MapView
    mapView.onCreate(savedInstanceState)
    mapView.onResume()
    mapView.getMapAsync(this)
    return view
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    viewModel = ViewModelProviders.of(this, viewModelFactory).get(MapViewModel::class.java)
    viewModel.getEvents().observe(this, androidx.lifecycle.Observer {
      it?.forEach { event ->
        run {
          val marker = googleMap.addMarker(initMarkersByEvent(event))
          mapMarkerToEvent[marker.id] = event
        }
      }
    })
  }

  override fun onMapLoaded() {
  }

  override fun onMapReady(map: GoogleMap) {
    if (isAdded)
      initMap(map)
  }

  override fun onMyLocationButtonClick(): Boolean {
    return true
  }

  override fun onMapLongClick(coord: LatLng?) {
    if (coord != null) {
      val markerOptions = MarkerOptions()
      markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker))
      markerOptions.position(coord)
      val eventFragment = EventFragment.newInstance(coord)
      eventFragment.show(activity?.supportFragmentManager, "NavigateToEventFragment")
    }
  }
}
