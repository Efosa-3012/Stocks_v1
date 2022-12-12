package com.example.flixster

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers
import org.json.JSONException

private const val NOW_PLAYING_URL= "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY_ADJUSTED&symbol="
private const val keyExt = "&apikey=AQ7WK7XKT1VDOMR3"

val senders = listOf("AAPL","AMZN","META","SHOP","PINS",)
private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    private val movies = mutableListOf<Movie>()
    private lateinit var MoviesRV: RecyclerView
    private var movieAdapter: MovieAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        MoviesRV = findViewById(R.id.MoviesRV)

        movieAdapter = MovieAdapter(this, movies)
        MoviesRV.adapter = movieAdapter
        MoviesRV.layoutManager = LinearLayoutManager(this) //puts movies top to bottom
        fetchStocks()
    }

    fun fetchStocks(index: Int = 0) {
        Log.i(TAG, "index: $index")
        if (index < senders.count()) {
            val client = AsyncHttpClient()
            val stockUrl = NOW_PLAYING_URL + senders[index] + keyExt
            client.get(stockUrl, object : JsonHttpResponseHandler(){
                override fun onFailure(statusCode: Int, headers: Headers?, response: String?, throwable: Throwable?) {
                    Log.e(TAG, "onFailure $statusCode")
                }

                @RequiresApi(Build.VERSION_CODES.O)
                override fun onSuccess(statusCode: Int, headers: Headers?, json: JSON) {
                    Log.i(TAG, "onSuccess: JSON data $json \n")
                    try {
                        val movieJsonArray = json.jsonObject.getJSONObject("Time Series (Daily)")
                        movies.addAll(Movie.fromJsonArray(senders.get(index), movieJsonArray))

                        Log.i(TAG, "Movie list $movies")
                        fetchStocks(index + 1)
                    }
                    catch (e: JSONException){
                        Log.e(TAG, "Encountered error $e")
                    }
                }

            })
        } else {
            Log.i(TAG, " ${movies.count()}")
            movieAdapter?.notifyDataSetChanged() //update movies
        }

    }
}