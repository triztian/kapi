package com.kapi.reservation

import java.time.LocalDateTime

import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

import com.kapi.diner.Diner
import com.kapi.diner.DinerRepository
import com.kapi.exposed.psql.*
import com.kapi.restaurant.Restaurant
import com.kapi.restaurant.RestaurantRepository
import com.kapi.restaurant.Table
import org.jetbrains.exposed.sql.SqlExpressionBuilder.inList
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.insert

/**
 *
 */
class ReservationRepositoryPsql(
    private val restaurantRepository: RestaurantRepository,
    private val dinerRepository: DinerRepository,
) : ReservationRepository {
    /**
     * Get a reservation by its ID. Returns `null` if the reservation does not exists.
     */
    override suspend fun get(id: Int): Reservation? {
        return newSuspendedTransaction {
            val resultRow = PsqlReservation.selectAll().where(PsqlReservation.id eq id).firstOrNull()
                ?: return@newSuspendedTransaction null

            val restaurant = restaurantRepository.get(
                resultRow[PsqlReservation.restaurantId]
            ) ?: return@newSuspendedTransaction null

            val diners = (PsqlDiner innerJoin PsqlReservationDiner).select(PsqlDiner.columns)
                .where(PsqlReservationDiner.reservationId eq id).mapNotNull {
                    dinerRepository.get(it[PsqlReservationDiner.dinerId])
                }.toSet()

            return@newSuspendedTransaction Reservation(
                resultRow[PsqlReservation.id],
                resultRow[PsqlReservation.status],
                resultRow[PsqlReservation.datetime],
                restaurant,
                emptySet(),
                diners
            )
        }
    }

    /**
     * Find a set of reservations that match the given criteria.
     */
    override suspend fun find(
        vararg restaurants: Restaurant,
        atDatetime: LocalDateTime?,
        withStatus: Set<ReservationStatus>,
        forPartySize: Int?
    ): Set<Reservation> {
        return newSuspendedTransaction {
            val restaurantIds = restaurants.map { it.id }.toList()
            val statusParams = if (withStatus.isEmpty()) ReservationStatus.entries else withStatus.toList()

            var query =
                (PsqlReservation.restaurantId inList restaurantIds) and (PsqlReservation.status inList statusParams)
            if (atDatetime != null) {
                query = query and (PsqlReservation.datetime eq atDatetime)
            }

            val restaurantsMap = restaurants.fold(mutableMapOf<Int, Restaurant>()) { map, restaurant ->
                map[restaurant.id] = restaurant
                map
            }

            return@newSuspendedTransaction PsqlReservation.selectAll()
                .where(query)
                .map {
                    Reservation(
                        it[PsqlReservation.id],
                        it[PsqlReservation.status],
                        it[PsqlReservation.datetime],
                        restaurantsMap[it[PsqlReservation.restaurantId]]!!, // guaranteed by the query condition
                        listReservationTables(it[PsqlReservation.restaurantId]),
                        listReservationDiners(it[PsqlReservation.id])
                    )
                }.toSet()
        }
    }

    /**
     * Creates a reservation at the given restaurant with the given diners at the given time.
     */
    override suspend fun create(restaurant: Restaurant, diners: Set<Diner>, atDatetime: LocalDateTime): Reservation? {
        return newSuspendedTransaction {
            val reservationId = PsqlReservation.insert {
                it[restaurantId] = restaurant.id
                it[datetime] = atDatetime
            } get PsqlReservation.id

            diners.forEach { diner ->
                PsqlReservationDiner.insert {
                    it[PsqlReservationDiner.dinerId] = diner.id
                    it[PsqlReservationDiner.reservationId] = reservationId
                }
            }

            // TODO: find available tables
            val availableTables = emptySet<Table>()
            availableTables.forEach { table ->
                PsqlReservationTable.insert {
                    it[PsqlReservationTable.reservationId] = reservationId
                    it[tableId] = table.id
                }
            }

            return@newSuspendedTransaction get(reservationId)
        }
    }

    // -- Support --

    /**
     * Provides a list of diners for a given reservation ID.
     */
    private suspend fun listReservationDiners(reservationId: Int): Set<Diner> {
        return newSuspendedTransaction {
            return@newSuspendedTransaction (PsqlReservationDiner innerJoin PsqlDiner).select(
                PsqlDiner.id,
                PsqlReservationDiner.reservationId
            )
                .where(PsqlReservationDiner.reservationId eq reservationId).mapNotNull {
                    dinerRepository.get(it[PsqlReservationDiner.dinerId])
                }.toSet()
        }
    }

    /**
     * Provides list of booked tables for a given reservation ID.
     */
    private suspend fun listReservationTables(reservationId: Int): Set<Table> {
        return newSuspendedTransaction {
            return@newSuspendedTransaction (PsqlReservationTable innerJoin PsqlTable).select(PsqlTable.columns)
                .where(PsqlReservationTable.reservationId eq reservationId)
                .map {
                    Table(
                        it[PsqlTable.id],
                        it[PsqlTable.capacity]
                    )
                }.toSet()
        }
    }
}