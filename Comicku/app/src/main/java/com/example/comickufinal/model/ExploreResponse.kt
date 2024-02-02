package com.example.comickufinal.model

import com.google.gson.annotations.SerializedName

data class ExploreResponse(
    @SerializedName("manga_list")
    val data: List<Manga>
) {
    data class Manga(
        @SerializedName("chapter")
        val chapter: String,
        @SerializedName("endpoint")
        val endpoint: String,
        @SerializedName("thumb")
        val thumb: String,
        @SerializedName("title")
        val title: String,
        @SerializedName("type")
        val type: String,
        @SerializedName("updated_on")
        val updatedOn: String
    )
}