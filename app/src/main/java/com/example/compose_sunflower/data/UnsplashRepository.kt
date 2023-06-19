package com.example.compose_sunflower.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.compose_sunflower.api.UnsplashService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UnsplashRepository @Inject constructor(private val service: UnsplashService) {

    fun getSearchResultStream(query: String): Flow<PagingData<UnsplashPhoto>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = 25),
            pagingSourceFactory = { UnsplashPagingSource(service, query) }
        ).flow
    }
}