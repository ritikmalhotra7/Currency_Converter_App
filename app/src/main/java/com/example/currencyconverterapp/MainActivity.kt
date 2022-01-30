package com.example.currencyconverterapp

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.example.currencyconverterapp.databinding.ActivityMainBinding
import com.example.currencyconverterapp.main.MainViewModel
import com.example.currencyconverterapp.utils.CurrencyEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var _binding : ActivityMainBinding? = null
    private val binding
    get() = _binding!!
    private val viewModel : MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnConvert.setOnClickListener {
            val amount = binding.etFrom.text.toString()
            val from = binding.spFromCurrency.selectedItem.toString()
            val to = binding.spToCurrency.selectedItem.toString()
            viewModel.convert(amount,from,to)
        }
        lifecycleScope.launchWhenStarted {
            viewModel.conversion.collect {event->
                when(event){
                    is CurrencyEvent.Sucess ->{
                        binding.progressBar.isVisible = false
                        binding.tvResult.setTextColor(Color.BLACK)
                        binding.tvResult.text = event.resultText
                    }
                    is CurrencyEvent.Failure ->{
                        binding.progressBar.isVisible = false
                        binding.tvResult.setTextColor(Color.BLACK)
                        binding.tvResult.text = event.errorText
                    }
                    is CurrencyEvent.Loading ->{
                        binding.progressBar.isVisible = true
                    }
                    else -> Unit
                }
            }
        }
    }
}