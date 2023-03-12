package dz.yassir.movies

import android.app.Application
import dz.yassir.movies.network.RetrofitBuilder
import dz.yassir.movies.repository.MovieRepository
import dz.yassir.movies.viewmodel.MovieViewModel
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider

/**
 * @author Djahra walid , created on 12/03/2023
 */
class MoviesApplication : Application(), KodeinAware {
    override val kodein: Kodein by Kodein.lazy {
        import(androidXModule(this@MoviesApplication))


        bind<MovieRepository>() with provider {
            MovieRepository(
                service = RetrofitBuilder.theMovieDbService()
            )
        }

        bind<MovieViewModel>() with provider { MovieViewModel(instance()) }

    }

}