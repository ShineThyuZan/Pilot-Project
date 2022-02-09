package com.galaxy_techno.uKnow.presentation.ui.top_lvl_dest.orders.tabs.all

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.galaxy_techno.uKnow.di.Qualifier
import com.galaxy_techno.uKnow.domain.UserRepository
import com.galaxy_techno.uKnow.movie.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AllOrdersViewModel @Inject constructor(
    private val userRepository: UserRepository,
    @Qualifier.Io private val io: CoroutineDispatcher
    ) : ViewModel() {

    private val _movies = MutableLiveData<PagingData<Movie>>()
    val movies: LiveData<PagingData<Movie>> get() = _movies

    init {
        getPagerMovies()
    }
    private fun getPagerMovies() {

        viewModelScope.launch(io) {
            val data = userRepository.getPagingMovies()
            data.collect {
                _movies.postValue(it)
            }
        }
    }
}