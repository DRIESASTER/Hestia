package com.ugnet.sel1.di

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ugnet.sel1.data.repositories.*
import com.ugnet.sel1.domain.repository.*
import com.ugnet.sel1.domain.useCases.*
import com.ugnet.sel1.domain.useCases.Issues.AddIssue
import com.ugnet.sel1.domain.useCases.Issues.ChangeIssueStatus
import com.ugnet.sel1.domain.useCases.Issues.DeleteIssue
import com.ugnet.sel1.domain.useCases.Issues.GetIssues
import com.ugnet.sel1.domain.useCases.adressen.AddAdres
import com.ugnet.sel1.domain.useCases.adressen.DeleteAdres
import com.ugnet.sel1.domain.useCases.adressen.GetAdres
import com.ugnet.sel1.domain.useCases.adressen.GetAdreses
import com.ugnet.sel1.domain.useCases.kamers.*
import com.ugnet.sel1.domain.useCases.manager.GetManager
import com.ugnet.sel1.domain.useCases.panden.GetOwnedPanden
import com.ugnet.sel1.domain.useCases.panden.GetPand
import com.ugnet.sel1.domain.useCases.panden.GetPanden
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideManagerRef() = Firebase.firestore

//    fun provideAdresesRef() = Firebase.firestore.collection("adres")
//    fun providePandenRef() = Firebase.firestore.collection("panden")
//    fun provideIssuesRef() = Firebase.firestore.collection("issues")
//    fun provideKamersRef() = Firebase.firestore.collection("kamers")



    @Provides
    fun provideAdresesRepository(
        adresesRef: FirebaseFirestore
    ): AdresRepository = AdresRepositoryImpl(adresesRef)

    @Provides
    fun providePandenRepository(
        pandenRef: FirebaseFirestore
    ): PandenRepository = PandenRepositoryImpl(pandenRef)

    @Provides
    fun provideIssuesRepository(
        issuesRef: FirebaseFirestore
    ): IssuesRepository = IssuesRepositoryImpl(issuesRef)

    @Provides
    fun providesKamersRepository(
        kamersRef: FirebaseFirestore
    ): KamersRepository = KamersRepositoryImpl(kamersRef)

    @Provides
    fun provideManagerRepository(
        managerRef: FirebaseFirestore
    ): ManagerRepository = ManagerRepositoryImpl(managerRef)

    @Provides
    fun provideUseCases(
        adresRepo: AdresRepository,
        pandenRepo : PandenRepository,
        issuesRepo : IssuesRepository,
        kamersRepository: KamersRepository,
        managerRepository: ManagerRepository
    ) = UseCases(
        getAdreses = GetAdreses(adresRepo),
        getAdres = GetAdres(adresRepo),
        addAdres = AddAdres(adresRepo),
        deleteAdres = DeleteAdres(adresRepo),
        getOwnedPanden = GetOwnedPanden(pandenRepo),
        getPand = GetPand(pandenRepo),
        getPanden = GetPanden(pandenRepo),
        addIssue = AddIssue(issuesRepo),
        getIssues = GetIssues(issuesRepo),
        deleteIssue = DeleteIssue(issuesRepo),
        changeIssueStatus = ChangeIssueStatus(issuesRepo),
        getKamer = GetKamer(kamersRepository),
        addKamer = AddKamer(kamersRepository),
        deleteKamer = DeleteKamer(kamersRepository),
        editKamer = EditKamer(kamersRepository),
        getKamers = GetKamers(kamersRepository),
        getManager = GetManager(managerRepository)
    )
}