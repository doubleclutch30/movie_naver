package com.lutawav.naver_movie.data.repository

import com.lutawav.naver_movie.data.model.Movie
import io.reactivex.Single

interface MovieRepository {
    fun getSearchMovie(
        query: String,
    ): Single<List<Movie>>
}