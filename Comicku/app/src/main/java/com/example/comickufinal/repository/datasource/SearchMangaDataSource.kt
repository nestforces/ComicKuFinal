package com.example.comickufinal.repository.datasource

import android.util.Log
import androidx.paging.PositionalDataSource
import com.example.comickufinal.model.SearchMangaResponse
import com.example.comickufinal.network.NetworkState
import com.example.comickufinal.repository.AllContentRepository
import io.reactivex.rxjava3.disposables.CompositeDisposable

class SearchMangaDataSource(
    private val keyword: String,
    private val allContentRepository: AllContentRepository,
    private val compositeDisposable: CompositeDisposable
) : PositionalDataSource<SearchMangaResponse.SearchMangaResponseItem>() {
    private val networkState = allContentRepository.networkState

    override fun loadInitial(
        params: LoadInitialParams,
        callback: LoadInitialCallback<SearchMangaResponse.SearchMangaResponseItem>
    ) {
        compositeDisposable.add(
            allContentRepository.getSearchManga(keyword)
                .subscribe({
                    Log.d("SearchMangaDataSource", "loadInitial: Masuk")
                    callback.onResult(it.data, 0)
                    networkState.postValue(NetworkState.LOADED)
                }, {
                    networkState.postValue(NetworkState.ERROR)
                })
        )
    }

    override fun loadRange(
        params: LoadRangeParams,
        callback: LoadRangeCallback<SearchMangaResponse.SearchMangaResponseItem>
    ) {
        // who care
    }
}