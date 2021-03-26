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
import com.bumptech.glide.Glide
import com.davisilvaprojetos.themoviedb.R
import com.davisilvaprojetos.themoviedb.data.api.POSTER_BASE_URL
import com.davisilvaprojetos.themoviedb.data.api.TheMovieDBClient
import com.davisilvaprojetos.themoviedb.data.api.TheMovieDBInterface
import com.davisilvaprojetos.themoviedb.data.repository.NetworkState
import com.davisilvaprojetos.themoviedb.data.vo.MovieDetails
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MovieViewModel
    private lateinit var movieDetailsRepository: MovieDetailsRepository
    private var likeClick: Boolean = false
    private var likes: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val movieId : Int = 791373

        val apiService : TheMovieDBInterface = TheMovieDBClient.getClient()
        movieDetailsRepository = MovieDetailsRepository(apiService)

        viewModel = getViewModel(movieId)
        viewModel.movieDetails.observe(this, Observer {
            bindUI(it)
        })

        viewModel.networkState.observe(this, Observer {
            progressBar.visibility = if (it == NetworkState.LOADING) View.VISIBLE else View.GONE
            textError.visibility = if (it == NetworkState.ERROR) View.VISIBLE else View.GONE

        })

            imageButtonLike.setOnClickListener {
            if(!likeClick){
                imageButtonLike.setImageResource(R.drawable.ic_likes_white_24)
                likes+=1
                textVoteCount.text = "$likes Likes"
                likeClick = true
            }else{
                imageButtonLike.setImageResource(R.drawable.ic_unlike_gray_24)
                likes-=1
                textVoteCount.text = "$likes Likes"
                likeClick = false
            }

        }

    }

    fun bindUI( it: MovieDetails){
        textTitle.text = it.title
        likes = it.voteCount
        textVoteCount.text = "$likes Likes"
        textPopularity.text = it.popularity.toString() + " Views"


        val moviePosterURL = POSTER_BASE_URL + it.posterPath
        Glide.with(this)
                .load(moviePosterURL)
                .into(imagePoster)

    }


    private fun getViewModel(movieId:Int): MovieViewModel{
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T{
                @Suppress("UNCHECKED_CAST")
                return MovieViewModel(movieDetailsRepository, movieId) as T
            }
        })[MovieViewModel::class.java]
    }
}