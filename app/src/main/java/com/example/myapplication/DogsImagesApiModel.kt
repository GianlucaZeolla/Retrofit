package com.example.myapplication

import com.google.gson.annotations.SerializedName


class RazasResponse (
    @SerializedName("message") var imagenes: List<String>,
    @SerializedName("status") var status: String
)
