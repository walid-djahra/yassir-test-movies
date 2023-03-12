package dz.yassir.movies.entity

import com.google.gson.annotations.SerializedName


/**
 * @author Djahra walid , created on 12/03/2023
 */

data class Movie(
    @SerializedName("id") var id: Long = 0,
    @SerializedName("backdrop_path") var backdropPath: String? = null,
    @SerializedName("overview") var overview: String? = null,
    @SerializedName("poster_path") var posterPath: String? = null,
    @SerializedName("release_date") var releaseDate: String? = null,
    @SerializedName("status") var status: String? = null,
    @SerializedName("title") var title: String? = null,
    @SerializedName("vote_average") var voteAverage: Double = 0.0,
    @SerializedName("vote_count") var voteCount: Int = 0
)