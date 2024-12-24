package com.reels.tv

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {
    private val _searchResults = MutableLiveData<List<Reel>>()
    val searchResults: LiveData<List<Reel>> = _searchResults

    private val reelsRepository = ReelsRepository()

    fun search(query: String) {
        viewModelScope.launch {
            try {
                val results = reelsRepository.getReels(query)
                _searchResults.value = results
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
}