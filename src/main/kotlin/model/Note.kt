package model

import kotlinx.serialization.Serializable

@Serializable
data class Note(
    val id: Int? = null,
    val userId: String,
    val offerId: String,
    val stage: String,
    val content: String,
    val date: String
)