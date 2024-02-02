package com.example.comickufinal.repository.datasource


import androidx.paging.PageKeyedDataSource
import com.example.comickufinal.model.ExploreResponse
import com.example.comickufinal.model.PopularMangaResponse
import com.example.comickufinal.network.NetworkState
import com.example.comickufinal.repository.AllContentRepository
import io.reactivex.rxjava3.disposables.CompositeDisposable

class ExploreDataSource(
    private val allContentRepository: AllContentRepository,
    private val compositeDisposable: CompositeDisposable
) : PageKeyedDataSource<Int, ExploreResponse.Manga>() {

    private var firstPage = 1
    private val networkState = allContentRepository.networkState

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, ExploreResponse.Manga>
    ) {
        compositeDisposable.add(
            allContentRepository.getExplore(firstPage)
                .subscribe({
                    callback.onResult(
                        it.data, null, firstPage + 1
                    )
                    networkState.postValue(NetworkState.LOADED)
                }, {
                    networkState.postValue(NetworkState.ERROR)
                })
        )
    }

    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, ExploreResponse.Manga>
    ) {
        // who care
    }

    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, ExploreResponse.Manga>
    ) {
        compositeDisposable.add(
            allContentRepository.getExplore(params.key)
                .subscribe({
                    callback.onResult(
                        it.data, params.key + 1
                    )
                    networkState.postValue(NetworkState.LOADED)
                }, {
                    networkState.postValue(NetworkState.ERROR)
                })
        )
    }
}