package br.edu.ifsp.finances.utils

import br.edu.ifsp.finances.BuildConfig
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

fun retrofitBuilder(): Retrofit.Builder {
    val okHttpClientBuilder = OkHttpClient.Builder()
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)

    if (BuildConfig.DEBUG) {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        okHttpClientBuilder.addInterceptor(logging)
    }
    return Retrofit.Builder()
        .baseUrl("http://192.168.0.2:8080/")
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .client(okHttpClientBuilder.build())
}