package dz.yassir.movies.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dz.yassir.movies.network.ApiResult
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

/**
 * @author Djahra walid , created on 12/03/2023
 */
open class BaseViewModel : ViewModel() {

    fun <Response> ViewModel.safeLaunch(
        call: Flow<ApiResult<Response?>>,
        postValue: (data: ApiResult<Response?>) -> Unit
    ) {
        viewModelScope.launch {
            call.collect { data ->
                postValue.invoke(data)
                if (data !is ApiResult.Loading) {
                    cancel()
                }
            }
        }
    }
}