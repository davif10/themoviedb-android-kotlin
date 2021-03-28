package com.davisilvaprojetos.themoviedb.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.davisilvaprojetos.themoviedb.R
import com.davisilvaprojetos.themoviedb.data.vo.Movies
import com.davisilvaprojetos.themoviedb.data.vo.SimilarMovies
import kotlinx.android.synthetic.main.movie_list_item.view.*

class AdapterSimilarMovies(private val listMovies: List<Movies>, private val context: Context) : RecyclerView.Adapter<AdapterSimilarMovies.MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.movie_list_item, parent, false)
        return MovieViewHolder(item)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movies = listMovies.get(position)
        holder.titleMovie.text = movies.title
        holder.yearMovie.text = movies.releaseDate
        holder.genreMovie.text = movies.genreIds.toString()

        println("TAMANHO DA LISTA: "+listMovies.size)
        println("MOVIES 1 : "+movies)
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

}