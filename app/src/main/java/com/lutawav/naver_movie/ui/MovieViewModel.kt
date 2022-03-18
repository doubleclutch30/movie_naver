package com.lutawav.naver_movie.ui

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.CheckBox
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lutawav.naver_movie.data.model.Movie
import com.lutawav.naver_movie.data.repository.MovieRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class MovieViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    private var currentQuery: String = ""
    val query = MutableLiveData<String>()

    private val _movieList = MutableLiveData<List<Movie>>()
    private val _toastMsg = MutableLiveData<MessageSet>()

    val movieList: LiveData<List<Movie>> get() = _movieList
    val toastMsg: LiveData<MessageSet> get() = _toastMsg

    fun fetchMovie() {
        currentQuery = query.toString().trim()

        if (currentQuery.isEmpty()) {
            _toastMsg.value = MessageSet.EMPTY_QUERY
        }
        else {
            compositeDisposable.add(
                movieRepository.getSearchMovie(currentQuery)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ movie ->
                        if (movie.isEmpty()) {
                            _toastMsg.value = MessageSet.NO_RESULT
                        } else {
                            _movieList.value = movie
                            Log.d("_movieList", _movieList.value.toString())
                            _toastMsg.value = MessageSet.SUCCESS
                        }
                    }, {
                        _toastMsg.value = MessageSet.NETWORK_ERROR
                    })
            )
        }
    }

    protected val compositeDisposable = CompositeDisposable()

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }

    enum class MessageSet {
        EMPTY_QUERY,
        NETWORK_ERROR,
        SUCCESS,
        NO_RESULT
    }
}