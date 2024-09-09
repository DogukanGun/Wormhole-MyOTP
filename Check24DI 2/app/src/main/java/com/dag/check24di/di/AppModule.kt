package com.dag.check24di.di

import androidx.room.Room
import com.dag.check24di.dao.ProductDatabase
import com.dag.check24di.feature.home.HomeVM
import com.dag.check24di.feature.product.ProductVM
import com.dag.check24di.retrofit.ApiServiceImpl
import com.dag.check24di.retrofit.ApiSource
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


val appModule = module {
    single<Retrofit> {
        val httpClient = OkHttpClient.Builder()
            .build()
        Retrofit.Builder()
            .baseUrl("https://app.check24.de/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(httpClient)
            .build()
    }
    single<ApiSource>  {
        ApiServiceImpl(get(Retrofit::class))
    }
    single<ProductDatabase> {
        Room.databaseBuilder(
            context = androidContext(),
            klass = ProductDatabase::class.java,
            name = "product.db"
        ).build()
    }
    viewModel<HomeVM> { HomeVM(get(ApiSource::class),get(ProductDatabase::class)) }
    viewModel<ProductVM> { ProductVM(get(ApiSource::class),get(ProductDatabase::class)) }


}