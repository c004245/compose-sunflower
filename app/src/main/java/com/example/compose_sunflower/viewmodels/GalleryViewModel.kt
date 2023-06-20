package com.example.compose_sunflower.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.compose_sunflower.data.UnsplashRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    repository: UnsplashRepository
): ViewModel() {

    private var queryString: String? = savedStateHandle["plantName"]

    val plantPictures =
        repository.getSearchResultStream(queryString ?: "").cachedIn(viewModelScope)
}