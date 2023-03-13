package dz.yassir.movies.repository

import androidx.paging.PagingData
import dz.yassir.movies.entity.Movie
import dz.yassir.movies.enums.SectionType
import dz.yassir.movies.network.ApiResult
import dz.yassir.movies.network.PagingMoviesResponse
import dz.yassir.movies.network.TheMovieDbService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * @author Djahra walid , created on 12/03/2023
 */
class MovieRepository(private val service: TheMovieDbService) : BaseRepository() {

    fun getMovieById(movieId: Long): Flow<ApiResult<Movie?>> = flow {
        emit(ApiResult.Loading)
        val response = safeApiCall {
            service.getMovieById(movieId)
        }
        emit(response)
    }

    fun getTrendingMovies(): Flow<ApiResult<PagingMoviesResponse?>> = flow {
        emit(ApiResult.Loading)
        val response = safeApiCall {
            service.getTrendingMovies(1)
        }
        emit(response)
    }

    fun getTopMovies(): Flow<ApiResult<PagingMoviesResponse?>> = flow {
        emit(ApiResult.Loading)
        val response = safeApiCall {
            service.getTopMovies(1)
        }
        emit(response)
    }

    fun getUpcomingMovies(): Flow<ApiResult<PagingMoviesResponse?>> = flow {
        emit(ApiResult.Loading)
        val response = safeApiCall {
            service.getUpcomingMovies(1)
        }
        emit(response)
    }

    fun getSectionMovies(sectionType: SectionType): Flow<PagingData<Movie>> {
        return when (sectionType) {
            SectionType.TOP -> {
                getPagedTopMovies()
            }
            SectionType.TRENDING -> {
                getPagedTrendingMovies()
            }
            else -> {
                getPagedUpcomingMovies()
            }
        }
    }

    private fun getPagedTrendingMovies(): Flow<PagingData<Movie>> {
        return getRemoteData { page ->
            service.getTrendingMovies(page).results
        }
    }

    private fun getPagedTopMovies(): Flow<PagingData<Movie>> {
        return getRemoteData { page ->
            service.getTopMovies(page).results
        }
    }

    private fun getPagedUpcomingMovies(): Flow<PagingData<Movie>> {
        return getRemoteData { page ->
            service.getUpcomingMovies(page).results
        }
    }

}