package com.davisilvaprojetos.themoviedb.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.davisilvaprojetos.themoviedb.data.repository.NetworkState
import com.davisilvaprojetos.themoviedb.data.vo.SimilarMovies
import io.reactivex.disposables.CompositeDisposable

class SimilarMoviesViewModel(private val similarMoviesRepository: SimilarMoviesRepository, movieId:Int) : ViewModel(){
    private val compositeDisposable = CompositeDisposable()

    val similarMovies: LiveData<SimilarMovies> by lazy{
        similarMoviesRepository.fetchSimilarMovies(compositeDisposable, movieId)
    }

    val networkState: LiveData<NetworkState> by lazy{
        similarMoviesRepository.getSimilarMoviesNetworkState()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}