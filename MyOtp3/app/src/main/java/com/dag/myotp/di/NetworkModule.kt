package com.dag.myotp.di

import android.content.Context
import com.dag.myotp.MyOtpApplication
import com.dag.myotp.services.ApiLogger
import com.dag.myotp.services.ApiServiceImpl
import com.dag.myotp.services.ApiSource
import com.dag.myotp.services.Datastore

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

    @Singleton
    @Provides
    fun providePreferencesDatastore(context:Context) = Datastore(context)

    @Provides
    @Singleton
    fun provideRetrofit(@ApplicationContext context: Context): Retrofit {
        val logger = HttpLoggingInterceptor(ApiLogger())
        logger.level = HttpLoggingInterceptor.Level.BODY
        val interceptor = HttpLoggingInterceptor()

        val httpClient = OkHttpClient.Builder()
            .addInterceptor(logger)
            .addInterceptor(interceptor)
            .build()
        var url = MyOtpApplication.baseUrl
        return Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(httpClient)
            .build()
    }
}