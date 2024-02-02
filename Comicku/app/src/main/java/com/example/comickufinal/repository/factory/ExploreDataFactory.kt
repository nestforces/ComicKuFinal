package com.example.comickufinal.repository.factory

import androidx.paging.DataSource
import com.example.comickufinal.model.ExploreResponse
import com.example.comickufinal.model.PopularMangaResponse
import com.example.comickufinal.repository.AllContentRepository
import com.example.comickufinal.repository.datasource.ExploreDataSource
import com.example.comickufinal.repository.datasource.PopularMangaDataSource
import io.reactivex.rxjava3.disposables.CompositeDisposable

class ExploreDataFactory (
    private val allContentRepository: AllContentRepository,
    private val compositeDisposable: CompositeDisposable
    ) : DataSource.Factory<Int, ExploreResponse.Manga>() {
        override fun create(): DataSource<Int, ExploreResponse.Manga> {
            return ExploreDataSource(allContentRepository, compositeDisposable)
        }
    }
