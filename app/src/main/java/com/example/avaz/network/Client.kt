package com.example.avaz.network

import com.example.avaz.models.ApiData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Client {

    @GET("{id}/icons")
    fun getResponse(@Path("id") id: String?,@Query("limit") limit: Int?,@Query("offset") offset: Int?,@Query("page") page: Int?): Call<ApiData?>?
}