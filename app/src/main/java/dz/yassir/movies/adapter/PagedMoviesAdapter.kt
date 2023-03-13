package dz.yassir.movies.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import dz.yassir.movies.R
import dz.yassir.movies.callback.Interaction
import dz.yassir.movies.databinding.MovieItemBinding
import dz.yassir.movies.entity.Movie
import dz.yassir.movies.utils.loadImage

/**
 * @author Djahra walid , created on 12/03/2023
 */
class PagedMoviesAdapter(
    private val interaction: Interaction<Movie>? = null
) : PagingDataAdapter<Movie, RecyclerView.ViewHolder>(DiffCallback()) {

    class DiffCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(
            oldItem: Movie,
            newItem: Movie
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: Movie,
            newItem: Movie
        ): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return MovieItemViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.movie_item,
                parent,
                false
            ),
            interaction = interaction
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MovieItemViewHolder -> {
                holder.bind(getItem(position))
            }
        }
    }

    inner class MovieItemViewHolder constructor(
        view: View,
        private val interaction: Interaction<Movie>?

    ) : RecyclerView.ViewHolder(view) {

        @SuppressLint("SetTextI18n")
        val binding = MovieItemBinding.bind(view)
        fun bind(item: Movie?) {
            item?.let {
                binding.title.text = item.title
                binding.ratingBar.rating = (item.voteAverage / 2).toFloat()
                loadImage(item.backdropPath, binding.movieImage, binding.loadingBar)
                binding.root.setOnClickListener {
                    interaction?.onInteraction(item, absoluteAdapterPosition)
                }
            }
        }
    }

}