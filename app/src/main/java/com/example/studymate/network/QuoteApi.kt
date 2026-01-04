package com.example.studymate.network

import com.example.studymate.network.model.QuoteResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface QuoteApi {

    @GET("quotes")
    suspend fun getQuotes(
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): QuoteResponse
}
