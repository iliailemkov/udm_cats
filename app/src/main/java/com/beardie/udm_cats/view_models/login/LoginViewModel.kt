package com.beardie.udm_cats.view_models.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.beardie.udm_cats.remote.RemoteApi
import com.beardie.udm_cats.remote.UserCredentials
import com.beardie.udm_cats.repository.shared.SharedRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class LoginViewModel @Inject constructor(private val sharedRepository: SharedRepository) : ViewModel() {

  var isLogging: Boolean = false

  fun login(login: String, password: String): LiveData<String> {
    val data = MutableLiveData<String>()
    RemoteApi.create().login(UserCredentials(login, password)).enqueue(object : Callback<String> {

      override fun onResponse(call: Call<String>, response: Response<String>) {
        if (response.isSuccessful) {
          isLogging = true
          saveAuthToken(response.body()!!)
        }
        data.postValue(response.body())
      }

      override fun onFailure(call: Call<String>, t: Throwable) {
      }

    })

    return data
  }

  fun saveAuthToken(token: String) {
    sharedRepository.setAuthToken(token)
  }

}