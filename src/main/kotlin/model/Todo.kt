package model

import kotlinx.serialization.Serializable

@Serializable
data class Todo(
    val id: Int? = null,
    val userId: String,
    val offerId: String,
    val content: String,
    val deadline: String,
    val priority: String,
    val completed: Boolean
)