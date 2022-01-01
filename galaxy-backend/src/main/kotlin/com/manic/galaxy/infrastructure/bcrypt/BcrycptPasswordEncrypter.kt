package com.manic.galaxy.infrastructure.bcrypt

import at.favre.lib.crypto.bcrypt.BCrypt
import com.manic.galaxy.domain.shared.Invariants
import com.manic.galaxy.domain.user.PasswordEncrypter

class BcrycptPasswordEncrypter : PasswordEncrypter {
    private val encrypter = BCrypt.withDefaults()
    private val verifyer = BCrypt.verifyer()

    override fun encrypt(rawPassword: String): String {
        return encrypter.hashToString(8, rawPassword.toCharArray())
    }

    override fun validate(
        rawPassword: String,
        encodedPassword: String,
    ) {
        val result = verifyer.verify(rawPassword.toCharArray(), encodedPassword.toCharArray())

        Invariants.require(result.verified) { "user.invalidPassword" }
    }
}
