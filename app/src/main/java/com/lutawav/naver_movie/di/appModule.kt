package com.lutawav.naver_movie.di

import android.util.Log
import androidx.room.Room
import com.lutawav.naver_movie.data.api.ApiClient
import com.lutawav.naver_movie.data.api.ApiInterface
import com.lutawav.naver_movie.data.db.MovieDatabase
import com.lutawav.naver_movie.data.db.dao.MovieDao
import com.lutawav.naver_movie.data.repository.MovieRepository
import com.lutawav.naver_movie.data.repository.MovieRepositoryImpl
import com.lutawav.naver_movie.data.repository.local.MovieLocalDataSource
import com.lutawav.naver_movie.data.repository.local.MovieLocalDataSourceImpl
import com.lutawav.naver_movie.data.repository.remote.MovieRemoteDataSource
import com.lutawav.naver_movie.data.repository.remote.MovieRemoteDataSourceImpl
import com.lutawav.naver_movie.ui.MovieViewModel
import com.lutawav.naver_movie.util.peekBody
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {

    single { MovieViewModel(get()) }
    single<MovieRemoteDataSource> { MovieRemoteDataSourceImpl(get()) }
    single<MovieRepository> { MovieRepositoryImpl(get()) }

    single<MovieLocalDataSource> { MovieLocalDataSourceImpl(get()) }
    single<MovieDao> { get<MovieDatabase>().movieDao() }
    single<MovieDatabase> {
        Room.databaseBuilder(
            get(),
            MovieDatabase::class.java, "Movie.db"
        )
            .build()
    }

    single<ApiInterface> { get<Retrofit>().create(ApiInterface::class.java) }

    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(ApiClient.BASE_URL)
            .client(get())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(get<GsonConverterFactory>())
            .build()
    }

    single<GsonConverterFactory> { GsonConverterFactory.create() }

    single<OkHttpClient> {
        OkHttpClient.Builder()
            .run {
                addInterceptor(get<Interceptor>())
                build()
            }
    }

    single<Interceptor> {
        Interceptor { chain ->
            with(chain) {
                val newRequest = request().newBuilder()
                    .addHeader("X-Naver-Client-Id", "tr6M7jBKez2OeO2BOXSg")
                    .addHeader("X-Naver-Client-Secret", "S_DUMEv030")
                    .build()

                val response = chain.proceed(newRequest)
                val statusCode = response.code
                Log.i("ApiClient", "statusCode=${statusCode}")

                if (statusCode == 200) {
                    Log.i("ApiClient", "res=${response}, body=${response.peekBody()}")
                }
                proceed(newRequest)
            }
        }
    }
}