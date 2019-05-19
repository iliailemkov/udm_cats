package com.beardie.udm_cats.remote

import android.preference.PreferenceManager
import android.text.TextUtils
import com.beardie.udm_cats.BaseApplication
import com.beardie.udm_cats.repository.shared.SharedRepository
import okhttp3.Interceptor
import java.io.IOException

class HeaderInterceptor : Interceptor {
  @Throws(IOException::class)
  override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
    val request = chain.request()
    val builder = request.newBuilder()
    val token = PreferenceManager.getDefaultSharedPreferences(BaseApplication.getInstance()).getString(
      SharedRepository.AUTH_TOKEN,
      ""
    )
    if (!TextUtils.isEmpty(token))
      builder.addHeader(
        "Authorization",
        "Bearer $token"
      )
    return chain.proceed(builder.build())
  }
}