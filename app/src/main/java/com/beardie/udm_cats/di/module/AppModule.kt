package com.beardie.udm_cats.di.module

import android.app.Application
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.beardie.udm_cats.repository.map.MapRepository
import com.beardie.udm_cats.repository.shared.SharedRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {
  @Provides
  @Singleton
  fun provideSharedPreference(app: Application): SharedPreferences =
    PreferenceManager.getDefaultSharedPreferences(app)

  @Provides
  @Singleton
  fun provideMapRepository(): MapRepository =
    MapRepository()

  @Provides
  @Singleton
  fun provideSharedPrefRepository(prefs: SharedPreferences): SharedRepository =
    SharedRepository(prefs)
}