package dz.yassir.movies.network

import com.google.gson.annotations.SerializedName
import dz.yassir.movies.entity.Movie

/**
 * @author Djahra walid , created on 12/03/2023
 */

data class PagingMoviesResponse(
    val page: Int = 1,
    val results: List<Movie>,
    @SerializedName("total_pages")
    val totalPages: Int = 0,
    @SerializedName("total_results")
    val totalResults: Int = 0
)