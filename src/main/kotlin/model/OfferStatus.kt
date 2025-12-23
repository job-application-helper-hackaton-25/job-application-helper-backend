package model

import kotlinx.serialization.Serializable

@Serializable
enum class OfferStatus {
    FOLLOWING,             // Tracking the offer
    APPLIED,               // Application submitted
    INTERVIEW_STAGE,       // In interview process
    WAITING_FOR_FEEDBACK,  // Awaiting response
    OFFER_RECEIVED,        // Job offer received
    REJECTED,              // Application rejected
    ACCEPTED,              // Offer accepted
    NO_FEEDBACK            // No response received
}
