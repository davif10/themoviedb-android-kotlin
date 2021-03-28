package com.davisilvaprojetos.themoviedb.ui

import androidx.lifecycle.LiveData
import com.davisilvaprojetos.themoviedb.data.api.TheMovieDBInterface
import com.davisilvaprojetos.themoviedb.data.repository.MovieDataSource
import com.davisilvaprojetos.themoviedb.data.repository.NetworkState
import com.davisilvaprojetos.themoviedb.data.vo.SimilarMovies
import io.reactivex.disposables.CompositeDisposable

class SimilarMoviesRepository(private val apiService: TheMovieDBInterface) {
    lateinit var movieDataSource: MovieDataSource

    fun fetchSimilarMovies(compositeDisposable: CompositeDisposable, movieId: Int):LiveData<SimilarMovies>{
        movieDataSource = MovieDataSource(apiService, compositeDisposable)
        movieDataSource.fetchSimilarMovies(movieId)

        return movieDataSource.dowloadedMoviesResponse
    }

    fun getSimilarMoviesNetworkState(): LiveData<NetworkState>{
        return  movieDataSource.ns
    }


}