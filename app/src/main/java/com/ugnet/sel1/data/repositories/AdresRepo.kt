package com.ugnet.sel1.data.repositories

import com.ugnet.sel1.data.models.Adres
import com.ugnet.sel1.data.AdresBackend
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AdresRepo @Inject constructor(
    private val backend: AdresBackend,
) {
    fun getAdres(id : String): Adres {
        val adres = backend.readAdres(id)
        if(adres != null) {
            return adres
        }
        return Adres("", null, null, null, null)
    }

    fun addAdres(straat : String, huisnummer : Int, postcode : Int, gemeente : String, land : String) {
        backend.writeNewAdres(straat, huisnummer, postcode, gemeente, land)
    }
}