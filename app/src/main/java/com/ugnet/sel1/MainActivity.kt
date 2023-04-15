package com.ugnet.sel1


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.google.android.gms.auth.api.identity.Identity
import com.ugnet.sel1.navigation.MyNavGraph
import com.ugnet.sel1.presentation.adres.Adreses
import com.ugnet.sel1.presentation.adres.AdresesScreen
import com.ugnet.sel1.presentation.sign_in.GoogleAuthUiClient
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val googleAuthUiClient by lazy {
        GoogleAuthUiClient(
            context = applicationContext,
            oneTapClient = Identity.getSignInClient(applicationContext)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
//            // A surface container using the 'background' color from the theme
//            Surface(
//                modifier = Modifier.fillMaxSize(),
//                color = MaterialTheme.colors.background
//            ) {
//                MyNavGraph(googleAuthUiClient = googleAuthUiClient)
//            }
            AdresesScreen()
        }
    }
}


