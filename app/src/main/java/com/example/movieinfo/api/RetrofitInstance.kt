package com.example.movieinfo.api

import com.example.movieinfo.util.Constants.Companion.BASE_URL
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {
    companion object {
        private val retrofit by lazy {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            val httpClient = okhttp3.OkHttpClient.Builder().
                                addInterceptor(logging).build()
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build()
        }
        val api by lazy {
            retrofit.create(MoviesApi::class.java)
        }
    }

}