package com.ugnet.sel1.data.db

class adres(
    private var huisnr : Int,
    private var straat : String,
    private var postcode : Int,
    private var gemeente : String,
    private var land : String,
    private var provincie : String
    ) {
    override fun toString(): String {
        return "$straat $huisnr, $postcode $gemeente, $provincie, $land"
    }
    fun setHuisnr(huisnr: Int) {
        this.huisnr = huisnr
    }
    fun setStraat(straat: String) {
        this.straat = straat
    }
    fun setPostcode(postcode: Int) {
        this.postcode = postcode
    }
    fun setGemeente(gemeente: String) {
        this.gemeente = gemeente
    }
    fun setLand(land: String) {
        this.land = land
    }
    fun setProvincie(provincie: String) {
        this.provincie = provincie
    }
    fun getHuisnr(): Int {
        return huisnr
    }
    fun getStraat(): String {
        return straat
    }
    fun getPostcode(): Int {
        return postcode
    }
    fun getGemeente(): String {
        return gemeente
    }
    fun getLand(): String {
        return land
    }
    fun getProvincie(): String {
        return provincie
    }
}