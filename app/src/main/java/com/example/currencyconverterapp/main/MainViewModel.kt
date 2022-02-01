package com.example.currencyconverterapp.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconverterapp.utils.CurrencyEvent
import com.example.currencyconverterapp.utils.DispatcherProvider
import com.example.currencyconverterapp.utils.Resources
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.round


@HiltViewModel
class MainViewModel @Inject constructor(
    private val repo : CurrencyRepository ,
    private val dispatchers:DispatcherProvider
) : ViewModel() {



    private val _conversion = MutableStateFlow<CurrencyEvent>(CurrencyEvent.Empty)
    val conversion:StateFlow<CurrencyEvent> = _conversion

    fun convert(amount:String,from:String,to:String){
        val amountValue = amount.toDoubleOrNull()
        if(amountValue == null){
            _conversion.value = CurrencyEvent.Failure("Not a valid amount")
            return
        }
        Log.d("viewModel",amountValue.toString())

        viewModelScope.launch(dispatchers.io) {
            _conversion.value = CurrencyEvent.Loading
            when(val response = repo.get("${from}_$to")){
                is Resources.Error ->{
                    _conversion.value = CurrencyEvent.Failure(response.message!!)
                    Log.d("viewModel","${from}_$to")
                }
                is Resources.Sucess ->{
                    Log.d("viewModel","${from}_$to")
                    val rate = response.data!!.fromTo
                    Log.d("viewModel",response.data!!.fromTo.toString())
                    if(rate == null){
                        _conversion.value = CurrencyEvent.Failure("Unexpected Errors")
                    }else{
                        val  resultCurrency = round(amountValue * rate * 100) / 100
                        _conversion.value = CurrencyEvent.Sucess("$resultCurrency $to = $amountValue $from")
                    }
                }
            }
        }
    }
}