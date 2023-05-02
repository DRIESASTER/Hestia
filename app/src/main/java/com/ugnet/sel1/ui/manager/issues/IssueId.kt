package com.ugnet.sel1.ui.manager.issues

import androidx.lifecycle.SavedStateHandle
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class IssueId

@Module
@InstallIn(ViewModelComponent::class)
object IssueDetailModule {
    @Provides
    @IssueId
    @ViewModelScoped
    fun provideIssueId(savedStateHandle: SavedStateHandle): String =
        savedStateHandle.get<String>("issueId")?.toString()
            ?: throw IllegalArgumentException("You have to provide postId as parameter with type String when navigating to comments")
}