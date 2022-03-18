package com.lutawav.naver_movie.data.repository.local

import com.lutawav.naver_movie.data.model.Movie
import io.reactivex.Completable
import io.reactivex.Single

interface MovieLocalDataSource {
    fun insertMovie(movies: List<Movie>) : Completable
    fun getFavoriteMovie(): Single<List<Movie>>
}
