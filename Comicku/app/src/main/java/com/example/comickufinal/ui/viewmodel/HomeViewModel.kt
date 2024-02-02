package com.example.comickufinal.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.comickufinal.network.NetworkState
import com.example.comickufinal.repository.HomeRepository
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import io.reactivex.rxjava3.disposables.CompositeDisposable

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private val compositeDisposable = CompositeDisposable()
    private val homeRepository = HomeRepository.getInstance(application)
    val firebaseAuth = Firebase.auth
    val networkState: LiveData<NetworkState> = homeRepository.networkState

    val popularManga = homeRepository.getPopularManga(1, compositeDisposable)

    val explore = homeRepository.getExplore(1, compositeDisposable)

    val genre = homeRepository.getGenre(compositeDisposable)

    val favoriteManga = homeRepository.getFavoriteManga(firebaseAuth.currentUser!!)

    val historyManga = homeRepository.getHistoryManga(firebaseAuth.currentUser!!)

    val recommended = homeRepository.getRecommended(compositeDisposable)

    val reset = onCleared()


    override fun onCleared() {
        super.onCleared()

        val recommended = homeRepository.getRecommended(compositeDisposable).value?.isEmpty()
        val explore = homeRepository.getExplore(1, compositeDisposable).value?.isEmpty()
        compositeDisposable.clear()
    }
}