package com.ugnet.sel1.authentication.selection

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ugnet.sel1.navigation.MyDestinations


@Composable
fun RoleSelectionScreen(
    openAndPopUp: (String, String) -> Unit,
    setRole: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Choose Your Role", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                setRole("Manager")
                openAndPopUp(MyDestinations.LOGIN_ROUTE, MyDestinations.ROLE_SELECTION_ROUTE)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Manager")
        }
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                setRole("Huurder")
                openAndPopUp(MyDestinations.LOGIN_ROUTE, MyDestinations.ROLE_SELECTION_ROUTE)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Huurder")
        }
    }
}