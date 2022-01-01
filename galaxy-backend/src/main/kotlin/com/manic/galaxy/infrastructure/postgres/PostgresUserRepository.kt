package com.manic.galaxy.infrastructure.postgres

import com.manic.galaxy.domain.user.User
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.statements.UpdateBuilder

class PostgresUserRepository : PostgresEntityRepository<User, UsersTable>(UsersTable) {
    override fun fromRow(row: ResultRow) = User(
        row[UsersTable.id],
        row[UsersTable.email],
        row[UsersTable.password],
        row[UsersTable.role],
    )

    override fun UsersTable.toRow(
        statement: UpdateBuilder<Number>,
        entity: User,
    ) {
        statement[id] = entity.id
        statement[email] = entity.email
        statement[password] = entity.password
        statement[role] = entity.role
    }
}

