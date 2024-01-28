package com.example.liftbuddy.di

import FoodViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.liftbuddy.data.repository.FoodRepository

class FoodViewModelFactory(private val foodRepository: FoodRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FoodViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FoodViewModel(foodRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
