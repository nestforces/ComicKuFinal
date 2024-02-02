package com.example.comickufinal.model

import com.google.gson.annotations.SerializedName

data class SearchMangaResponse(
        @SerializedName("manga_list")
        val data: List<SearchMangaResponseItem>
) {
    data class SearchMangaResponseItem(
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