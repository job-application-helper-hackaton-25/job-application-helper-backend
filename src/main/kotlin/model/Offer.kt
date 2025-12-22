package model

import kotlinx.serialization.Serializable

@Serializable
data class Offer(
    val id: Int? = null,
    val companyName: String,
    val companyImage: String,
    val position: String,
    val description: String,
    val jobType: String,
    val agreementType: String,
    val salary: String,
    val publishDate: String,
    val linkToTheOffer: String
)
