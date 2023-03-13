package dz.yassir.movies

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import dz.yassir.movies.network.ApiResult
import dz.yassir.movies.network.PagingMoviesResponse
import dz.yassir.movies.network.RetrofitBuilder
import dz.yassir.movies.repository.MovieRepository
import dz.yassir.movies.viewmodel.MovieViewModel
import junit.framework.TestCase.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton
import java.util.*
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit


@RunWith(AndroidJUnit4::class)
class MoviesTest {
    @get:Rule
    var instantExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    private val kodein = Kodein {
        bind<Context>() with singleton { InstrumentationRegistry.getInstrumentation().targetContext }
        bind<MovieViewModel>() with provider { MovieViewModel(instance()) }
        bind<MovieRepository>() with provider { MovieRepository(RetrofitBuilder.theMovieDbService()) }
    }

    lateinit var movieViewModel: MovieViewModel

    private val instance by lazy { Instance(kodein) }

    @Before
    @Throws(Exception::class)
    fun setUp() {
        movieViewModel = instance.movieViewModel
    }


    @Test
    fun testGettingTrendingMovies() {
        var result: ApiResult<PagingMoviesResponse?> = ApiResult.Loading
        val latch = CountDownLatch(1)
        val observer = object : Observer<ApiResult<PagingMoviesResponse?>> {
            override fun onChanged(value: ApiResult<PagingMoviesResponse?>) {
                if (value !is ApiResult.Loading) {
                    result = value
                    latch.countDown()
                    movieViewModel.trendingMoviesViewState.removeObserver(this)
                }
            }
        }
        movieViewModel.trendingMoviesViewState.observeForever(observer)
        movieViewModel.getTrendingMovies()
        latch.await(5, TimeUnit.SECONDS)
        assertTrue(result is ApiResult.Success)
    }

}

class Instance(kodein: Kodein) {
    val movieViewModel: MovieViewModel by kodein.instance()
}

