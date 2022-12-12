package com.example.flixster

import android.os.Build
import android.os.Parcelable
import androidx.annotation.RequiresApi
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import org.json.JSONArray
import org.json.JSONObject

import java.time.LocalDateTime
import  java.time.format.DateTimeFormatter

@Parcelize

data class Movie (
    val name: String,
    val movieId: String,
    val voteAverage: String,
    private val posterPath: String,
    val title: String,
        ) : Parcelable
{
    @IgnoredOnParcel
    val posterImageURL = "https://image.tmdb.org/t/p/w342/$posterPath"
    companion object {
        @RequiresApi(Build.VERSION_CODES.O)
        fun fromJsonArray(stock: String, movieJsonArray: JSONObject): List<Movie> {
            val currDate = "2022-12-07" //getCurrDate()
            val movies = mutableListOf<Movie>()
            val movieJson = movieJsonArray.getJSONObject(currDate)
            movies.add(
                Movie(
                    stock,
                    movieJson.getString("2. high"),
                    movieJson.getString("3. low"),
                    movieJson.getString("6. volume") ,
                    movieJson.getString("4. close")
                )
            )
            return movies
        }
    }
}

//@RequiresApi(Build.VERSION_CODES.O)
//fun getCurrDate(): String? {
//    val curr = LocalDateTime.now()
//    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
//    return curr.format(formatter)
//}
