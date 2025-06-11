package com.example.myapplication

import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
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


    private lateinit var spinner: Spinner

    private var listaImagenes = mutableListOf<String>()

    private var breedsList = mutableListOf<String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerview = findViewById(R.id.recyclerviewlista)

        spinner = findViewById(R.id.spinner)


        recyclerview.layoutManager = LinearLayoutManager(this)
        adapter = AdapterDog(listaImagenes)
        recyclerview.adapter = adapter


    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://dog.ceo/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun getListOfBreed(breeds: String) {

        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(APIService::class.java).getListOfBreed("breed/list/all")
            val response = call.body()

            runOnUiThread {
                if (call.isSuccessful) {
                    val breedsMap = response?.message
                    if (breedsMap != null) {
                        for (breed in breedsMap.keys)
                            breedsList.add(breed)
                        setSpinner()
                    }

                } else {


                }
            }
        }
    }

    private fun getImagesBy(breed: String?) {
        CoroutineScope(Dispatchers.IO).launch {
            val call =
                getRetrofit().create(APIService::class.java).getListaImagenes("breed/$breed/images")
            val response = call.body()

            runOnUiThread {
                if (call.isSuccessful) {
                    val images = response?.imagenes ?: emptyList()
                    listaImagenes.clear()
                    listaImagenes.addAll(images)
                    adapter.notifyDataSetChanged()
                } else {

                }
            }
        }
    }


    private fun setSpinner() {
        val spinnerAdapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, breedsList)
        spinner.adapter = spinnerAdapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                getListOfBreed(listaImagenes[p2])
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
    }
}





    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://dog.ceo/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
