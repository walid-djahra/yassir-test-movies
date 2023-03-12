package dz.yassir.movies.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dz.yassir.movies.entity.Movie
import dz.yassir.movies.network.ApiResult
import dz.yassir.movies.repository.MovieRepository
import kotlinx.coroutines.flow.Flow

/**
 * @author Djahra walid , created on 12/03/2023
 */
class MovieViewModel(private val repository: MovieRepository) : BaseViewModel() {
    val movieDetailViewState: LiveData<ApiResult<Movie?>>
        get() = _movieDetailView

    private val _movieDetailView: MutableLiveData<ApiResult<Movie?>> = MutableLiveData()
    fun getMovieById(movieId: Long) {
        safeLaunch(repository.getMovieById(movieId = movieId)) {
            _movieDetailView.postValue(it)
        }
    }

    fun getSectionMovies(): Flow<PagingData<Movie>> {
        return repository.getTrendingMovies().cachedIn(viewModelScope)
    }

}