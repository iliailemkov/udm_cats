package com.beardie.udm_cats.repository.shared

import android.content.SharedPreferences
import javax.inject.Inject

class SharedRepository @Inject constructor(private var preferences: SharedPreferences) {

  companion object {
    const val AUTH_TOKEN = "AuthToken"
  }

  fun setAuthToken(token: String) {
    preferences.edit().putString(AUTH_TOKEN, token).apply()
  }

  fun getAuthToken() = preferences.getString(AUTH_TOKEN, null)
}