package com.beardie.udm_cats.view_models.map

import androidx.lifecycle.ViewModel
import com.beardie.udm_cats.repository.map.MapRepository
import javax.inject.Inject

class MapViewModel @Inject constructor(
  private var mapRepository: MapRepository
) : ViewModel() {

  fun getEvents() = mapRepository.getEvents()
}