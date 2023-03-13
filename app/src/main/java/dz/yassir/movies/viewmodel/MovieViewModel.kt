package dz.yassir.movies.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dz.yassir.movies.entity.Movie
import dz.yassir.movies.enums.SectionType
import dz.yassir.movies.network.ApiResult
import dz.yassir.movies.network.PagingMoviesResponse
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

    val trendingMoviesViewState: LiveData<ApiResult<PagingMoviesResponse?>>
        get() = _trendingMoviesViewState

    private val _trendingMoviesViewState: MutableLiveData<ApiResult<PagingMoviesResponse?>> =
        MutableLiveData()

    fun getTrendingMovies() {
        safeLaunch(repository.getTrendingMovies()) {
            _trendingMoviesViewState.postValue(it)
        }
    }

    val topMoviesViewState: LiveData<ApiResult<PagingMoviesResponse?>>
        get() = _topMoviesViewState

    private val _topMoviesViewState: MutableLiveData<ApiResult<PagingMoviesResponse?>> =
        MutableLiveData()

    fun getTopMovies() {
        safeLaunch(repository.getTopMovies()) {
            _topMoviesViewState.postValue(it)
        }
    }

    val upcomingViewState: LiveData<ApiResult<PagingMoviesResponse?>>
        get() = _upcomingViewState

    private val _upcomingViewState: MutableLiveData<ApiResult<PagingMoviesResponse?>> =
        MutableLiveData()

    fun getUpcomingMovies() {
        safeLaunch(repository.getUpcomingMovies()) {
            _upcomingViewState.postValue(it)
        }
    }

    fun getMovies(sectionType: SectionType): Flow<PagingData<Movie>> {
        return repository.getSectionMovies(sectionType).cachedIn(viewModelScope)
    }

}