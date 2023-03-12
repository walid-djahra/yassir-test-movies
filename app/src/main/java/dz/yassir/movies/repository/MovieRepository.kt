package dz.yassir.movies.repository

import androidx.paging.PagingData
import dz.yassir.movies.entity.Movie
import dz.yassir.movies.network.ApiResult
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

    fun getTrendingMovies(): Flow<PagingData<Movie>> {
        return getRemoteData { page ->
            service.getTrendingMovies(page).results
        }
    }


}