package com.lutawav.naver_movie.data.repository

import com.lutawav.naver_movie.data.model.Movie
import com.lutawav.naver_movie.data.repository.remote.MovieRemoteDataSource
import io.reactivex.Single

class MovieRepositoryImpl(
    private val movieRemoteDataSource: MovieRemoteDataSource,
) : MovieRepository {
    override fun getSearchMovie(
        query: String
    ): Single<List<Movie>> =
        movieRemoteDataSource.getSearchMovies(query)
            .flatMap {
                Single.just(it.movies)
            }
}