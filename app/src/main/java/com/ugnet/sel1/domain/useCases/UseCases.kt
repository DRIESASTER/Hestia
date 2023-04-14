package com.ugnet.sel1.domain.useCases

import com.ugnet.sel1.domain.useCases.Issues.AddIssue
import com.ugnet.sel1.domain.useCases.Issues.ChangeIssueStatus
import com.ugnet.sel1.domain.useCases.Issues.DeleteIssue
import com.ugnet.sel1.domain.useCases.Issues.GetIssue
import com.ugnet.sel1.domain.useCases.adressen.AddAdres
import com.ugnet.sel1.domain.useCases.adressen.DeleteAdres
import com.ugnet.sel1.domain.useCases.adressen.GetAdreses
import com.ugnet.sel1.domain.useCases.panden.GetOwnedPanden
import com.ugnet.sel1.domain.useCases.panden.GetPand


data class UseCases constructor(
    val getAdreses: GetAdreses,
    val addAdres: AddAdres,
    val deleteAdres: DeleteAdres,
    val getOwnedPanden: GetOwnedPanden,
    val getPand: GetPand,
    val getIssue: GetIssue,
    val addIssue: AddIssue,
    val deleteIssue: DeleteIssue,
    val changeIssueStatus: ChangeIssueStatus
)