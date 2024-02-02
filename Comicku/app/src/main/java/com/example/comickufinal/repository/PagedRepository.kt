package com.example.comickufinal.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.comickufinal.model.*
import com.example.comickufinal.model.helper.MangaHelper
import com.example.comickufinal.network.NetworkState
import com.example.comickufinal.repository.factory.*
import io.reactivex.rxjava3.disposables.CompositeDisposable
import java.util.concurrent.Executors

class PagedRepository(context: Context) {
    companion object {
        @Volatile
        private var INSTANCE: PagedRepository? = null

        fun getInstance(context: Context): PagedRepository =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: PagedRepository(context).also { INSTANCE = it }
            }
    }

    private val allContentRepository = AllContentRepository.getInstance(context)
    private val postPerPage = 10
    private val executor = Executors.newFixedThreadPool(5)
    private val pageListConfig = PagedList.Config.Builder()
        .setPageSize(postPerPage)
        .setEnablePlaceholders(false)
        .build()
    private val _networkState = allContentRepository.networkState

    val networkState: LiveData<NetworkState>
        get() {
            // tidak tau kenapa yang kepanggil adalah loaded duluan,
            // maka ny harus di ubah ke loading
            _networkState.value = NetworkState.LOADING
            return _networkState
        }

    fun allPopularManga(
        compositeDisposable: CompositeDisposable
    ): LiveData<PagedList<PopularMangaResponse.Manga>> {
        val popularMangaDataFactory =
            PopularMangaDataFactory(allContentRepository, compositeDisposable)

        return LivePagedListBuilder(popularMangaDataFactory, pageListConfig)
            .setFetchExecutor(executor)
            .build()
    }

    fun allExplore(
        compositeDisposable: CompositeDisposable
    ): LiveData<PagedList<ExploreResponse.Manga>> {
        val exploreDataFactory =
            ExploreDataFactory(allContentRepository, compositeDisposable)

        return LivePagedListBuilder(exploreDataFactory, pageListConfig)
            .setFetchExecutor(executor)
            .build()
    }

    fun allManga(
        compositeDisposable: CompositeDisposable
    ): LiveData<PagedList<MangaResponse.Manga>> {
        val mangaDataFactory =
            MangaDataFactory(MangaHelper.Type.manga, allContentRepository, compositeDisposable)


        return LivePagedListBuilder(mangaDataFactory, pageListConfig)
            .setFetchExecutor(executor)
            .build()
    }

    fun allManhua(
        compositeDisposable: CompositeDisposable
    ): LiveData<PagedList<MangaResponse.Manga>> {
        val mangaDataFactory =
            MangaDataFactory(MangaHelper.Type.manhua, allContentRepository, compositeDisposable)

        return LivePagedListBuilder(mangaDataFactory, pageListConfig)
            .setFetchExecutor(executor)
            .build()
    }

    fun allManhwa(
        compositeDisposable: CompositeDisposable
    ): LiveData<PagedList<MangaResponse.Manga>> {
        val mangaDataFactory =
            MangaDataFactory(MangaHelper.Type.manhwa, allContentRepository, compositeDisposable)

        return LivePagedListBuilder(mangaDataFactory, pageListConfig)
            .setFetchExecutor(executor)
            .build()
    }

    fun allGenreManga(
        genreEndpoint: String,
        compositeDisposable: CompositeDisposable
    ): LiveData<PagedList<GenreMangaResponse.Manga>> {
        val genreMangaDataFactory =
            GenreMangaDataFactory(genreEndpoint, allContentRepository, compositeDisposable)

        return LivePagedListBuilder(genreMangaDataFactory, pageListConfig)
            .setFetchExecutor(executor)
            .build()
    }

    fun searchManga(
        keyword: String,
        compositeDisposable: CompositeDisposable
    ): LiveData<PagedList<SearchMangaResponse.SearchMangaResponseItem>> {
        val searchMangaDataFactory =
            SearchMangaDataFactory(keyword, allContentRepository, compositeDisposable)

        return LivePagedListBuilder(searchMangaDataFactory, pageListConfig)
            .setFetchExecutor(executor)
            .build()
    }
}