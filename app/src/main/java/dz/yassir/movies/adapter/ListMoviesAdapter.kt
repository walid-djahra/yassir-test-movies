package dz.yassir.movies.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.*
import dz.yassir.movies.R
import dz.yassir.movies.callback.Interaction
import dz.yassir.movies.databinding.MovieItemBinding
import dz.yassir.movies.entity.Movie
import dz.yassir.movies.utils.loadImage

/**
 * @author Djahra walid , created on 12/03/2023
 */
class ListMoviesAdapter(
    private val interaction: Interaction<Movie>? = null
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var currentList: List<Movie> = arrayListOf()

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<Movie>?) {
        list?.let {
            currentList = list
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return ListMovieItemViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.movie_item,
                parent,
                false

            ),
            interaction
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ListMovieItemViewHolder) {
            holder.bind(currentList[position])
        }
    }

    override fun getItemCount(): Int = currentList.size

    inner class ListMovieItemViewHolder constructor(
        view: View,
        private val interaction: Interaction<Movie>?

    ) : RecyclerView.ViewHolder(view) {

        @SuppressLint("SetTextI18n")
        val binding  = MovieItemBinding.bind(view)
        fun bind(item: Movie) = with(itemView) {
            binding.title.text = item.title
            binding.ratingBar.rating = (item.voteAverage/2).toFloat()
            loadImage(item.posterPath,binding.movieImage,binding.loadingBar)
            binding.root.setOnClickListener {
                interaction?.onInteraction(item, absoluteAdapterPosition)
            }
        }
    }


}