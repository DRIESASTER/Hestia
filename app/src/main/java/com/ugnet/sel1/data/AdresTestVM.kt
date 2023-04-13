package com.ugnet.sel1.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ugnet.sel1.data.models.Adres
import com.ugnet.sel1.data.repositories.GetAdresUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class AdresTestVM @Inject constructor(private val getAdresUseCase : GetAdresUseCase): ViewModel() {

    fun getAdres() : Adres = getAdresUseCase.invoke("1HfbWeoiU6bIP5eBZ3Wt")




    private val data = MutableStateFlow(getAdresUseCase.invoke("1HfbWeoiU6bIP5eBZ3Wt"))


//    fun refreshQuote() {
//        data.value = backend.get()
//    }
}