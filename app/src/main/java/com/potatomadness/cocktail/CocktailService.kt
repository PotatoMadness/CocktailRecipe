package com.potatomadness.cocktail

import com.potatomadness.cocktail.data.DrinksResponse
import com.potatomadness.cocktail.data.FilterListResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface CocktailService {
    @GET("/list.php")
    suspend fun getFilterList(@QueryMap(encoded = true) filter: Map<String, String>): FilterListResponse

    @GET("/filter.php")
    suspend fun getFilteredDrinks(@QueryMap(encoded = true) filter: Map<String, String>): DrinksResponse


    companion object {
        private const val BASE_URL = "www.thecocktaildb.com/api/json/v1/1"

        fun create(): CocktailService {
            val logger = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(CocktailService::class.java)
        }
    }
}
