package dz.yassir.movies.network

import dz.yassir.movies.entity.Movie
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


/**
 * @author Djahra walid , created on 12/03/2023
 * <br>
 * retrofit service for connect to API
 */
interface TheMovieDbService {
    @GET("/3/movie/{movieId}")
    suspend fun getMovieById(@Path("movieId") movieId: Long): Movie

    @GET("/3/discover/movie")
    suspend fun getTrendingMovies(@Query("page") page: Int): PagingMoviesResponse

    @GET("/3/movie/top_rated")
    suspend fun getTopMovies(@Query("page") page: Int): PagingMoviesResponse

    @GET("/3/movie/upcoming")
    suspend fun getUpcomingMovies(@Query("page") page: Int): PagingMoviesResponse

}