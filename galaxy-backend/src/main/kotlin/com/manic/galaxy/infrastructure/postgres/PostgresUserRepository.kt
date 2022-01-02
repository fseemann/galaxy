package com.manic.galaxy.infrastructure.postgres

import com.manic.galaxy.domain.shared.Invariants
import com.manic.galaxy.domain.user.User
import com.manic.galaxy.domain.user.UserRepository
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.statements.UpdateBuilder

class PostgresUserRepository : PostgresEntityRepository<User, UsersTable>(UsersTable), UserRepository {
    override fun requireUnique(email: String) {
        val count = UsersTable.select { UsersTable.email eq email }.count()
        Invariants.require(count == 0L) { "user.emailTaken" }
    }

    override fun getByEmail(email: String): User {
        val user = UsersTable.select { UsersTable.email eq email }.map { UsersTable.fromRow(it) }.firstOrNull()
        Invariants.require(user != null) { "user.emailNotTaken" }
        return user!!
    }

    override fun UsersTable.fromRow(row: ResultRow) = User(
        row[id],
        row[email],
        row[password],
        row[role],
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

