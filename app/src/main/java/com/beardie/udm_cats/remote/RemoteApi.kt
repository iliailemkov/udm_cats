package com.beardie.udm_cats.remote

import com.beardie.udm_cats.local.models.Event
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

interface RemoteApi {

  @POST("/api/Authentication")
  fun login(@Body userCredentials: UserCredentials): Call<String>

  @GET("/api/Events")
  fun getAllEvents(): Call<List<Event>>

  @Headers(
    value =
    ["accept: text/plain", "Content-Type: application/json-patch+json"]
  )
  @POST("/api/Events")
  fun addEvent(@Body event: Event): Call<Event>

  companion object {

    private val gson: Gson
      get() = GsonBuilder().setLenient().create()

    fun create(): RemoteApi {

      val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(gson))
        .callbackExecutor(Executors.newSingleThreadExecutor())
        .client(
          OkHttpClient.Builder().addInterceptor(
            HeaderInterceptor()
          )
            .connectTimeout(60, TimeUnit.SECONDS).build()
        )
        .baseUrl("https://hackathon.directum.ru/")
        .build()

      return retrofit.create(RemoteApi::class.java)
    }
  }
}