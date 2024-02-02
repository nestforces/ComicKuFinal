package com.example.comickufinal.repository.factory

import androidx.paging.DataSource
import com.example.comickufinal.model.SearchMangaResponse
import com.example.comickufinal.repository.AllContentRepository
import com.example.comickufinal.repository.datasource.SearchMangaDataSource
import io.reactivex.rxjava3.disposables.CompositeDisposable

class SearchMangaDataFactory(
    private val keyword: String,
    private val allContentRepository: AllContentRepository,
    private val compositeDisposable: CompositeDisposable
) : DataSource.Factory<Int, SearchMangaResponse.SearchMangaResponseItem>() {
    override fun create(): DataSource<Int, SearchMangaResponse.SearchMangaResponseItem> {
        return SearchMangaDataSource(keyword, allContentRepository, compositeDisposable)
    }
}