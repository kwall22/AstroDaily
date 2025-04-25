package com.example.project3

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project3.api.AstroRepository
import com.example.project3.api.GridItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

private const val TAG = "AstroGridViewModel"

class AstroGridViewModel : ViewModel() {
    private val astroRepository = AstroRepository.get()

    private val _gridItems: MutableStateFlow<List<GridItem>> =
        MutableStateFlow(emptyList())
    val gridItems: StateFlow<List<GridItem>>
        get() = _gridItems.asStateFlow()

    init {
        viewModelScope.launch {
            try {
                val items = astroRepository.fetchPhotos()
                Log.d(TAG, "Items received: $items")
                _gridItems.value = items
            } catch (ex: Exception) {
                Log.e(TAG, "Failed to fetch grid items", ex)
            }
        }
    }
}