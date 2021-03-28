package com.davisilvaprojetos.themoviedb.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.davisilvaprojetos.themoviedb.data.api.FIRST_PAGE
import com.davisilvaprojetos.themoviedb.data.api.MOVIE_ID
import com.davisilvaprojetos.themoviedb.data.api.TheMovieDBInterface
import com.davisilvaprojetos.themoviedb.data.vo.MovieDetails
import com.davisilvaprojetos.themoviedb.data.vo.Movies
import com.davisilvaprojetos.themoviedb.data.vo.SimilarMovies
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.lang.Exception

class MovieDataSource(
        private val apiService: TheMovieDBInterface,
        private val compositeDisposable: CompositeDisposable) {

    private var page = FIRST_PAGE
    private var id = MOVIE_ID
    val networkState: MutableLiveData<NetworkState> = MutableLiveData()
    val ns: LiveData<NetworkState>
    get() = networkState

    private val dowloadedSimilarMoviesResponse = MutableLiveData<SimilarMovies>()
    val dowloadedMoviesResponse: LiveData<SimilarMovies>
        get() = dowloadedSimilarMoviesResponse

    fun fetchSimilarMovies(movieId: Int) {
        networkState.postValue(NetworkState.LOADING)
        try {
            compositeDisposable.add(
                    apiService.getSimilarMovies(movieId)
                            .subscribeOn(Schedulers.io())
                            .subscribe(
                                    {
                                    dowloadedSimilarMoviesResponse.postValue(it)
                                        networkState.postValue(NetworkState.LOADED)

                                    },
                                    {
                                        networkState.postValue(NetworkState.ERROR)
                                        println("MovieDataSource: "+it.message)
                                    }
                            )
            )

        }catch (e: Exception){
            println("MovieDataSource: "+e.message)
        }
    }
}

