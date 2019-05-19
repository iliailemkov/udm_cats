package com.beardie.udm_cats.navigation

import androidx.fragment.app.Fragment

interface INavigation {
  fun navigateTo(fragment: Fragment)
  fun navigateToWithReplace(fragment: Fragment)
  fun navigateBack()
}