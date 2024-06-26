package com.potatomadness.network

import com.potatomadness.network.model.DrinksResponse
import com.potatomadness.network.model.FilterListResponse
import com.potatomadness.network.model.IngredientsResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface CocktailService {
    @GET("list.php")
    suspend fun getFilterList(@QueryMap(encoded = true) filter: Map<String, String>): FilterListResponse

    @GET("filter.php")
    suspend fun getFilteredDrinks(@QueryMap(encoded = true) filter: Map<String, String>): DrinksResponse

    @GET("search.php")
    suspend fun searchDrinksByAlpha(@Query("f") alpha: String): DrinksResponse

    @GET("lookup.php")
    suspend fun getDrinkRecipe(@Query("i") id: Int): DrinksResponse

    @GET("search.php")
    suspend fun searchIngredientInfo(@Query("i") alpha: String): IngredientsResponse


    companion object {
        private const val BASE_URL = "https://www.thecocktaildb.com/api/json/v1/1/"

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
