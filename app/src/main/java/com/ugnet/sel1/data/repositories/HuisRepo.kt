package com.ugnet.sel1.data.repositories

import com.ugnet.sel1.data.backends.HuisBackend
import com.ugnet.sel1.data.models.Huis
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HuisRepo @Inject constructor(
    private val backend: HuisBackend,
) {
    fun getHuis(id : String) : Huis {
        val huis = backend.readHuis(id)
        if(huis != null) {
            return huis
        }
        return Huis("", "", "")
    }

    fun getHuizen(ids: List<String>?) : List<Huis> {
        return backend.readHuizen(ids)
    }

//
//    fun addAdres(straat : String, huisnummer : Int, postcode : Int, gemeente : String, land : String) {
//        backend.writeNewAdres(straat, huisnummer, postcode, gemeente, land)
//    }
}