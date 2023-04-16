package com.ugnet.sel1.di

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ugnet.sel1.data.repositories.*
import com.ugnet.sel1.domain.repository.*
import com.ugnet.sel1.domain.useCases.*
import com.ugnet.sel1.domain.useCases.GetUser
import com.ugnet.sel1.domain.useCases.nieuwUsecases.GetOwnedProperties
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideManagerRef() = Firebase.firestore


//    @Provides
//    fun provideAdresesRepository(
//        adresesRef: FirebaseFirestore
//    ): AdresRepository = AdresRepositoryImpl(adresesRef)

    @Provides
    fun providePropertyRepository(
        dbRef: FirebaseFirestore
    ): PropertiesRepository = PropertiesRepositoryImpl(dbRef)

    @Provides
    fun provideIssuesRepository(
        dbRef: FirebaseFirestore
    ): IssuesRepository = IssuesRepositoryImpl(dbRef)

    @Provides
    fun provideRoomsRepository(
        dbRef: FirebaseFirestore
    ): RoomsRepository = RoomsRepositoryImpl(dbRef)

    @Provides
    fun provideUsersRepository(
        dbRef: FirebaseFirestore
    ): UsersRepository = UsersRepositoryImpl(dbRef)

    @Provides
    fun provideUseCases(
        propertyRepo : PropertiesRepository,
        issuesRepo : IssuesRepository,
        roomsRepo: RoomsRepository,
        usersRepo: UsersRepository,
    ) = UseCases(
        getUser = GetUser(usersRepo),
        getOwnedProperties = GetOwnedProperties(propertyRepo),
        getIssuesForRoom = GetIssuesForRoom(issuesRepo),
        getRoomsForProperty = GetRoomsForProperty(roomsRepo),
        changeIssueStatus = ChangeIssueStatus(issuesRepo)
    )
}