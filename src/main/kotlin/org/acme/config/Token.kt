package org.acme.config

import io.smallrye.jwt.build.Jwt
import org.acme.entity.User
import org.acme.service.UserService
import javax.inject.Singleton

@Singleton
class Token( private val userService: UserService) {


    fun getToken(user: User): String {
        return generateToken(user)
    }

    private fun setRole(user: User): String {
        return userService.getUserByUsername(user.userName)!!.role.toString()

    }

    private fun generateToken(user : User): String {
        return Jwt.groups(setRole(user)).upn(user.userName).issuer("http://example.com/issuer").sign()
    }

}