package org.acme.config

import io.smallrye.jwt.auth.principal.PrincipalUtils.setClaims
import io.smallrye.jwt.build.Jwt
import org.acme.entity.User
import javax.inject.Singleton
import javax.resource.spi.ConfigProperty

@Singleton
class Token {

    private var issuer: String = ""

    fun getToken(user: User): String {
        return generateToken(user)
    }

    private fun setClaims(user: User): Map<String, String?> {
        return mapOf("username" to user.userName, "role" to user.role)
    }

    private fun generateToken(user : User): String {
        return Jwt.claims(setClaims(user)).sign()
    }

}