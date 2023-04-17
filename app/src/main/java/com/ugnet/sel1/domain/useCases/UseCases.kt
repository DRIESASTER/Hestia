package com.ugnet.sel1.domain.useCases

import com.ugnet.sel1.domain.repository.DeletePropertyResponse
import com.ugnet.sel1.domain.useCases.nieuwUsecases.GetOwnedProperties


data class UseCases constructor(
    val getUser : GetUser,
    val getIssuesForRoom: GetIssuesForRoom,
    val getRoomsForProperty: GetRoomsForProperty,
    val getOwnedProperties: GetOwnedProperties,
    val getRentedRoomsByUser: GetRentedRoomsByUser,
    val changeIssueStatus: ChangeIssueStatus,
    val addRoomToProperty: AddRoomToProperty,
    val deleteRoomFromProperty: DeleteRoomFromProperty,
    val addProperty: AddProperty,
    val deleteProperty : DeleteProperty
)