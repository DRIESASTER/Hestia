package com.ugnet.sel1.ui.manager.properties

import androidx.lifecycle.SavedStateHandle
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Qualifier

// bron: https://dev.to/lex_fury/parameter-injection-for-android-viewmodels-cjl
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class IssueId

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class PropId

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Email

@Module
@InstallIn(ViewModelComponent::class)
object PropDetailModule {
    @Provides
    @IssueId
    @ViewModelScoped
    fun provideIssueId(savedStateHandle: SavedStateHandle): String =
        savedStateHandle.get<String>("issueId")?.toString()
            ?: throw IllegalArgumentException("You have to provide issueId as parameter with type String when navigating to comments")

    @Provides
    @PropId
    @ViewModelScoped
    fun providePropId(savedStateHandle: SavedStateHandle): String =
        savedStateHandle.get<String>("propId")?.toString()
            ?: throw IllegalArgumentException("You have to provide propId as parameter with type String when navigating to comments")


    @Provides
    @Email
    @ViewModelScoped
    fun provideEmail(savedStateHandle: SavedStateHandle): String =
        savedStateHandle.get<String>("email")?.toString()
            ?: throw IllegalArgumentException("You have to provide email as parameter with type String when navigating to comments")

}