package com.example.currencyconverterapp.api

import com.example.currencyconverterapp.data.model.CurrencyResponse
import com.example.currencyconverterapp.utils.Utils.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface CurrencyApi {
    @GET("/api/v7/convert")
    suspend fun convertTo(
        @Query("q")
        from_to:String,
        @Query("compact")
        ultra:String = "ultra",
        @Query("apiKey")
        apiKey:String = API_KEY
    ): Response<CurrencyResponse>
}