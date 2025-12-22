package mapping

import org.jetbrains.exposed.dao.id.IntIdTable

import model.OfferStatus

object OffersTable : IntIdTable("offers") {
    val companyName = varchar("company_name", 255)
    val companyImage = varchar("company_image", 255)
    val position = varchar("position", 255)
    val description = text("description")
    val jobType = varchar("job_type", 100)
    val agreementType = varchar("agreement_type", 100)
    val salary = varchar("salary", 100)
    val publishDate = varchar("publish_date", 15)
    val linkToTheOffer = varchar("link_to_offer", 700)
    val status = varchar("status", 50).default("FOLLOWING")
}
