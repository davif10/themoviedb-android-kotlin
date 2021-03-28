package com.davisilvaprojetos.themoviedb.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.davisilvaprojetos.themoviedb.R
import com.davisilvaprojetos.themoviedb.data.api.POSTER_BASE_URL
import com.davisilvaprojetos.themoviedb.data.vo.Movies
import com.davisilvaprojetos.themoviedb.data.vo.SimilarMovies
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.movie_list_item.view.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class AdapterSimilarMovies(private val listMovies: List<Movies>, private val context: Context) : RecyclerView.Adapter<AdapterSimilarMovies.MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.movie_list_item, parent, false)
        return MovieViewHolder(item)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        //Config Title
        val movies = listMovies.get(position)
        holder.titleMovie.text = movies.title

        //Config Genres
        var genreDisplay = ""

        for (i in movies.genreIds.indices) {
            val genre = genres(movies.genreIds.get(i))
            if (i == movies.genreIds.indices.last) {
                genreDisplay += genre
            } else {
                genreDisplay += genre + ", "
            }
        }
        holder.genreMovie.text = genreDisplay

        //Config Year
        val date: String
        if(movies.releaseDate == null || movies.releaseDate.equals("")){
            date = "ND"
        }else{
            date = movies.releaseDate.subSequence(0, 4).toString()
        }

        holder.yearMovie.text = date

        //Config Image
        val moviePosterURL = POSTER_BASE_URL + movies.posterPath
        Glide.with(context)
                .load(moviePosterURL)
                .into(holder.imageMovie)

    }

    override fun getItemCount(): Int {
        return listMovies.size
    }

    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageMovie: ImageView
        var titleMovie: TextView
        var yearMovie: TextView
        var genreMovie: TextView

        init {
            imageMovie = itemView.imageSimilarMoviesItem
            titleMovie = itemView.textSimilarMoviesTitle
            yearMovie = itemView.textSimilarMoviesYear
            genreMovie = itemView.textSimilarMoviesGenre

        }
    }

    fun genres(id: Int): String {
        val name: String

        when (id) {
            12 -> name = "Adventure"
            14 -> name = "Fantasy"
            16 -> name = "Animation"
            18 -> name = "Drama"
            27 -> name = "Horror"
            28 -> name = "Action"
            35 -> name = "Comedy"
            36 -> name = "History"
            37 -> name = "Western"
            53 -> name = "Thriller"
            80 -> name = "Crime"
            99 -> name = "Documentary"
            878 -> name = "Science Fiction"
            9648 -> name = "Mystery"
            10402 -> name = "Music"
            10749 -> name = "Romance"
            10751 -> name = "Family"
            10752 -> name = "War"
            10770 -> name = "TV Movie"

            else -> name = ""
        }

        return name
    }

}