package com.example.myapplication

data class BreedsResponse (
    val message: Map<String, List<String>>,
    val status: String
)