package com.davisilvaprojetos.themoviedb.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.davisilvaprojetos.themoviedb.data.repository.NetworkState
import com.davisilvaprojetos.themoviedb.data.vo.MovieDetails
import io.reactivex.disposables.CompositeDisposable

class MovieViewModel(private val movieDetailsRepository: MovieDetailsRepository, movieId: Int) : ViewModel(){

    private val compositeDisposable = CompositeDisposable()

    val movieDetails: LiveData<MovieDetails> by lazy{
        movieDetailsRepository.fetchSingleMovieDetails(compositeDisposable, movieId)
    }

    val networkState: LiveData<NetworkState> by lazy{
        movieDetailsRepository.getMovieDetailsNetworkState()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}