package com.ugnet.sel1


import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import dagger.hilt.android.AndroidEntryPoint
import androidx.navigation.compose.rememberNavController
import com.ugnet.sel1.domain.models.Response
import com.ugnet.sel1.domain.repository.oudeShit.Adreses
import com.ugnet.sel1.navigation.HestiaApp
import com.ugnet.sel1.navigation.MyDestinations

import com.ugnet.sel1.presentation.profile.adres.AdresesScreen
import com.ugnet.sel1.ui.manager.ManagerHomeScreen


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<AuthViewModel>()

    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HestiaApp(viewModel)
        }
    }
}



