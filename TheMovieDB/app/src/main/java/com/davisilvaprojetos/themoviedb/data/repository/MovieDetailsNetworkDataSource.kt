package com.davisilvaprojetos.themoviedb.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.davisilvaprojetos.themoviedb.data.api.TheMovieDBInterface
import com.davisilvaprojetos.themoviedb.data.vo.MovieDetails
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.lang.Exception

class MovieDetailsNetworkDataSource(
        private val apiService: TheMovieDBInterface,
        private val compositeDisposable: CompositeDisposable) {

    private val networkState = MutableLiveData<NetworkState>()
    val ns: LiveData<NetworkState>
    get() = networkState

    private val downloadedMovieDetailsResponse = MutableLiveData<MovieDetails>()
    val downloadedMovieResponse:LiveData<MovieDetails>
    get() = downloadedMovieDetailsResponse

    fun fetchMovieDetails(movieId: Int){
        networkState.postValue(NetworkState.LOADING)

        try{
            compositeDisposable.add(
                    apiService.getMovieDetails(movieId)
                            .subscribeOn(Schedulers.io())
                            .subscribe(
                                    {
                                        downloadedMovieDetailsResponse.postValue(it)
                                        networkState.postValue(NetworkState.LOADED)
                                    },
                                    {
                                        networkState.postValue(NetworkState.ERROR)
                                        println("MovieDetailsDataSource: "+it.message)
                                    }

                            )

            )


        }catch (e: Exception){
            println("MovieDetailsDataSource: "+e.message)
        }
    }
}