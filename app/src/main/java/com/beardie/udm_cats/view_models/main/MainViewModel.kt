package com.beardie.udm_cats.view_models.main

import androidx.lifecycle.ViewModel
import com.beardie.udm_cats.repository.shared.SharedRepository
import javax.inject.Inject

class MainViewModel @Inject constructor(
  private val sharedRepository: SharedRepository
) : ViewModel() {

  fun authinticated(): Boolean {
    if (sharedRepository.getAuthToken() != null)
      return true
    return false
  }

  fun getAuthToken() = sharedRepository.getAuthToken()
}