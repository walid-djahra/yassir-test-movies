package dz.yassir.movies.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import dz.yassir.movies.R
import dz.yassir.movies.databinding.LoadStateAdapterBinding
import dz.yassir.movies.utils.StringUtils
import retrofit2.HttpException

/**
 * @author Djahra walid , created on 12/03/2023
 */
class HistoryLoadStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<HistoryLoadStateViewHolder>() {
    override fun onBindViewHolder(holder: HistoryLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): HistoryLoadStateViewHolder {
        return HistoryLoadStateViewHolder.create(parent, retry)
    }
}

class HistoryLoadStateViewHolder(
    private val binding: LoadStateAdapterBinding,
    retry: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {


    init {
        binding.retryButton.setOnClickListener { retry.invoke() }
    }

    fun bind(loadState: LoadState) {
        if (loadState is LoadState.Error) {
            when (loadState.error) {
                is HttpException -> {
                    binding.errorMsg.text = StringUtils.INTERNAL_SERVER_ERROR
                }
                else -> {
                    binding.errorMsg.text = binding.root.context.getText(R.string.connection_problem)
                }
            }
        }
        binding.progressBar.isVisible = loadState is LoadState.Loading
        binding.retryButton.isVisible = loadState !is LoadState.Loading
        binding.errorMsg.isVisible = loadState !is LoadState.Loading
    }

    companion object {
        fun create(parent: ViewGroup, retry: () -> Unit): HistoryLoadStateViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.load_state_adapter, parent, false)
            val binding = LoadStateAdapterBinding.bind(view)
            return HistoryLoadStateViewHolder(binding, retry)
        }
    }

}
