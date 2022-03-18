package com.lutawav.naver_movie.data.repository.remote

import com.lutawav.naver_movie.data.model.MovieResponse
import io.reactivex.Single

interface MovieRemoteDataSource {
    fun getSearchMovies(
        query: String,
        start: Int = 1
    ): Single<MovieResponse>
}