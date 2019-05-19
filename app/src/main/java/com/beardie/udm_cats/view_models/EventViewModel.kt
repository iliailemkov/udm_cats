package com.beardie.udm_cats.view_models

import androidx.lifecycle.ViewModel
import com.beardie.udm_cats.local.models.Event
import com.beardie.udm_cats.remote.RemoteApi
import retrofit2.Call
import retrofit2.Callback
import javax.inject.Inject

class EventViewModel @Inject constructor() : ViewModel() {

  fun addEvent(event: Event) = RemoteApi.create().addEvent(event).enqueue(object : Callback<Event> {
    override fun onFailure(call: Call<Event>, t: Throwable) {
    }

    override fun onResponse(call: Call<Event>, response: retrofit2.Response<Event>) {
    }
  })
}