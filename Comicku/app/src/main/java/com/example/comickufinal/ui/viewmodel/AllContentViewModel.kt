package com.example.comickufinal.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.comickufinal.repository.PagedRepository
import com.example.comickufinal.ui.home.AllContentActivity
import io.reactivex.rxjava3.disposables.CompositeDisposable

class AllContentViewModel(application: Application) : AndroidViewModel(application) {
    private val pagedRepository = PagedRepository.getInstance(application)
    private val compositeDisposable = CompositeDisposable()
    private val type: MutableLiveData<String> = MutableLiveData()
    private val genreEndpoint: MutableLiveData<String> = MutableLiveData()
    private val searchKeyword: MutableLiveData<String> = MutableLiveData()

    val networkState = pagedRepository.networkState

    val popularManga =
        pagedRepository.allPopularManga(compositeDisposable)

    val explore =
        pagedRepository.allExplore(compositeDisposable)

    val manga =
        pagedRepository.allManga(compositeDisposable)

    val manhua =
        pagedRepository.allManhua(compositeDisposable)

    val manhwa =
        pagedRepository.allManhwa(compositeDisposable)

    val genreManga = Transformations
        .switchMap(genreEndpoint) {
            pagedRepository.allGenreManga(it, compositeDisposable)
        }

    val searchManga = Transformations
        .switchMap(searchKeyword) {
            pagedRepository.searchManga(it, compositeDisposable)
        }

    fun isListEmpty(): Boolean {
        return when (type.value) {
            AllContentActivity.Type.ALL_POPULAR -> popularManga.value?.isEmpty() ?: true
            AllContentActivity.Type.ALL_EXPLORE -> explore.value?.isEmpty() ?: true
            AllContentActivity.Type.ALL_MANGA -> manga.value?.isEmpty() ?: true
            AllContentActivity.Type.ALL_MANHUA -> manhua.value?.isEmpty() ?: true
            AllContentActivity.Type.ALL_MANHWA -> manhwa.value?.isEmpty() ?: true
            else -> genreManga.value?.isEmpty() ?: true
        }
    }

    fun setType(value: String) {
        if (type.value == value) {
            return
        }
        type.value = value
    }

    fun setGenreEndpoint(value: String) {
        if (genreEndpoint.value == value) {
            return
        }
        genreEndpoint.value = value
    }

    fun setSearchKeyword(value: String) {
        if (searchKeyword.value == value) {
            return
        }
        searchKeyword.value = value
    }

    override fun onCleared() {
        super.onCleared()

        compositeDisposable.clear()
    }
}