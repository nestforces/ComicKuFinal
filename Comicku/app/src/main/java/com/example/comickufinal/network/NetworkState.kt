package com.example.comickufinal.network

class NetworkState(val message: String) {
    companion object {
        val LOADING = NetworkState("")
        val LOADED = NetworkState("")
        val TIMEOUT = NetworkState("Slow connection")
        val NO_CONNECTION = NetworkState("No internet connection")
        val END_OF_PAGE = NetworkState("All data loaded")
        val ERROR = NetworkState("Something wrong, i can feel it")
    }
}