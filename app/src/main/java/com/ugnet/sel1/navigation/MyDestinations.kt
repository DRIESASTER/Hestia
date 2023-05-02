package com.ugnet.sel1.navigation


object MyDestinations {
    const val SPLASH_ROUTE = "splash"
    const val ADD_PROPERTY = "add_property"
    const val ROLE_SELECTION_ROUTE = "role_selection"
    const val LOGIN_ROUTE = "login"
    const val SIGN_UP_ROUTE = "sign_up/{role}"
    const val PROFILE_ROUTE = "profile"
    const val MANAGER_HOME_ROUTE = "manager_home"
    const val HIREE_HOME_ROUTE = "hiree_home"
    const val EMAIL_VER_ROUTE = "email_ver"
    const val FORGOT_PASSWORD_ROUTE = "forgot_password"
    const val ROOM_EDIT_ROUTE_APP = "room_edit/{propId}"
    const val ADD_ISSUE_ROUTE = "add_issue"
    const val ISSUE_ROUTE = "issue/{${IssueArgs.IssueId}}/{${IssueArgs.PropId}}"
    const val ROOM_EDIT_ROUTE_HOUSE = "add_house/${HouseEditArgs.Email}/${HouseEditArgs.PropId}"
    const val EDIT_PROPERTY_ROUTE = "edit_property/${IssueArgs.PropId}"

    object IssueArgs {
        const val IssueId = "issueId"
        const val PropId = "propId"
    }
    object RoomEditArgs {
        const val PropId = "propId"
    }

    object HouseEditArgs{
        const val Email = "email"
        const val PropId = "propId"
    }

}