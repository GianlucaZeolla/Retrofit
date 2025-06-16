package com.example.myapplication

import android.app.ProgressDialog.show
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

        getListOfBreed()



    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://dog.ceo/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun getListOfBreed() {
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(APIService::class.java).getListOfBreed()
            val response = call.body()

            runOnUiThread {
                if (call.isSuccessful && response != null) {
                    val breedsMap = response.message
                    breedsList.clear()
                    breedsList.addAll(breedsMap.keys)
                    setSpinner()
                }
            }
        }
    }

    private fun getImagesBy(breed: String?) {
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(APIService::class.java).getListaImagenes(breed ?: "")

            val response = call.body()

            runOnUiThread {
                if (call.isSuccessful && response != null) {
                    listaImagenes.clear()
                    listaImagenes.addAll(response.imagenes)
                    adapter.notifyDataSetChanged()
                }
            }
        }
    }


    private fun setSpinner() {
        val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, breedsList)
        spinner.adapter = spinnerAdapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                getImagesBy(breedsList[p2])
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
    }
}




