package com.beardie.udm_cats

import com.beardie.udm_cats.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class BaseApplication : DaggerApplication() {

  companion object {
    private lateinit var instance: BaseApplication
    fun getInstance() = instance
  }

  override fun onCreate() {
    super.onCreate()
    instance = this
    Thread.setDefaultUncaughtExceptionHandler { paramThread, paramThrowable -> {} }
  }

  override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
    return DaggerAppComponent
      .builder()
      .create(this)
      .build()
  }
}

