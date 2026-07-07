package com.gustavovs.mvconfeitariacatalogapp.data.remote

import com.gustavovs.mvconfeitariacatalogapp.data.model.Cake
import retrofit2.Response
import retrofit2.http.*

interface CakeApi {

    @GET("api/v1/catalog")
    suspend fun getCakes(): Response<List<Cake>>

    @GET("api/v1/catalog/cake/{title}")
    suspend fun findCakesByTitle(@Path("title") title: String): Response<List<Cake>>

    @POST("api/v1/catalog/cake")
    suspend fun createCake(@Body cake: Cake): Response<Cake>

    @PUT("api/v1/catalog/cake/{id}")
    suspend fun updateCake(@Path("id") id: Long, @Body cake: Cake): Response<Cake>

    @DELETE("api/v1/catalog/cake/{id}")
    suspend fun deleteCake(@Path("id") id: Long): Response<Void>
}
