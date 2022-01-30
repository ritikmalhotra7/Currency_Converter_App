package com.example.currencyconverterapp.di

import com.example.currencyconverterapp.api.CurrencyApi
import com.example.currencyconverterapp.main.CurrencyRepository
import com.example.currencyconverterapp.main.DefaultRepository
import com.example.currencyconverterapp.utils.DispatcherProvider
import com.example.currencyconverterapp.utils.Utils.Companion.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.Dispatcher
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule{

    @Singleton
    @Provides
    fun provideCurrencyApi():CurrencyApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(CurrencyApi::class.java)

    @Singleton
    @Provides
    fun provideCurrencyRepository(api:CurrencyApi):CurrencyRepository = DefaultRepository(api)

    @Singleton
    @Provides
    fun provideDispatchers():DispatcherProvider = object :DispatcherProvider{
        override val main: CoroutineDispatcher
            get() = Dispatchers.Main
        override val io: CoroutineDispatcher
            get() = Dispatchers.IO
        override val default: CoroutineDispatcher
            get() = Dispatchers.Default
        override val unconfined: CoroutineDispatcher
            get() = Dispatchers.Unconfined
    }

}