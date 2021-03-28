package com.davisilvaprojetos.themoviedb.data.api

import com.davisilvaprojetos.themoviedb.data.vo.MovieDetails
import com.davisilvaprojetos.themoviedb.data.vo.SimilarMovies
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TheMovieDBInterface {

    // https://api.themoviedb.org/3/
    // https://api.themoviedb.org/3/movie/791373?api_key=d01edbcc404ca3bffc4b3199b0b83403
    // https://api.themoviedb.org/3/movie/popular?api_key=6e63c2317fbe963d76c3bdc2b785f6d1&page=1
    //https://api.themoviedb.org/3/movie/791373/similar?api_key=d01edbcc404ca3bffc4b3199b0b83403&page=1


    @GET("movie/{movie_id}")
    fun getMovieDetails(@Path("movie_id") id: Int): Single<MovieDetails>

    @GET("movie/{movie_id}/similar")
    fun getSimilarMovies(@Path("movie_id") id: Int): Single<SimilarMovies>
}