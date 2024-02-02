package com.example.comickufinal.model

import com.google.gson.annotations.SerializedName

data class PopularMangaResponse(
    @SerializedName("manga_list")
    val data: List<Manga>
) {
    data class Manga(
        @SerializedName("title")
        val title: String,
        @SerializedName("type")
        val type: String,
        @SerializedName("thumb")
        val thumb: String,
        @SerializedName("endpoint")
        val endpoint: String,
        @SerializedName("upload_on")
        val uploadOn: String
    )
}