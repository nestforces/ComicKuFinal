package com.example.comickufinal.repository.factory

import androidx.paging.DataSource
import com.example.comickufinal.model.DetailMangaResponse
import com.example.comickufinal.repository.AllContentRepository
import com.example.comickufinal.repository.datasource.ChapterDataSource
import io.reactivex.rxjava3.disposables.CompositeDisposable

class ChapterDataFactory(
    private val endpoint: String,
    private val allContentRepository: AllContentRepository,
    private val compositeDisposable: CompositeDisposable
) : DataSource.Factory<Int, DetailMangaResponse.Chapter>() {
    override fun create(): DataSource<Int, DetailMangaResponse.Chapter> {
        return ChapterDataSource(endpoint, allContentRepository, compositeDisposable)
    }
}