package dz.yassir.movies.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.viewbinding.ViewBinding
import dz.yassir.movies.R
import dz.yassir.movies.network.ApiResult

/**
 * @author Djahra walid , created on 13/03/2023
 */
abstract class BaseFragment<VB : ViewBinding>(
    private val inflate: (LayoutInflater, ViewGroup?, Boolean) -> VB
) : Fragment() {

    lateinit var binding: VB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = inflate.invoke(inflater, container, false)
        return binding.root
    }

    fun handlePagingError(loadState: CombinedLoadStates) {
        val errorState = loadState.source.append as? LoadState.Error
            ?: loadState.source.prepend as? LoadState.Error

        errorState?.let {
            Toast.makeText(
                requireContext(),
                getString(R.string.connection_problem),
                Toast.LENGTH_LONG
            ).show()
        }
    }

    fun <T> handleApiError(result: ApiResult<T?>) {
        when (result) {
            is ApiResult.GenericError -> {
                toast(result.errorMessage)
            }
            else -> {
                Toast.makeText(requireContext(), "network error", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun toast(text: String?) {
        Toast.makeText(
            requireContext(),
            text,
            Toast.LENGTH_SHORT
        ).show()
    }

}