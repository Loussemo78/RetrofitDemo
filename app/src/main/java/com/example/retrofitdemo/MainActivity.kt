package com.example.retrofitdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.liveData
import com.example.retrofitdemo.databinding.ActivityMainBinding
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val retService = RetrofitInstance
            .getRetrofitInstance()
            .create(AlbumService::class.java)
        val responseLivedata:LiveData<Response<Albums>> = liveData {
            val response = retService.getAlbums()
            emit(response)
        }
        responseLivedata.observe(this, Observer{
            val albumsList = it.body()?.listIterator()
            if (albumsList != null){
                while (albumsList.hasNext()){
                    val albumsItem = albumsList.next()
                    val result = " "+"Album Title : ${albumsItem.title}"+"\n"+
                    " "+"Album id : ${albumsItem.title}"+"\n"+
                    " "+"User Id : ${albumsItem.userId}"+"\n\n\n"
                    binding.textView.append(result)
                }
            }
        })

    }
}