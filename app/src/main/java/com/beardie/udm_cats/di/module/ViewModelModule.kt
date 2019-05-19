package com.beardie.udm_cats.di.module

import androidx.lifecycle.ViewModel
import com.beardie.udm_cats.di.ViewModelKey
import com.beardie.udm_cats.view_models.EventViewModel
import com.beardie.udm_cats.view_models.login.LoginViewModel
import com.beardie.udm_cats.view_models.main.MainViewModel
import com.beardie.udm_cats.view_models.map.MapViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
internal abstract class ViewModelModule {
  @Binds
  @IntoMap
  @ViewModelKey(LoginViewModel::class)
  abstract fun bindLoginViewModel(viewModel: LoginViewModel): ViewModel

  @Binds
  @IntoMap
  @ViewModelKey(MainViewModel::class)
  abstract fun bindMainViewModel(viewModel: MainViewModel): ViewModel

  @Binds
  @IntoMap
  @ViewModelKey(MapViewModel::class)
  abstract fun bindMapViewModel(viewModel: MapViewModel): ViewModel

  @Binds
  @IntoMap
  @ViewModelKey(EventViewModel::class)
  abstract fun bindEventViewModel(viewModel: EventViewModel): ViewModel
}