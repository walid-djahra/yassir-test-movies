package dz.yassir.movies.ui

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import dz.yassir.movies.R
import dz.yassir.movies.adapter.ListMoviesAdapter
import dz.yassir.movies.callback.Interaction
import dz.yassir.movies.databinding.FragmentMainMoviesBinding
import dz.yassir.movies.entity.Movie
import dz.yassir.movies.enums.SectionType
import dz.yassir.movies.network.ApiResult
import dz.yassir.movies.viewmodel.MovieViewModel
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

/**
 * @author Djahra walid , created on 13/03/2023
 */
class MainMoviesFragment :
    BaseFragment<FragmentMainMoviesBinding>(FragmentMainMoviesBinding::inflate),
    KodeinAware, Interaction<Movie> {
    override val kodein by closestKodein()
    private val movieViewModel: MovieViewModel by instance()
    private val topMoviesAdapter = ListMoviesAdapter(this)
    private val trendingMoviesAdapter = ListMoviesAdapter(this)
    private val upcomingMoviesAdapter = ListMoviesAdapter(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapters()
        setupObservers()
        movieViewModel.getTopMovies()
        movieViewModel.getTrendingMovies()
        movieViewModel.getUpcomingMovies()
        binding.seeAllTop.setOnClickListener {
            navigateToAllMovies(SectionType.TOP)
        }

        binding.seeAllUpcoming.setOnClickListener {
            navigateToAllMovies(SectionType.POPULAR)
        }

        binding.seeAllTrending.setOnClickListener {
            navigateToAllMovies(SectionType.TRENDING)
        }
    }

    private fun navigateToAllMovies(sectionType: SectionType) {
        val bundle = Bundle()
        bundle.putString("section_type", sectionType.name)
        findNavController().navigate(R.id.action_mainMoviesFragment_to_allMoviesFragment,bundle)
    }

    private fun setupObservers() {
        movieViewModel.topMoviesViewState.observe(viewLifecycleOwner) { result ->
            binding.topMoviesProgress.isVisible = result is ApiResult.Loading
            binding.topMoviesConnectionError.isVisible =
                result is ApiResult.GenericError || result is ApiResult.NetworkError
            if (result is ApiResult.Success) {
                topMoviesAdapter.submitList(result.value?.results)
            }
        }

        movieViewModel.trendingMoviesViewState.observe(viewLifecycleOwner) { result ->
            binding.trendingMoviesProgress.isVisible = result is ApiResult.Loading
            binding.trendingMoviesConnectionError.isVisible =
                result is ApiResult.GenericError || result is ApiResult.NetworkError
            if (result is ApiResult.Success) {
                trendingMoviesAdapter.submitList(result.value?.results)
            }
        }

        movieViewModel.upcomingViewState.observe(viewLifecycleOwner) { result ->
            binding.upcomingMoviesProgress.isVisible = result is ApiResult.Loading
            binding.upcomingMoviesConnectionError.isVisible =
                result is ApiResult.GenericError || result is ApiResult.NetworkError
            if (result is ApiResult.Success) {
                upcomingMoviesAdapter.submitList(result.value?.results)
            }
        }
    }

    private fun setupAdapters() {
        binding.topMoviesRec.adapter = topMoviesAdapter
        binding.upcomingMoviesRec.adapter = upcomingMoviesAdapter
        binding.trendingMoviesRec.adapter = trendingMoviesAdapter
    }

    override fun onInteraction(item: Movie, position: Int) {
        val bundle = Bundle()
        bundle.putLong("movie_id", item.id)
        findNavController().navigate(R.id.action_mainMoviesFragment_to_movieDetailFragment,bundle)
    }

}