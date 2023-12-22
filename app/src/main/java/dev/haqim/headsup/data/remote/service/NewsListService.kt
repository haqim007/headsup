package dev.haqim.headsup.data.remote.service

import dev.haqim.headsup.data.remote.response.NewsListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsListService {
    @GET("top-headlines")
    suspend fun getTopHeadlines(
        @Query("q") query: String?,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int,
        @Query("category") category:String,
        @Query("language") language:String?
    ): NewsListResponse
}