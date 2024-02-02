package com.example.comickufinal.repository.factory

import androidx.paging.DataSource
import com.example.comickufinal.model.GenreMangaResponse
import com.example.comickufinal.repository.AllContentRepository
import com.example.comickufinal.repository.datasource.GenreMangaDataSource
import io.reactivex.rxjava3.disposables.CompositeDisposable

class GenreMangaDataFactory(
    private val genreEndpoint: String,
    private val allContentRepository: AllContentRepository,
    private val compositeDisposable: CompositeDisposable
) : DataSource.Factory<Int, GenreMangaResponse.Manga>() {
    override fun create(): DataSource<Int, GenreMangaResponse.Manga> {
        return GenreMangaDataSource(genreEndpoint, allContentRepository, compositeDisposable)
    }
}