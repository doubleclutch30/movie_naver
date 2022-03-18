package com.lutawav.naver_movie.data.repository.local

import com.lutawav.naver_movie.data.db.dao.MovieDao
import com.lutawav.naver_movie.data.model.Movie
import io.reactivex.Completable
import io.reactivex.Single

class MovieLocalDataSourceImpl(private val movieDao: MovieDao) : MovieLocalDataSource {

    override fun insertMovie(movies: List<Movie>): Completable {
        TODO("Not yet implemented")
    }

    override fun getFavoriteMovie(): Single<List<Movie>> {
        TODO("Not yet implemented")
    }
}