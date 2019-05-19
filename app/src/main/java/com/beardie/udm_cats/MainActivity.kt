package com.beardie.udm_cats

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.beardie.udm_cats.di.ViewModelFactory
import com.beardie.udm_cats.navigation.INavigation
import com.beardie.udm_cats.ui.login.LoginFragment
import com.beardie.udm_cats.ui.map.MapFragment
import com.beardie.udm_cats.view_models.main.MainViewModel
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity(), INavigation {

  @Inject
  lateinit var viewModelFactory: ViewModelFactory

  private lateinit var viewModel: MainViewModel

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    viewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)
    setContentView(R.layout.activity_main)
    supportActionBar?.hide()
    if (viewModel.authinticated())
      navigateToWithReplace(MapFragment.newInstance())
    else
      navigateToWithReplace(LoginFragment.newInstance())
  }

  override fun navigateTo(fragment: Fragment) {
    supportFragmentManager.beginTransaction().replace(R.id.main_container, fragment).addToBackStack("navigateTo")
      .commit()
  }

  override fun navigateToWithReplace(fragment: Fragment) {
    supportFragmentManager.beginTransaction().replace(R.id.main_container, fragment).disallowAddToBackStack()
      .commit()
  }

  override fun navigateBack() {
    supportFragmentManager.popBackStack()
  }
}
