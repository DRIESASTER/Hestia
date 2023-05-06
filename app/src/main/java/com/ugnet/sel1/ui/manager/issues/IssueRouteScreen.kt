package com.ugnet.sel1.ui.manager.issues

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.rounded.PinDrop
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.map
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ugnet.sel1.domain.models.Issue
import com.ugnet.sel1.domain.models.Response
import com.ugnet.sel1.domain.models.Status
import com.ugnet.sel1.ui.chat.components.ChatWindowDialog
import com.ugnet.sel1.ui.components.ProgressSwitch
import com.ugnet.sel1.ui.components.ProgressionStatus
import com.ugnet.sel1.ui.components.SimpleTopBar
import com.ugnet.sel1.ui.components.getStatus
import com.ugnet.sel1.ui.theme.MainGroen

@Composable
fun IssueDetailsScreen(
    issue: Issue,
    openChatWindow: () -> Unit,
    viewModel: IssueDetailVM,
    navigateBack : () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        SimpleTopBar(name = issue.titel ?: "", navigate={ navigateBack() })
        Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
            Text(
                text = issue.userId ?: "",
                style = MaterialTheme.typography.h6,
                modifier = Modifier.padding(8.dp)
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Rounded.PinDrop,
                    contentDescription = "person",
                    tint = MainGroen,
                    modifier = Modifier
                        .padding(2.dp)
                        .size(14.dp)
                )
                //viewModel.getRoom()
                Text(
                    text = issue.roomId ?: "",
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier.padding(8.dp),
                    fontSize = 14.sp
                )
            }

        }
        Spacer(modifier = Modifier.height(8.dp))

        viewModel.getUser("").collectAsState(initial = Response.Loading).value.let{
            when(it){
                is Response.Loading -> CircularProgressIndicator()
                is Response.Failure -> Text(text = "failed")
                is Response.Success -> {
                    if(it.data?.accountType == "Manager") {
                        ProgressSwitch(initialState = getStatus(issue.status!!), onStateChanged = {
                            issue.status = when (it) {
                                "Not Started" -> Status.notStarted
                                "In Progress" -> Status.inProgress
                                "Finished" -> Status.finished
                                else -> Status.notStarted
                            }
                            viewModel.changeIssueStatus(issue.issueId!!, issue.status!!)
                        }, modifier = Modifier.padding(8.dp))
                    } else {
                        ProgressionStatus(currentState = getStatus(issue.status!!), modifier = Modifier.padding(8.dp))
                    }
                }
            }
        }


        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "description", modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.body1, fontWeight = FontWeight.Bold
        )
        Card(
            border = BorderStroke(2.dp, MainGroen),
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .height(200.dp),
            elevation = 8.dp
        ) {
            Text(
                text = issue.beschrijving ?: "",
                style = MaterialTheme.typography.body1,
                modifier = Modifier.padding(8.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

/*        Image(
            bitmap = issue.image.asImageBitmap(),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            contentScale = ContentScale.Crop
        )*/

        Spacer(modifier = Modifier.height(8.dp))

        IconButton(onClick = openChatWindow) {
            Icon(imageVector = Icons.Default.Chat, contentDescription = "chat", tint = MainGroen,
                modifier = Modifier
                    .padding(2.dp)
                    .size(40.dp))
        }
    }
}

@Composable
fun IssueRouteScreen(viewModel: IssueDetailVM, navigateBack: () -> Unit) {

    val messages by viewModel.messages.map {
        when (it) {
            is Response.Success -> it.data
            else -> listOf()
        }
    }.observeAsState(listOf())

    var isChatWindowOpen by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        when (viewModel.issueDataResponse) {
            is Response.Loading -> CircularProgressIndicator(Modifier.align(Alignment.Center))
            is Response.Failure -> Text(text = "failed", Modifier.align(Alignment.Center))
            is Response.Success -> {
                val issueData = (viewModel.issueDataResponse as Response.Success).data
                IssueDetailsScreen(issueData!!,viewModel = viewModel,
                    openChatWindow = { isChatWindowOpen = true}, navigateBack = navigateBack)
                if (isChatWindowOpen) {
                    ChatWindowDialog(
                        issue = issueData,
                        messages = messages,
                        currentUserEmail = Firebase.auth.currentUser!!.email!!,
                        onDismiss = { isChatWindowOpen = false },
                        onSendMessage = { messageText ->
                            viewModel.sendMessage(messageText = messageText)
                        })
                }
            }
        }

    }
}


//@Preview
//@Composable
//fun issueDetailsPreview() {
//    IssueDetailsScreen(
//        issue = Issue(
//            titel = "Issue 1",
//            beschrijving = "Issue 1 description",
//            status = Status.notStarted,
//            userId = "1",
//            datum = Timestamp.now(),
//        ),
//        openChatWindow = {}
//    )
//}











