package com.example.comickufinal.repository.factory

import androidx.paging.DataSource
import com.example.comickufinal.model.PopularMangaResponse
import com.example.comickufinal.repository.AllContentRepository
import com.example.comickufinal.repository.datasource.PopularMangaDataSource
import io.reactivex.rxjava3.disposables.CompositeDisposable

class PopularMangaDataFactory(
    private val allContentRepository: AllContentRepository,
    private val compositeDisposable: CompositeDisposable
) : DataSource.Factory<Int, PopularMangaResponse.Manga>() {
    override fun create(): DataSource<Int, PopularMangaResponse.Manga> {
        return PopularMangaDataSource(allContentRepository, compositeDisposable)
    }
}