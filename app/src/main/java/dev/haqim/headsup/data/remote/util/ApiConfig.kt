package dev.haqim.headsup.data.remote.util

import dev.haqim.headsup.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiConfig private constructor(
    private val okHttpClient: OkHttpClient.Builder
) {

    private fun createRetrofit(
        baseUrl: String = BuildConfig.BASE_URL,
    ): Retrofit {
        
        okHttpClient
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .writeTimeout(120, TimeUnit.SECONDS)


        if(BuildConfig.DEBUG){
            val loggingInterceptor =
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            okHttpClient
                .addInterceptor(loggingInterceptor)
        }


        return Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient.build())
            .build()
    }

    fun <ServiceClass> createService(
        serviceClass: Class<ServiceClass>
    ): ServiceClass {
        val retrofit = createRetrofit()
        return retrofit.create(serviceClass)
    }
    
    companion object{
        fun getInstance(okHttpClient: OkHttpClient.Builder): ApiConfig {
            return ApiConfig(okHttpClient)
        }
    }

}