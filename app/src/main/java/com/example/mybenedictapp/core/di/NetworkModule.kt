package com.example.mybenedictapp.core.di

import android.util.Log
import com.example.mybenedictapp.BuildConfig
import com.example.mybenedictapp.core.network.EndpointConfig
import com.example.mybenedictapp.core.network.MovieDbApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import java.util.logging.Logger
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {

        val builder = OkHttpClient.Builder()
        val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
            setLevel(
                HttpLoggingInterceptor.Level.BODY
            )
        }
        builder.addInterceptor(httpLoggingInterceptor)
        return builder
            .addInterceptor(
                Interceptor { chain ->
                    val requestBuilder = chain.request().newBuilder()
                        .header("Content-Type", "application/json")
                        .header("Authorization", "Bearer ${BuildConfig.ACCESS_TKN}")
                    return@Interceptor chain.proceed(requestBuilder.build())
                }
            )
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build()
    }

//    @Provides
//    @Singleton
//    fun providesHttpLoggingInterceptor(): Interceptor = HttpLoggingInterceptor().apply {
//        setLevel(
//            HttpLoggingInterceptor.Level.BODY
//        )
//    }

    @Provides
    @Singleton
    fun movieDbApi(
        okHttpClient: OkHttpClient,
        endpointConfig: EndpointConfig,
    ): MovieDbApi {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(endpointConfig.baseApiUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieDbApi::class.java)
    }
}