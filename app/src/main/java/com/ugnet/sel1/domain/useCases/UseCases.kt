package com.ugnet.sel1.domain.useCases

import com.ugnet.sel1.domain.useCases.nieuwUsecases.GetOwnedProperties


data class UseCases constructor(
    //val getUser : GetUser,
    val propertyExist : PropertyExists,
    val getOwnedPropertiesNoResponse: GetOwnedPropertiesNoResponse,
    val getAnnouncementsForManager: GetAnnouncementsForManager,
    val getAnnouncementsForHiree: GetAnnouncementsForHiree,
    val addAnnouncementToProperty : AddAnnouncementToProperty,
    val getUser : GetUserTest,
    val getUserNoResponse: GetUserNoReponse,
    val getIssuesForRoom: GetIssuesForRoom,
    val getRoomsForProperty: GetRoomsForProperty,
    val getOwnedProperties: GetOwnedProperties,
    val getAccesibleRoomsPerUser: GetAccesibleRoomsPerUser,
    val changeIssueStatus: ChangeIssueStatus,
    val addRoomToProperty: AddRoomToProperty,
    val deleteRoomFromProperty: DeleteRoomFromProperty,
    val addProperty: AddProperty,
    val deleteProperty : DeleteProperty,
    val addIssue: AddIssue,
    val deleteIssue: DeleteIssue,
    val getUserByEmail: GetUserByEmail,
    val getIssuesPerProperty: GetIssuesPerProperty,
    val getIssuesForRenter: GetIssuesForRenter,
    val getRentedProperties: GetRentedProperties,
    val getRentersList: GetRentersList,
    val addUserToProperty: AddUserToProperty,
    val removeUserFromProperty: RemoveUserFromProperty,
    val getIssue: GetIssue,
    val addUserToRoom: AddUserToRoom,
    val editProperty: EditProperty,
    val getProperty: GetProperty,
    val getRoom: GetRoom,
    val getImage: GetImage
)