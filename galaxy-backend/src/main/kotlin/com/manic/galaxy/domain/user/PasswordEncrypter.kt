package com.manic.galaxy.domain.user

interface PasswordEncrypter {
    fun encrypt(rawPassword: String): String

    /**
     * @throws com.manic.galaxy.domain.shared.BusinessException if password do not match
     */
    fun validate(rawPassword: String, encodedPassword: String)
}
