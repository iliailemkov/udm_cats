package com.beardie.udm_cats.repository.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.beardie.udm_cats.local.models.Event
import com.beardie.udm_cats.remote.RemoteApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class MapRepository @Inject constructor(
) {
  fun getEvents(): LiveData<List<Event>> {
    val data = MutableLiveData<List<Event>>()
    RemoteApi.create().getAllEvents().enqueue(object : Callback<List<Event>> {
      override fun onFailure(call: Call<List<Event>>, t: Throwable) {

      }

      override fun onResponse(call: Call<List<Event>>, response: Response<List<Event>>) {
        data.postValue(response.body())
      }
    })
    return data
  }
}