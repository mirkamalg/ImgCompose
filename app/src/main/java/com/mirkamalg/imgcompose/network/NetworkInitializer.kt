package com.mirkamalg.imgcompose.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.mirkamalg.imgcompose.BuildConfig
import com.mirkamalg.imgcompose.utils.BASE_URL
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object NetworkInitializer {

    val moshi: Moshi = Moshi.Builder()
        .build()

    private var retrofit: Retrofit? = null

    private fun buildOkHttpClient(): OkHttpClient {
        val okHttpBuilder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            okHttpBuilder.addInterceptor(logging)
        }
        okHttpBuilder.apply {
            readTimeout(60, java.util.concurrent.TimeUnit.SECONDS)
            connectTimeout(60, java.util.concurrent.TimeUnit.SECONDS)
        }

        return okHttpBuilder.build()
    }

    private fun getClient(): Retrofit {
        retrofit = Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(buildOkHttpClient())
            .baseUrl(BASE_URL)
            .build()

        return retrofit as Retrofit
    }

    val photoServices: PhotoServices by lazy {
        getClient().create(PhotoServices::class.java)
    }
}