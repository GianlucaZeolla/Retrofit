package com.example.myapplication

import com.google.gson.annotations.SerializedName

class BreedReponse (
    @SerializedName("message") var imagenes: List<String>,
    @SerializedName("status") var status: String
)
