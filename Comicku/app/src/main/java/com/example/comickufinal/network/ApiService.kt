package com.example.comickufinal.network

import com.example.comickufinal.model.*
import io.reactivex.rxjava3.core.Flowable
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("recommended")
    fun getRecom(): Flowable<RecommendedResponse>

    @GET("manga/popular/{page}")
    fun getPopularManga(
        @Path("page") page: Int
    ): Flowable<PopularMangaResponse>

    @GET("manga/page/{page}")
    fun getExplore(
        @Path("page") page: Int
    ): Flowable<ExploreResponse>

    @GET("genres")
    fun getGenre(): Flowable<GenreResponse>

    @GET("manga/page/{page}")
    fun getManga(
        @Path("page") page: Int
    ): Flowable<MangaResponse>

    @GET("manhua/{page}")
    fun getManhua(
        @Path("page") page: Int
    ): Flowable<MangaResponse>

    @GET("manhwa/{page}")
    fun getManhwa(
        @Path("page") page: Int
    ): Flowable<MangaResponse>

    @GET("genres/{endpoint}/{page}")
    fun getGenreManga(
        @Path("endpoint") endpoint: String,
        @Path("page") page: Int
    ): Flowable<GenreMangaResponse>

    @GET("cari/{keyword}")
    fun getSearchManga(
        @Path("keyword") keyword: String
    ): Flowable<SearchMangaResponse>

    @GET("manga/detail/{endpoint}")
    fun getDetailManga(
        @Path("endpoint") endpoint: String
    ): Flowable<DetailMangaResponse>

    @GET("chapter/{endpoint}")
    fun getChapterImage(
        @Path("endpoint") endpoint: String
    ): Flowable<ChapterMangaResponse>
}