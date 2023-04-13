package com.ugnet.sel1.data.repositories

import com.ugnet.sel1.data.backends.AdresBackend
import com.ugnet.sel1.data.backends.ManagerBackend
import com.ugnet.sel1.data.models.Adres
import com.ugnet.sel1.data.models.Manager
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ManagerRepo @Inject constructor(
    private val backend: ManagerBackend,
) {


    fun getManager(id : String) : Manager {
        val manager = backend.readManager(id)
        if(manager != null) {
            return manager
        }
        return Manager("", "","",null, null)
    }
}


//    fun getAdres(id : String): Adres {
//        val adres = backend.readAdres(id)
//        if(adres != null) {
//            return adres
//        }
//        return Adres("", null, null, null, null)
//    }
//
//    fun addAdres(straat : String, huisnummer : Int, postcode : Int, gemeente : String, land : String) {
//        backend.writeNewAdres(straat, huisnummer, postcode, gemeente, land)
//    }
