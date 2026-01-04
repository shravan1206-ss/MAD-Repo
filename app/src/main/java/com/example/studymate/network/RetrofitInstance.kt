package com.example.studymate.network


import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://sourav-express-quote-api.onrender.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: QuoteApi by lazy {
        retrofit.create(QuoteApi::class.java)
    }
}
