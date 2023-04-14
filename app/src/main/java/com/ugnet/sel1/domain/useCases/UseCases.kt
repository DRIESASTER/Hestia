package com.ugnet.sel1.domain.useCases


data class UseCases constructor(
    val getAdreses: GetAdreses,
    val addAdres: AddAdres,
    val deleteAdres: DeleteAdres,
    val getOwnedPanden: GetOwnedPanden,
    val getPand: GetPand
)