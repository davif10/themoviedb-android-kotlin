package com.davisilvaprojetos.themoviedb.data.vo


import com.google.gson.annotations.SerializedName

data class Movies(

    @SerializedName("genre_ids")
    val genreIds: List<Int>,
    val id: Int,
    val overview: String,
    val popularity: Double,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("release_date")
    val releaseDate: String,
    val title: String,
    @SerializedName("vote_average")
    val voteAverage: Double,
    @SerializedName("vote_count")
    val voteCount: Int
)