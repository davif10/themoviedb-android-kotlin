package com.davisilvaprojetos.themoviedb.ui

import androidx.lifecycle.LiveData
import com.davisilvaprojetos.themoviedb.data.api.TheMovieDBInterface
import com.davisilvaprojetos.themoviedb.data.repository.MovieDetailsNetworkDataSource
import com.davisilvaprojetos.themoviedb.data.repository.NetworkState
import com.davisilvaprojetos.themoviedb.data.vo.MovieDetails
import io.reactivex.disposables.CompositeDisposable

class MovieDetailsRepository(private val apiService: TheMovieDBInterface) {
    lateinit var movieDetailsNetworkDataSource: MovieDetailsNetworkDataSource

    fun fetchSingleMovieDetails(compositeDisposable: CompositeDisposable, movieId: Int):LiveData<MovieDetails>{
        movieDetailsNetworkDataSource = MovieDetailsNetworkDataSource(apiService, compositeDisposable)
        movieDetailsNetworkDataSource.fetchMovieDetails(movieId)

        return  movieDetailsNetworkDataSource.downloadedMovieResponse
    }

    fun getMovieDetailsNetworkState():LiveData<NetworkState>{
        return  movieDetailsNetworkDataSource.ns
    }
}