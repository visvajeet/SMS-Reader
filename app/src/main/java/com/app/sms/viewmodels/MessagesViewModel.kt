package com.app.sms.viewmodels

import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.app.sms.data.MessageRepository
import com.app.sms.data.MessagesDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MessagesViewModel @Inject constructor(
    private val repository : MessageRepository
): ViewModel() {

    val messagesFLow = Pager(PagingConfig(pageSize = 50)) {
        MessagesDataSource(repository)
    }.flow

}