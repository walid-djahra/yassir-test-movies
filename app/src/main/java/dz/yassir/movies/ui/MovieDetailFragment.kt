package dz.yassir.movies.ui

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import dz.yassir.movies.databinding.FragmentMovieDetailBinding
import dz.yassir.movies.entity.Movie
import dz.yassir.movies.network.ApiResult
import dz.yassir.movies.utils.StringUtils
import dz.yassir.movies.utils.loadImage
import dz.yassir.movies.viewmodel.MovieViewModel
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance


/**
 * @author Djahra walid , created on 13/03/2023
 */
class MovieDetailFragment : BaseFragment<FragmentMovieDetailBinding>(FragmentMovieDetailBinding::inflate),
    KodeinAware {
    override val kodein by closestKodein()
    private val movieViewModel: MovieViewModel by instance()
    private var movieId: Long = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            movieId = it.getLong("movie_id")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObserver()
        movieViewModel.getMovieById(movieId)
        binding.back.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.retry.setOnClickListener{
            movieViewModel.getMovieById(movieId)
        }
    }

    private fun setupObserver() {

        /**
         * add listener to movie details for showing in fragment
         **/
        movieViewModel.movieDetailViewState.observe(viewLifecycleOwner) {
            binding.progressLinear.isVisible = it !is ApiResult.Success
            showProgressBar(it is ApiResult.Loading)
            if (it is ApiResult.Success) {
                bindData(it.value)
            }
        }
    }

    private fun showProgressBar(isLoading: Boolean) {
        binding.progressView.isVisible = isLoading
        binding.connectionProblem.isVisible = !isLoading
        binding.retry.isVisible = !isLoading
    }

    private fun bindData(it: Movie?) {
        it?.let {
            binding.movieTitle.text = it.title
            binding.movieSubtitle.text = it.status
            binding.movieReleaseAt.text = it.releaseDate
            binding.movieDescription.text = it.overview
            binding.ratingBar.rating = (it.voteAverage/2).toFloat()
            binding.ratingTitle.text = voteText(it)
            loadImage(it.backdropPath, binding.imageBackground,binding.loadingBar)
        }
    }

    private fun voteText(it: Movie): String{
        return "${StringUtils.decimalFormat.format((it.voteAverage/2).toFloat())}(${it.voteCount})"
    }


}