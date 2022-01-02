package com.manic.galaxy.infrastructure.postgres

import com.manic.galaxy.domain.facility.Facility
import com.manic.galaxy.domain.facility.FacilityRepository
import com.manic.galaxy.domain.facility.Mine
import com.manic.galaxy.domain.facility.Storage
import com.manic.galaxy.domain.shared.BusinessException
import com.manic.galaxy.domain.shared.Invariants
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import java.util.*

class PostgresFacilityRepository : FacilityRepository {
    override fun getStorage(planetId: UUID): Storage {
        val row = FacilitiesTable
            .select { FacilitiesTable.planetId eq planetId and (FacilitiesTable._type eq "STORAGE") }
            .firstOrNull()
            ?: throw BusinessException("entity.idUnused")
        val storage = StoragesTable.select { StoragesTable.id eq row[FacilitiesTable.id] }
            .map { fromStorageRow(row, it) }
            .firstOrNull()
        Invariants.require(storage != null) { "planet.hasNoStorage" }
        return storage!!
    }

    override fun findMine(planetId: UUID): Mine? {
        val row = FacilitiesTable
            .select { FacilitiesTable.planetId eq planetId and (FacilitiesTable._type eq "MINE") }
            .firstOrNull()
            ?: throw BusinessException("entity.idUnused")
        return MinesTable.select { MinesTable.id eq row[FacilitiesTable.id] }.map { fromMineRow(row, it) }.firstOrNull()
    }

    override fun insert(vararg facilities: Facility): List<Facility> {
        facilities.forEach(::insert)
        return facilities.toList()
    }

    override fun insert(entity: Facility): Facility {
        FacilitiesTable.insert { toRow(it, entity) }
        when (entity) {
            is Storage -> StoragesTable.insert { toStorageRow(it, entity) }
            is Mine -> MinesTable.insert { toMineRow(it, entity) }
        }
        return entity
    }

    override fun list(planetId: UUID): List<Facility> {
        return FacilitiesTable.select { FacilitiesTable.planetId eq planetId }
            .map { joinSubtypes(it) }
    }

    override fun update(entity: Facility): Facility {
        FacilitiesTable.update({ FacilitiesTable.id eq entity.id }) { toRow(it, entity) }
        when (entity) {
            is Storage -> StoragesTable.update({ StoragesTable.id eq entity.id }) { toStorageRow(it, entity) }
            is Mine -> MinesTable.update({ MinesTable.id eq entity.id }) { toMineRow(it, entity) }
        }
        return entity
    }

    override fun delete(entity: Facility) {
        when (entity) {
            is Storage -> StoragesTable.deleteWhere { StoragesTable.id eq entity.id }
            is Mine -> MinesTable.deleteWhere { MinesTable.id eq entity.id }
        }
    }

    override fun get(id: UUID): Facility {
        val row = FacilitiesTable.select { FacilitiesTable.id eq id }.firstOrNull()
            ?: throw BusinessException("entity.idUnused")
        return joinSubtypes(row)
    }

    private fun joinSubtypes(row: ResultRow): Facility {
        val id = row[FacilitiesTable.id]
        return when (val type = row[FacilitiesTable._type]) {
            "STORAGE" -> StoragesTable.select { StoragesTable.id eq id }.map { fromStorageRow(row, it) }.first()
            "MINE" -> MinesTable.select { MinesTable.id eq id }.map { fromMineRow(row, it) }.first()
            else -> throw IllegalStateException("Unknown facility type '$type' with id '$id'.")
        }
    }

    private fun fromMineRow(
        facilityRow: ResultRow,
        mineRow: ResultRow,
    ): Mine {
        return Mine(
            facilityRow[FacilitiesTable.id],
            facilityRow[FacilitiesTable.planetId],
            facilityRow[FacilitiesTable.createdAt],
            mineRow[MinesTable.mineralsPerMinute]
        )
    }

    private fun toMineRow(
        statement: UpdateBuilder<Number>,
        mine: Mine,
    ) {
        statement[MinesTable.id] = mine.id
        statement[MinesTable.mineralsPerMinute] = mine.mineralsPerMinute
    }

    private fun toStorageRow(
        statement: UpdateBuilder<Number>,
        storage: Storage,
    ) {
        statement[StoragesTable.id] = storage.id
        statement[StoragesTable.minerals] = storage.minerals
        statement[StoragesTable.mineralsCapacity] = storage.mineralsCapacity
        statement[StoragesTable.lastUpdatedAt] = storage.lastUpdatedAt
    }

    private fun fromStorageRow(
        facilityRow: ResultRow,
        storageRow: ResultRow,
    ): Storage {
        return Storage(
            facilityRow[FacilitiesTable.id],
            facilityRow[FacilitiesTable.planetId],
            facilityRow[FacilitiesTable.createdAt],
            storageRow[StoragesTable.minerals],
            storageRow[StoragesTable.mineralsCapacity],
            storageRow[StoragesTable.lastUpdatedAt],
        )
    }

    private fun fromStorageRow(
        row: ResultRow,
    ): Storage {
        return Storage(
            row[FacilitiesTable.id],
            row[FacilitiesTable.planetId],
            row[FacilitiesTable.createdAt],
            row[StoragesTable.minerals],
            row[StoragesTable.mineralsCapacity],
            row[StoragesTable.lastUpdatedAt],
        )
    }

    private fun FacilitiesTable.toRow(
        statement: UpdateBuilder<Number>,
        entity: Facility,
    ) {
        statement[id] = entity.id
        statement[createdAt] = entity.createdAt
        statement[planetId] = entity.planetId
        statement[_type] = when (entity) {
            is Storage -> "STORAGE"
            is Mine -> "MINE"
        }
    }

}
