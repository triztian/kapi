package com.kapi.diner

import org.jetbrains.exposed.sql.*

import com.kapi.exposed.psql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.inList
import org.jetbrains.exposed.sql.transactions.transaction


class DinerRepositoryPsql : DinerRepository {
    /**
     * Get a diner by ID. If the diner does not exist null is returned.
     */
    override suspend fun get(id: Int): Diner? {
        val result = transaction {
            PsqlDiner.selectAll().where { PsqlDiner.id eq id }.firstOrNull()
        }
        if (result == null) return null
        return Diner(
            result[PsqlDiner.id],
            result[PsqlDiner.name],
            result[PsqlDiner.phoneNumberE164],
            listDinerDietaryRestrictions(result[PsqlDiner.id])
        )
    }

    /**
     *
     */
    override suspend fun get(vararg ids: Int): Set<Diner> {
        return transaction {
            PsqlDiner.selectAll().where { PsqlDiner.id inList ids.toList() }.map {
                Diner(
                    it[PsqlDiner.id],
                    it[PsqlDiner.name],
                    it[PsqlDiner.phoneNumberE164],
                    listDinerDietaryRestrictions(it[PsqlDiner.id])
                )
            }.toSet()
        }
    }

    /**
     * Obtains all the dietary restrictions of a diner.
     */
    override suspend fun list(forDiner: Diner?): Set<DietaryRestriction> {
        if (forDiner != null) {
            return listDinerDietaryRestrictions(forDiner.id)
        }

        return PsqlDietaryRestriction.selectAll().map {
            DietaryRestriction(
                it.get(PsqlDietaryRestriction.id),
                it.get(PsqlDietaryRestriction.name),
                it.get(PsqlDietaryRestriction.description)
            )
        }.toSet()
    }

    /**
     * Find the dietary restrictions of a diner by ID.
     */
    private fun listDinerDietaryRestrictions(dinerId: Int): Set<DietaryRestriction> {
        return transaction {
            (PsqlDietaryRestriction innerJoin PsqlDinerDietaryRestriction innerJoin PsqlDiner).select(
                PsqlDietaryRestriction.columns
            )
                .where(PsqlDiner.id eq dinerId).map {
                    DietaryRestriction(
                        it.get(PsqlDietaryRestriction.id),
                        it.get(PsqlDietaryRestriction.name),
                        it.get(PsqlDietaryRestriction.description)
                    )
                }.toSet()
        }
    }
}