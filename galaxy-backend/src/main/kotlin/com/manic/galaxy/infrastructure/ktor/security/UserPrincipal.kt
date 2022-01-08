package com.manic.galaxy.infrastructure.ktor.security

import io.ktor.auth.*
import java.util.*

data class UserPrincipal(val id: UUID) : Principal
