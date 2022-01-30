package com.example.currencyconverterapp.main

import com.example.currencyconverterapp.api.CurrencyApi
import com.example.currencyconverterapp.data.model.CurrencyResponse
import com.example.currencyconverterapp.utils.Resources
import javax.inject.Inject

class DefaultRepository @Inject constructor(
    private val api: CurrencyApi
):CurrencyRepository{

    override suspend fun get(base: String): Resources<CurrencyResponse> {
        return try{
            val response = api.convertTo(base)
            val result = response.body()
            if(response.isSuccessful && result != null){
                Resources.Sucess(result)
            }else{
                Resources.Error(response.message())
            }

        }catch(e:Exception){
            Resources.Error(e.message ?: "An error message")
        }
    }


}