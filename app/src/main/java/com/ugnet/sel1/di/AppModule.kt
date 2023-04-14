package com.ugnet.sel1.di

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ugnet.sel1.data.repositories.AdresRepositoryImpl
import com.ugnet.sel1.domain.repository.AdresRepository
import com.ugnet.sel1.domain.useCases.GetAdreses
import com.ugnet.sel1.domain.useCases.UseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun provideAdresesRef() = Firebase.firestore.collection("adres")

    @Provides
    fun provideAdresesRepository(
        adresesRef: CollectionReference
    ): AdresRepository = AdresRepositoryImpl(adresesRef)

    @Provides
    fun provideUseCases(
        repo: AdresRepository
    ) = UseCases(
        getAdreses = GetAdreses(repo)
    )
}