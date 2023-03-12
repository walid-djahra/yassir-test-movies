package dz.yassir.movies.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import dz.yassir.movies.network.ApiResult
import dz.yassir.movies.utils.StringUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import retrofit2.HttpException

/**
 * @author Djahra walid , created on 12/03/2023
 */
open class BaseRepository {
    /**
     * safeApiCall to connect to api and handle success and all error possibles in one place
     */
    suspend fun <T> safeApiCall(apiCall: suspend () -> T): ApiResult<T?> {
        return withContext(Dispatchers.IO) {
            try {
                ApiResult.Success(apiCall.invoke())
            } catch (e: Exception) {
                when (e) {
                    is HttpException -> {
                        ApiResult.GenericError(e.code(), StringUtils.INTERNAL_SERVER_ERROR)
                    }
                    else -> {
                        ApiResult.NetworkError
                    }
                }
            }
        }
    }

    /**
     * Remote pagination
     */
    fun <HistoryModel : Any> getRemoteData(
        apiCall: suspend (page: Int) -> List<HistoryModel>
    ): Flow<PagingData<HistoryModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = StringUtils.PAGINATION_PAGE_SIZE,
                enablePlaceholders = false,
                initialLoadSize = StringUtils.PAGINATION_PAGE_SIZE
            ),
            pagingSourceFactory = {
                RemotePagingSource(apiCall = apiCall)
            }
        ).flow
    }

}