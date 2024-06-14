package com.santhoshkumar.storeapp.api

import com.santhoshkumar.storeapp.utils.Constants.Companion.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// Retrofit is the external library act as the HttpClient
class RetrofitInstance {
    companion object {
        private val retrofit: Retrofit by lazy {
            //  Logging Interceptor is to intercept the request and response
            // Used for debugging purpose
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()

            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        //  CREATING RETROFIT INSTANCE
        val apiService: ApiService by lazy {
            retrofit.create(ApiService::class.java)
        }
    }


}