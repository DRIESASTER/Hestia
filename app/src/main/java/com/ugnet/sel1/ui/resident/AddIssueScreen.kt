package com.ugnet.sel1.ui.resident

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ugnet.sel1.ui.components.InputWithTitle
import com.ugnet.sel1.ui.theme.MainGroen

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AddIssueScreen(
    viewModel: ResidentHomeVM,
    navigate : (String) -> Unit
) {
    var propertyExpanded by remember { mutableStateOf(false) }
    var roomExpanded by remember { mutableStateOf(false) }
    var typeExpanded by remember { mutableStateOf(false) }

    var issueTitle by remember { mutableStateOf("") }
    var issueDescription by remember { mutableStateOf("") }
    var issueProperty by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Add new issue",  fontWeight = FontWeight.Bold, fontSize = 30.sp, color = MainGroen)

        InputWithTitle(title = "Title", initValue = issueTitle, onValuechanged = {issueTitle = it})

        InputWithTitle(title = "Description", initValue = issueDescription, onValuechanged = {issueDescription = it})
        //Property dropdown menu
        ExposedDropdownMenuBox(
            expanded = propertyExpanded,
            onExpandedChange = { propertyExpanded = it }
        ) {
            TextField(
                value = "Select property",
                onValueChange = {},
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = propertyExpanded)
                },
                colors = ExposedDropdownMenuDefaults.textFieldColors()
            )
            
            ExposedDropdownMenu(expanded = propertyExpanded, onDismissRequest = { propertyExpanded = false }) {
                
            }
        }
    }
}

//@Preview
//@Composable
//fun AddIssueScreenPreview() {
//    //AddIssueScreen(navigate = {})
//}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun test() {
    var issueTitle by remember { mutableStateOf("title") }

    Column() {
        //Text(text = "Title",  fontWeight = FontWeight.Bold, fontSize = 20.sp, color = MainGroen)
        InputWithTitle(title = "Title", initValue = issueTitle, onValuechanged = {issueTitle = it})
    }
}

@Preview
@Composable
fun testPreview() {
    test()
}