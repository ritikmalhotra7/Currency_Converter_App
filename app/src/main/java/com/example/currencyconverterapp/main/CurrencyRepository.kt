package com.example.currencyconverterapp.main

import com.example.currencyconverterapp.data.model.CurrencyResponse
import com.example.currencyconverterapp.utils.Resources

interface  CurrencyRepository {
    suspend fun get(base:String) : Resources<CurrencyResponse>
}