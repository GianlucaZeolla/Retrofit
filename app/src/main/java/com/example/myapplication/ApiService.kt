package com.example.myapplication

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

interface APIService {
    @GET("breeds/list/all")
    suspend fun getListOfBreed(): Response<BreedsResponse>

    @GET("breed/{raza}/images")
    suspend fun getListaImagenes(@Path("raza") raza: String): Response<BreedReponse>
}