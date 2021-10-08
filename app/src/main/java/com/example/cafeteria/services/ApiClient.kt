package com.example.cafeteria.services

import android.content.Context
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

//BASE URL
private const val BASE_URL = "http://daniel100-001-site1.ftempurl.com/"

class ApiClient(context: Context) {

    //create logger:
    private val logger = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    /**
     * Create custom interceptor to apply Headers application wide
     * Note: for every call (CRUD) the interceptor will show headers
     * */
    /*private val headerInterceptor: Interceptor = object : Interceptor {
        override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
            var request: Request = chain.request()
            request = request.newBuilder()
                .addHeader("x-device-type", Build.DEVICE)
                .addHeader("Accept-Language", Locale.getDefault().language)
                .build()
            return chain.proceed(request)
        }
    }*/

    // Create OkHttp Client
    private val okHttp = OkHttpClient.Builder()
        //The normal time out is 10 seconds
        //call == read and write and connect timeouts
        .callTimeout(8, TimeUnit.SECONDS)
        //.addInterceptor(headerInterceptor)
        .addInterceptor(RequestInterceptor(context))
        .authenticator(TokenAuthenticator(context))
        .addInterceptor(logger)


    private val builder = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttp.build())

    private val retrofit: Retrofit = builder.build()

    fun <T> buildService(serviceType: Class<T>): T {
        return retrofit.create(serviceType)
    }
}
