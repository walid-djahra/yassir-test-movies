package dz.yassir.movies.network


/**
 * @author Djahra walid , created on 12/03/2023
 */

open class ApiResult<out T> {
    /**
     * An generic class for handle all responses possible: loading, success, server error and network error
     */

    object Loading : ApiResult<Nothing>()

    data class Success<out T>(val value: T) : ApiResult<T>()

    data class GenericError(
        val code: Int? = null,
        val errorMessage: String? = null
    ) : ApiResult<Nothing>()

    object NetworkError : ApiResult<Nothing>()

}