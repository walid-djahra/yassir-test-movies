package dz.yassir.movies.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState

/**
 * @author Djahra walid , created on 12/03/2023
 */
class RemotePagingSource<HistoryModel : Any>(
    private val apiCall: suspend (page: Int) -> List<HistoryModel>
) : PagingSource<Int, HistoryModel>() {
    //movie db start from page = 1
    private val startingPageIndex = 1

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, HistoryModel> {
        val pageIndex = params.key ?: startingPageIndex
        return try {
            //call to Api
            val response = apiCall.invoke(pageIndex)
            //save prevKey and nexKey for next load
            val prevKey = if (pageIndex == startingPageIndex) null else pageIndex
            val nextKey = if (response.isEmpty()) null else pageIndex + 1
            LoadResult.Page(
                data = response,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, HistoryModel>): Int? {
        //To get the current page
        return try {
            state.anchorPosition?.let { anchorPosition ->
                state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                    ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
            }
        } catch (e: Exception) {
            null
        }
    }

    override val keyReuseSupported: Boolean
        get() = true
}