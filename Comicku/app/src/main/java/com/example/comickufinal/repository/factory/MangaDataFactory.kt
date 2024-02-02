package com.example.comickufinal.repository.factory

import androidx.paging.DataSource
import com.example.comickufinal.model.MangaResponse
import com.example.comickufinal.repository.AllContentRepository
import com.example.comickufinal.repository.datasource.MangaDataSource
import io.reactivex.rxjava3.disposables.CompositeDisposable

class MangaDataFactory(
    private val type: String,
    private val allContentRepository: AllContentRepository,
    private val compositeDisposable: CompositeDisposable
) : DataSource.Factory<Int, MangaResponse.Manga>() {
    override fun create(): DataSource<Int, MangaResponse.Manga> {
        return MangaDataSource(type, allContentRepository, compositeDisposable)
    }
}