package com.davisilvaprojetos.themoviedb.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.davisilvaprojetos.themoviedb.R
import com.davisilvaprojetos.themoviedb.data.api.POSTER_BASE_URL
import com.davisilvaprojetos.themoviedb.data.api.TheMovieDBClient
import com.davisilvaprojetos.themoviedb.data.api.TheMovieDBInterface
import com.davisilvaprojetos.themoviedb.data.repository.NetworkState
import com.davisilvaprojetos.themoviedb.data.vo.MovieDetails
import com.davisilvaprojetos.themoviedb.data.vo.Movies
import com.davisilvaprojetos.themoviedb.data.vo.SimilarMovies
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MovieViewModel
    private lateinit var movieDetailsRepository: MovieDetailsRepository
    private lateinit var movieSimilarMoviesRepository: SimilarMoviesRepository
    private lateinit var viewModelSimilarMovies: SimilarMoviesViewModel
    private var likeClick: Boolean = false
    private var likes: Int = 0
    private val listSimilarMovies: MutableList<SimilarMovies> = ArrayList()
    private var listMovies: List<Movies> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val movieId: Int = 791373

        val apiService: TheMovieDBInterface = TheMovieDBClient.getClient()
        movieDetailsRepository = MovieDetailsRepository(apiService)

        movieSimilarMoviesRepository = SimilarMoviesRepository(apiService)


        viewModel = getViewModel(movieId)
        viewModel.movieDetails.observe(this, Observer {
            bindUI(it)
        })

        viewModel.networkState.observe(this, Observer {
            progressBar.visibility = if (it == NetworkState.LOADING) View.VISIBLE else View.GONE
            textError.visibility = if (it == NetworkState.ERROR) View.VISIBLE else View.GONE

        })


        viewModelSimilarMovies = getViewModelSimilarMovies(movieId)
        viewModelSimilarMovies.similarMovies.observe(this, Observer {
            listSimilarMovies.add(it)
            listMovies = it.results
            val adapterSimilarMovies = AdapterSimilarMovies(listMovies, this)
            recyclerSimilarMovies.setHasFixedSize(true)
            recyclerSimilarMovies.layoutManager = LinearLayoutManager(this)
            recyclerSimilarMovies.adapter = adapterSimilarMovies


        })

        viewModelSimilarMovies.networkState.observe(this, Observer {
            progressBar.visibility = if (it == NetworkState.LOADING) View.VISIBLE else View.GONE
            textError.visibility = if (it == NetworkState.ERROR) View.VISIBLE else View.GONE

        })

        imageButtonLike.setOnClickListener {
            if (!likeClick) {
                imageButtonLike.setImageResource(R.drawable.ic_likes_white_24)
                likes += 1
                textVoteCount.text = "$likes Likes"
                likeClick = true
            } else {
                imageButtonLike.setImageResource(R.drawable.ic_unlike_gray_24)
                likes -= 1
                textVoteCount.text = "$likes Likes"
                likeClick = false
            }

        }

    }

    fun bindUI(it: MovieDetails) {
        textTitle.text = it.title
        likes = it.voteCount
        textVoteCount.text = "$likes Likes"
        textPopularity.text = it.popularity.toString() + " Views"


        val moviePosterURL = POSTER_BASE_URL + it.posterPath
        Glide.with(this)
                .load(moviePosterURL)
                .into(imagePoster)

    }


    private fun getViewModel(movieId: Int): MovieViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return MovieViewModel(movieDetailsRepository, movieId) as T
            }
        })[MovieViewModel::class.java]
    }

    private fun getViewModelSimilarMovies(movieId: Int): SimilarMoviesViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return SimilarMoviesViewModel(movieSimilarMoviesRepository, movieId) as T
            }
        })[SimilarMoviesViewModel::class.java]
    }
}