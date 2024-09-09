package com.dag.myapplication.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.dag.myapplication.MetamaskDemoApplication
import com.dag.myapplication.services.ApiLogger
import com.dag.myapplication.services.ApiServiceImpl
import com.dag.myapplication.services.ApiSource
import com.dag.myapplication.services.NetworkResponseAdapterFactory
import okhttp3.logging.HttpLoggingInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideApiSource(retrofit: Retrofit): ApiSource = ApiServiceImpl(retrofit)

    @Provides
    @Singleton
    fun provideRetrofit(@ApplicationContext context: Context): Retrofit {
        val logger = HttpLoggingInterceptor(ApiLogger())
        logger.level = HttpLoggingInterceptor.Level.BODY
        val interceptor = HttpLoggingInterceptor()

        val httpClient = OkHttpClient.Builder()
            .addInterceptor(
                ChuckerInterceptor.Builder(context)
                    .collector(ChuckerCollector(context))
                    .maxContentLength(250000L)
                    .redactHeaders(emptySet())
                    .alwaysReadResponseBody(true)
                    .build()
            )
            .addInterceptor(logger)
            .addInterceptor(interceptor)
            .build()
        var url = MetamaskDemoApplication.baseUrl
        return Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(httpClient)
            .build()
    }
}