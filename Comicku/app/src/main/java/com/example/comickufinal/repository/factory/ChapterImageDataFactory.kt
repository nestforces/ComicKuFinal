package com.example.comickufinal.repository.factory

import androidx.paging.DataSource
import com.example.comickufinal.model.ChapterMangaResponse
import com.example.comickufinal.repository.AllContentRepository
import com.example.comickufinal.repository.datasource.ChapterImageDataSource
import io.reactivex.rxjava3.disposables.CompositeDisposable

class ChapterImageDataFactory(
    private val endpoint: String,
    private val allContentRepository: AllContentRepository,
    private val compositeDisposable: CompositeDisposable
) : DataSource.Factory<Int, ChapterMangaResponse.ChapterImage>() {
    override fun create(): DataSource<Int, ChapterMangaResponse.ChapterImage> {
        return ChapterImageDataSource(endpoint, allContentRepository, compositeDisposable)
    }
}
