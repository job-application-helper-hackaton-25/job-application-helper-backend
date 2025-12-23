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
                    id = it[OffersTable.id].value,
                    companyName = it[OffersTable.companyName],
                    companyImage = it[OffersTable.companyImage],
                    position = it[OffersTable.position],
                    description = it[OffersTable.description],
                    jobType = it[OffersTable.jobType],
                    agreementType = it[OffersTable.agreementType],
                    salary = it[OffersTable.salary],
                    publishDate = it[OffersTable.publishDate],
                    linkToTheOffer = it[OffersTable.linkToTheOffer],
                    status = model.OfferStatus.valueOf(it[OffersTable.status])
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
                row[status] = offer.status.name
            }.value
        }

    fun update(id: Int, offer: Offer): Offer? =
        transaction {
            val updated = OffersTable.update({ OffersTable.id eq id }) { row ->
                row[companyName] = offer.companyName
                row[companyImage] = offer.companyImage
                row[position] = offer.position
                row[description] = offer.description
                row[jobType] = offer.jobType
                row[agreementType] = offer.agreementType
                row[salary] = offer.salary
                row[publishDate] = offer.publishDate
                row[linkToTheOffer] = offer.linkToTheOffer
                row[status] = offer.status.name
            }
            
            if (updated > 0) {
                OffersTable.select { OffersTable.id eq id }.map {
                    Offer(
                        id = it[OffersTable.id].value,
                        companyName = it[OffersTable.companyName],
                        companyImage = it[OffersTable.companyImage],
                        position = it[OffersTable.position],
                        description = it[OffersTable.description],
                        jobType = it[OffersTable.jobType],
                        agreementType = it[OffersTable.agreementType],
                        salary = it[OffersTable.salary],
                        publishDate = it[OffersTable.publishDate],
                        linkToTheOffer = it[OffersTable.linkToTheOffer],
                        status = model.OfferStatus.valueOf(it[OffersTable.status])
                    )
                }.firstOrNull()
            } else null
        }

    fun partialUpdate(id: Int, updates: Map<String, String>): Offer? =
        transaction {
            val existing = OffersTable.select { OffersTable.id eq id }.firstOrNull()
                ?: return@transaction null

            OffersTable.update({ OffersTable.id eq id }) { row ->
                updates["companyName"]?.let { row[companyName] = it }
                updates["companyImage"]?.let { row[companyImage] = it }
                updates["position"]?.let { row[position] = it }
                updates["description"]?.let { row[description] = it }
                updates["jobType"]?.let { row[jobType] = it }
                updates["agreementType"]?.let { row[agreementType] = it }
                updates["salary"]?.let { row[salary] = it }
                updates["publishDate"]?.let { row[publishDate] = it }
                updates["linkToTheOffer"]?.let { row[linkToTheOffer] = it }
                updates["status"]?.let { row[status] = it }
            }

            OffersTable.select { OffersTable.id eq id }.map {
                Offer(
                    id = it[OffersTable.id].value,
                    companyName = it[OffersTable.companyName],
                    companyImage = it[OffersTable.companyImage],
                    position = it[OffersTable.position],
                    description = it[OffersTable.description],
                    jobType = it[OffersTable.jobType],
                    agreementType = it[OffersTable.agreementType],
                    salary = it[OffersTable.salary],
                    publishDate = it[OffersTable.publishDate],
                    linkToTheOffer = it[OffersTable.linkToTheOffer],
                    status = model.OfferStatus.valueOf(it[OffersTable.status])
                )
            }.firstOrNull()
        }

    fun deleteById(id: Int): Boolean =
        transaction {
            OffersTable.deleteWhere { OffersTable.id eq id } > 0
        }
}