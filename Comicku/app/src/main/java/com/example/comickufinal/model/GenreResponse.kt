package com.example.comickufinal.model

import com.google.gson.annotations.SerializedName

data class GenreResponse(
    @SerializedName("list_genre")
    val data: List<Genre>
) {
    data class Genre(
        @SerializedName("genre_name")
        val genre_name: String
    )
}