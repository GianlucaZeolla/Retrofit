package com.example.myapplication

import com.google.gson.annotations.SerializedName

class BreedReponse (
    @SerializedName("message") val imagenes: List<String>,
    @SerializedName("status") val status: String
)
