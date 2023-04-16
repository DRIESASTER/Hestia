package com.ugnet.sel1.domain.useCases.oudeShit.panden.Issues

import com.google.firebase.Timestamp
import com.ugnet.sel1.domain.repository.IssuesRepository

class AddIssue constructor(
    private val repo: IssuesRepository
) {
    suspend operator fun invoke(
        beschrijving: String,
        datum : Timestamp,
        titel : String
    ) = repo.addIssueToFirestore(beschrijving, datum, titel)
}