package service

import mapping.OffersTable
import model.Offer
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class OfferService {

    fun getAllOffers(): List<Offer> =
        transaction {
            OffersTable.selectAll().map {
                Offer(
                    companyName = it[OffersTable.companyName],
                    companyImage = it[OffersTable.companyImage],
                    position = it[OffersTable.position],
                    description = it[OffersTable.description],
                    jobType = it[OffersTable.jobType],
                    agreementType = it[OffersTable.agreementType],
                    salary = it[OffersTable.salary],
                    publishDate = it[OffersTable.publishDate],
                    linkToTheOffer = it[OffersTable.linkToTheOffer]
                )
            }
        }

    fun insert(offer: Offer): Int =
        transaction {
            OffersTable.insertAndGetId { row ->
                row[companyName] = offer.companyName
                row[companyImage] = offer.companyImage
                row[position] = offer.position
                row[description] = offer.description
                row[jobType] = offer.jobType
                row[agreementType] = offer.agreementType
                row[salary] = offer.salary
                row[publishDate] = offer.publishDate
                row[linkToTheOffer] = offer.linkToTheOffer
            }.value
        }

    fun deleteById(id: Int): Boolean =
        transaction {
            OffersTable.deleteWhere { OffersTable.id eq id } > 0
        }
}