package com.example.flixster

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import jp.wasabeef.glide.transformations.RoundedCornersTransformation

private const val TAG = "MovieAdapter"
const val MOVIE_EXTRA = "MOVIE_EXTRA"
class MovieAdapter(private val context : Context, private val movies: List<Movie>) :
    RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    // Expensive operation: create a view
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.i(TAG, "onCreateViewHolder")
        val view = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false)
        return ViewHolder(view)
    }

    // Cheap: simply bind data to an existing viewholder
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.i(TAG, "onBindViewHolder position $position")
        val movie = movies[position]
        holder.bind(movie)
    }

    override fun getItemCount() = movies.size

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView), View.OnClickListener{
        private val Poster = itemView.findViewById<TextView>(R.id.Poster)
        private val Title = itemView.findViewById<TextView>(R.id.Title)
        private val Description = itemView.findViewById<TextView>(R.id.Description)

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(movie: Movie){
            Title.text = movie.title
            Description.text = movie.voteAverage
            Poster.text = movie.name
        }

        override fun onClick(p0: View?) {

        }
    }
}