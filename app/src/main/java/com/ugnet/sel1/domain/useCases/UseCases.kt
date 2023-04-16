package com.ugnet.sel1.domain.useCases

import com.ugnet.sel1.domain.useCases.Issues.AddIssue
import com.ugnet.sel1.domain.useCases.Issues.ChangeIssueStatus
import com.ugnet.sel1.domain.useCases.Issues.DeleteIssue
import com.ugnet.sel1.domain.useCases.Issues.GetIssues
import com.ugnet.sel1.domain.useCases.adressen.AddAdres
import com.ugnet.sel1.domain.useCases.adressen.DeleteAdres
import com.ugnet.sel1.domain.useCases.adressen.GetAdres
import com.ugnet.sel1.domain.useCases.adressen.GetAdreses
import com.ugnet.sel1.domain.useCases.kamers.*
import com.ugnet.sel1.domain.useCases.manager.GetManager
import com.ugnet.sel1.domain.useCases.panden.DeletePand
import com.ugnet.sel1.domain.useCases.panden.GetOwnedPanden
import com.ugnet.sel1.domain.useCases.panden.GetPand
import com.ugnet.sel1.domain.useCases.panden.GetPanden


data class UseCases constructor(
    val getAdreses: GetAdreses,
    val getAdres: GetAdres,
    val addAdres: AddAdres,
    val deleteAdres: DeleteAdres,
    val getOwnedPanden: GetOwnedPanden,
    val getPand: GetPand,
    val getPanden: GetPanden,
    val deletePand: DeletePand,
    val getIssues: GetIssues,
    val addIssue: AddIssue,
    val deleteIssue: DeleteIssue,
    val changeIssueStatus: ChangeIssueStatus,
    val getKamer: GetKamer,
    val addKamer: AddKamer,
    val deleteKamer: DeleteKamer,
    val editKamer: EditKamer,
    val getKamers : GetKamers,
    val getManager: GetManager
)