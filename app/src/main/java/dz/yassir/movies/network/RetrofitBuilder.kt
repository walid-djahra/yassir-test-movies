package dz.yassir.movies.network


import dz.yassir.movies.utils.StringUtils.Companion.API_KEY
import dz.yassir.movies.utils.StringUtils.Companion.BASE_URL
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @author Djahra walid , created on 12/03/2023
 */
object RetrofitBuilder {
    /**
     * create static instance from retrofit service  with timeout 5 sec
     * and interceptor to send token in every request
     */
    private var service: TheMovieDbService? = null
    fun theMovieDbService(): TheMovieDbService {
        return service ?: buildService().also { service = it }
    }

    private fun buildService(): TheMovieDbService {
        val interceptor = Interceptor { chain ->

            var newRequest = chain.request()
            val url = newRequest.url
                .newBuilder()
                .addQueryParameter("api_key", API_KEY)
                .build()
            newRequest = newRequest.newBuilder().url(url).build()
            chain.proceed(newRequest)
        }

        val httpClient = OkHttpClient.Builder()
            .callTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
            .addInterceptor(interceptor)

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TheMovieDbService::class.java)
    }

}