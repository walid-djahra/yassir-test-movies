package dz.yassir.movies.ui

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import dz.yassir.movies.R
import dz.yassir.movies.adapter.HistoryLoadStateAdapter
import dz.yassir.movies.adapter.PagedMoviesAdapter
import dz.yassir.movies.callback.Interaction
import dz.yassir.movies.databinding.FragmentAllMoviesBinding
import dz.yassir.movies.entity.Movie
import dz.yassir.movies.enums.SectionType
import dz.yassir.movies.viewmodel.MovieViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

/**
 * @author Djahra walid , created on 13/03/2023
 */
class AllMoviesFragment : BaseFragment<FragmentAllMoviesBinding>(FragmentAllMoviesBinding::inflate),
    KodeinAware, Interaction<Movie> {
    override val kodein by closestKodein()
    private val movieViewModel: MovieViewModel by instance()
    private lateinit var sectionType: SectionType
    private val moviesAdapter: PagedMoviesAdapter by lazy {
        PagedMoviesAdapter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            sectionType =
                SectionType.valueOf(it.getString("section_type", SectionType.TRENDING.name))
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupTitle()
        binding.moviesRec.adapter = moviesAdapter.withLoadStateFooter(
            footer = HistoryLoadStateAdapter {
                moviesAdapter.retry()
            }
        )
        binding.refreshLayout.setOnRefreshListener {
            moviesAdapter.refresh()
        }
        binding.retry.setOnClickListener {
            moviesAdapter.refresh()
        }
        binding.back.setOnClickListener {
            findNavController().navigateUp()
        }
        setupObservers()
    }

    private fun setupTitle() {
        when (sectionType) {
            SectionType.TOP -> {
                binding.title.text = getString(R.string.top_movies)
            }
            SectionType.TRENDING -> {
                binding.title.text = getString(R.string.trending)
            }
            else -> {
                binding.title.text = getString(R.string.upcoming_movies)
            }
        }
    }

    private fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            movieViewModel.getMovies(sectionType).collectLatest {
                moviesAdapter.submitData(it)
            }
        }
        moviesAdapter.addLoadStateListener { loadState ->
            val refreshState = loadState.source.refresh
            binding.refreshLayout.isRefreshing = refreshState is LoadState.Loading
            binding.connectionProblem.isVisible = refreshState is LoadState.Error
            handlePagingError(loadState)
        }
    }

    override fun onInteraction(item: Movie, position: Int) {
        val bundle = Bundle()
        bundle.putLong("movie_id", item.id)
        findNavController().navigate(R.id.action_allMoviesFragment_to_movieDetailFragment, bundle)
    }

}