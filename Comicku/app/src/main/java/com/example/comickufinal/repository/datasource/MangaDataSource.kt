package com.example.comickufinal.repository.datasource

import androidx.paging.PageKeyedDataSource
import com.example.comickufinal.model.MangaResponse
import com.example.comickufinal.model.helper.MangaHelper
import com.example.comickufinal.network.NetworkState
import com.example.comickufinal.repository.AllContentRepository
import io.reactivex.rxjava3.disposables.CompositeDisposable

class MangaDataSource(
    private val type: String,
    private val allContentRepository: AllContentRepository,
    private val compositeDisposable: CompositeDisposable
) : PageKeyedDataSource<Int, MangaResponse.Manga>() {

    private var firstPage = 1
    private val networkState = allContentRepository.networkState

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, MangaResponse.Manga>
    ) {
        when (type) {
            MangaHelper.Type.manga -> {
                compositeDisposable.add(
                    allContentRepository.getAllManga(firstPage)
                        .map {
                            it.data.filter {
                                it.type == MangaHelper.Type.manga
                            }
                        }
                        .subscribe({
                            callback.onResult(
                                it, null, firstPage + 1
                            )
                            networkState.postValue(NetworkState.LOADED)
                        }, {
                            networkState.postValue(NetworkState.ERROR)
                        })
                )
            }
            MangaHelper.Type.manhua -> {
                compositeDisposable.add(
                    allContentRepository.getAllManhua(firstPage)
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
            MangaHelper.Type.manhwa -> {
                compositeDisposable.add(
                    allContentRepository.getAllManhwa(firstPage)
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
        }
    }

    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, MangaResponse.Manga>
    ) {
        // who care
    }

    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, MangaResponse.Manga>
    ) {
        when (type) {
            MangaHelper.Type.manga -> {
                compositeDisposable.add(
                    allContentRepository.getAllManga(params.key)
                        .map {
                            it.data.filter {
                                it.type == MangaHelper.Type.manga
                            }
                        }
                        .subscribe({
                            callback.onResult(
                                it, params.key + 1
                            )
                            networkState.postValue(NetworkState.LOADED)
                        }, {
                            networkState.postValue(NetworkState.ERROR)
                        })
                )
            }
            MangaHelper.Type.manhua -> {
                compositeDisposable.add(
                    allContentRepository.getAllManhua(params.key)
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
            MangaHelper.Type.manhwa -> {
                compositeDisposable.add(
                    allContentRepository.getAllManhwa(params.key)
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
    }
}