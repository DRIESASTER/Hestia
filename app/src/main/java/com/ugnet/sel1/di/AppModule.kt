package com.ugnet.sel1.di

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ugnet.sel1.data.repositories.AdresRepositoryImpl
import com.ugnet.sel1.data.repositories.IssuesRepositoryImpl
import com.ugnet.sel1.data.repositories.PandenRepositoryImpl
import com.ugnet.sel1.domain.repository.AdresRepository
import com.ugnet.sel1.domain.repository.IssuesRepository
import com.ugnet.sel1.domain.repository.PandenRepository
import com.ugnet.sel1.domain.useCases.*
import com.ugnet.sel1.domain.useCases.Issues.AddIssue
import com.ugnet.sel1.domain.useCases.Issues.DeleteIssue
import com.ugnet.sel1.domain.useCases.Issues.GetIssue
import com.ugnet.sel1.domain.useCases.adressen.AddAdres
import com.ugnet.sel1.domain.useCases.adressen.DeleteAdres
import com.ugnet.sel1.domain.useCases.adressen.GetAdreses
import com.ugnet.sel1.domain.useCases.panden.GetOwnedPanden
import com.ugnet.sel1.domain.useCases.panden.GetPand
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun provideAdresesRef() = Firebase.firestore.collection("adres")
    fun providePandenRef() = Firebase.firestore.collection("panden")
    fun provideIssuesRef() = Firebase.firestore.collection("issues")

    @Provides
    fun provideAdresesRepository(
        adresesRef: CollectionReference
    ): AdresRepository = AdresRepositoryImpl(adresesRef)

    @Provides
    fun providePandenRepository(
        pandenRef: CollectionReference
    ): PandenRepository = PandenRepositoryImpl(pandenRef)

    @Provides
    fun provideIssuesRepository(
        issuesRef: CollectionReference
    ): IssuesRepository = IssuesRepositoryImpl(issuesRef)

    @Provides
    fun provideUseCases(
        adresRepo: AdresRepository,
        pandenRepo : PandenRepository,
        issuesRepo : IssuesRepository
    ) = UseCases(
        getAdreses = GetAdreses(adresRepo),
        addAdres = AddAdres(adresRepo),
        deleteAdres = DeleteAdres(adresRepo),
        getOwnedPanden = GetOwnedPanden(pandenRepo),
        getPand = GetPand(pandenRepo),
        addIssue = AddIssue(issuesRepo),
        getIssue = GetIssue(issuesRepo),
        deleteIssue = DeleteIssue(issuesRepo)

    )

}