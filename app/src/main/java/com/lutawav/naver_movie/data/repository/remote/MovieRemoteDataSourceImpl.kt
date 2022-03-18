package com.lutawav.naver_movie.data.repository.remote

import com.lutawav.naver_movie.data.model.MovieResponse
import com.lutawav.naver_movie.data.api.ApiInterface
import io.reactivex.Single

class MovieRemoteDataSourceImpl(private val apiInterface: ApiInterface) : MovieRemoteDataSource {
    override fun getSearchMovies(
        query: String, start: Int
    ): Single<MovieResponse> = apiInterface.getSearchMovie(query, start)
}