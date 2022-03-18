package com.lutawav.naver_movie.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lutawav.naver_movie.BaseActivity
import com.lutawav.naver_movie.R
import com.lutawav.naver_movie.databinding.ActivityMainBinding
import com.lutawav.naver_movie.ui.WebViewActivity.Companion.EXTRA_URL
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private val viewModel: MovieViewModel by viewModel()
    lateinit var movieAdapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        movieAdapter = MovieAdapter { movie ->
            Intent(this, WebViewActivity::class.java).apply {
                putExtra(EXTRA_URL, movie.link)
            }.run {
                startActivity(this)
            }
        }

        binding.recyclerview.adapter = movieAdapter

        initViews()
//        binding.vm = viewModel

        initViewModelCallback()

//        viewModel.fetchMovie()
    }

    private fun initViews() {
        binding.recyclerview.apply {
            binding.vm = viewModel
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = movieAdapter
            itemAnimator = DefaultItemAnimator()
            addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
        }
    }

    private fun initViewModelCallback() {
        with(viewModel) {
            toastMsg.observe(this@MainActivity, Observer {
                when (toastMsg.value) {
                    MovieViewModel.MessageSet.EMPTY_QUERY -> showToast(getString(R.string.search_input_query_msg))
                    MovieViewModel.MessageSet.NETWORK_ERROR -> showToast(getString(R.string.network_error_msg))
                    MovieViewModel.MessageSet.SUCCESS -> showToast(getString(R.string.load_movie_success_msg))
                    MovieViewModel.MessageSet.NO_RESULT -> showToast(getString(R.string.no_movie_error_msg))
                }
            })
        }
    }
}