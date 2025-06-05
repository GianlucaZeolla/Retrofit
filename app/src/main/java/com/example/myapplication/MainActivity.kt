package com.example.myapplication

import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.URL

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerview: RecyclerView
    private lateinit var adapter: AdapterDog

    private lateinit var searchView: SearchView

    private var listaImagenes = mutableListOf<String>()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerview = findViewById(R.id.recyclerviewlista)
        searchView = findViewById(R.id.searchviewrecycler)

        searchView.setOnQueryTextListener(this) //implementacion del metodo de arriba

        recyclerview.layoutManager = LinearLayoutManager(this)
        adapter = AdapterDog(listaImagenes)
        recyclerview.adapter = adapter

    }
    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(URL_DOGS)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun searchBy(breed: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(APIService::class.java).getListaImagenes("breed/$breed/images")
            val respuesta: RazasResponse? = call.body()

            runOnUiThread {
                if (call.isSuccessful) {
                    val imagenes = (respuesta?.imagenes ?: emptyList())
                    listaImagenes.clear()
                    listaImagenes.addAll(imagenes)
                    adapter.notifyDataSetChanged()
                } else {

                }
            }
        }
    }


    companion object //notacion que tiene kotlin para que algo sea estatico. Que puede ser accesible desde cualquier clase de mi aplicación, es muy costoso para la memoria, pero es rapido y comodo.
    const val URL_DOGS = "https://dog.ceo/api/hound"


}