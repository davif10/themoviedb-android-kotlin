package com.davisilvaprojetos.themoviedb.data.vo


import com.google.gson.annotations.SerializedName

data class SimilarMovies(
        val page: Int,
        val results: List<Movies>,
        @SerializedName("total_pages")
    val totalPages: Int,
        @SerializedName("total_results")
    val totalResults: Int
)