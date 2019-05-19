package com.beardie.udm_cats.di

import android.app.Application
import com.beardie.udm_cats.BaseApplication
import com.beardie.udm_cats.di.module.AppModule
import com.beardie.udm_cats.di.module.ViewModelModule
import com.beardie.udm_cats.di.module.ViewModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
  modules = [
    AndroidSupportInjectionModule::class,
    AppModule::class,
    ViewModule::class,
    ViewModelModule::class
  ]
)
interface AppComponent : AndroidInjector<BaseApplication> {
  @Component.Builder
  interface Builder {
    @BindsInstance
    fun create(app: Application): Builder

    fun build(): AppComponent
  }
}