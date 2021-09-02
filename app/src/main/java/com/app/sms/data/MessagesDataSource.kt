package com.app.sms.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import javax.inject.Inject


class MessagesDataSource @Inject constructor(
    private val repo: MessageRepository
) : PagingSource<Int, DataItem>() {

    var initialLoadSize: Int = 0

    override fun getRefreshKey(state: PagingState<Int, DataItem>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DataItem> {

        try {

            val nextPage = params.key ?: 1

            if (params.key == null) initialLoadSize = params.loadSize

            val offset = if (nextPage == 2) initialLoadSize else
                ((nextPage - 1) * params.loadSize) + (initialLoadSize - params.loadSize)


            val messages = repo.getMessages(params.loadSize, offset)

            val nextKey = if (messages.size < params.loadSize) null else nextPage + 1

            return LoadResult.Page(
                data = messages,
                prevKey = null,
                nextKey = nextKey
            )

        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }


}