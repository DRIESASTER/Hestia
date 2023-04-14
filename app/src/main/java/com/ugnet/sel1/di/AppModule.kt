package com.ugnet.sel1.di

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ugnet.sel1.data.repositories.AdresRepositoryImpl
import com.ugnet.sel1.data.repositories.IssuesRepositoryImpl
import com.ugnet.sel1.data.repositories.KamersRepositoryImpl
import com.ugnet.sel1.data.repositories.PandenRepositoryImpl
import com.ugnet.sel1.domain.repository.AdresRepository
import com.ugnet.sel1.domain.repository.IssuesRepository
import com.ugnet.sel1.domain.repository.KamersRepository
import com.ugnet.sel1.domain.repository.PandenRepository
import com.ugnet.sel1.domain.useCases.*
import com.ugnet.sel1.domain.useCases.Issues.AddIssue
import com.ugnet.sel1.domain.useCases.Issues.ChangeIssueStatus
import com.ugnet.sel1.domain.useCases.Issues.DeleteIssue
import com.ugnet.sel1.domain.useCases.Issues.GetIssue
import com.ugnet.sel1.domain.useCases.adressen.AddAdres
import com.ugnet.sel1.domain.useCases.adressen.DeleteAdres
import com.ugnet.sel1.domain.useCases.adressen.GetAdres
import com.ugnet.sel1.domain.useCases.adressen.GetAdreses
import com.ugnet.sel1.domain.useCases.kamers.AddKamer
import com.ugnet.sel1.domain.useCases.kamers.DeleteKamer
import com.ugnet.sel1.domain.useCases.kamers.EditKamer
import com.ugnet.sel1.domain.useCases.kamers.GetKamer
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
    fun provideKamersRef() = Firebase.firestore.collection("kamers")

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
    fun providesKamersRepository(
        kamersRef: CollectionReference
    ): KamersRepository = KamersRepositoryImpl(kamersRef)

    @Provides
    fun provideUseCases(
        adresRepo: AdresRepository,
        pandenRepo : PandenRepository,
        issuesRepo : IssuesRepository,
        kamersRepository: KamersRepository
    ) = UseCases(
        getAdreses = GetAdreses(adresRepo),
        getAdres = GetAdres(adresRepo),
        addAdres = AddAdres(adresRepo),
        deleteAdres = DeleteAdres(adresRepo),
        getOwnedPanden = GetOwnedPanden(pandenRepo),
        getPand = GetPand(pandenRepo),
        addIssue = AddIssue(issuesRepo),
        getIssue = GetIssue(issuesRepo),
        deleteIssue = DeleteIssue(issuesRepo),
        changeIssueStatus = ChangeIssueStatus(issuesRepo),
        getKamer = GetKamer(kamersRepository),
        addKamer = AddKamer(kamersRepository),
        deleteKamer = DeleteKamer(kamersRepository),
        editKamer = EditKamer(kamersRepository)
    )
}