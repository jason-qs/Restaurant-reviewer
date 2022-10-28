package org.acme.config

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import javax.inject.Singleton

@Singleton
class Password {
    private val encoder = BCryptPasswordEncoder()

    fun encodePassword(input: String): String {
        return encoder.encode(input)
    }

    fun validPassword(input: String, hashPassword: String): Boolean {
        return encoder.matches(input, hashPassword)
    }
}