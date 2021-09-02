package com.app.sms.di

import android.content.Context
import com.app.sms.data.MessageRepository
import com.app.sms.data.MessagesDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent


@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @Provides
    fun provideRepository(
        @ApplicationContext context: Context
    ): MessageRepository {
        return MessageRepository(context)
    }

    @Provides
    fun provideDataSource(
        repository: MessageRepository
    ): MessagesDataSource {
        return MessagesDataSource(repository)
    }

}