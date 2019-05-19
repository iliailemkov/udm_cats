package com.beardie.udm_cats.di.module

import com.beardie.udm_cats.MainActivity
import com.beardie.udm_cats.ui.event.EventFragment
import com.beardie.udm_cats.ui.login.LoginFragment
import com.beardie.udm_cats.ui.map.MapFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ViewModule {

  @ContributesAndroidInjector
  abstract fun mainActivity(): MainActivity

  @ContributesAndroidInjector
  abstract fun loginFragment(): LoginFragment

  @ContributesAndroidInjector
  abstract fun mapFragment(): MapFragment

  @ContributesAndroidInjector
  abstract fun eventFragment(): EventFragment
}
