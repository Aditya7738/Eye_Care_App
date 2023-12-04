package com.example.eyecareapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.eyecareapp.repository.JSONRepository

class UIViewModelFactory(private val repository: JSONRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return UIViewModel(repository) as T
    }
}