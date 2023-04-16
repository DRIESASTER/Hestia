package com.ugnet.sel1.authentication.selection

import com.google.firebase.auth.FirebaseAuth
import com.ugnet.sel1.data.repositories.AuthRepositoryImpl
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

import dagger.Binds
import dagger.Provides
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository

    companion object {
        @Provides
        @Singleton
        fun provideFirebaseAuth(): FirebaseAuth {
            return FirebaseAuth.getInstance()
        }
    }
}

