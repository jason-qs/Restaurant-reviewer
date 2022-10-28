package org.acme.service

import io.quarkus.security.identity.SecurityIdentity
import io.smallrye.jwt.build.Jwt
import org.acme.config.Password
import org.acme.entity.User
import javax.annotation.security.PermitAll
import javax.annotation.security.RolesAllowed
import javax.inject.Inject
import javax.inject.Singleton
import javax.persistence.EntityManager
import javax.transaction.Transactional

@Singleton
class UserService(private val security: Password) {
    @Inject
    var entityManager: EntityManager? = null
    fun getUsers(): kotlin.collections.List<User?>? {
        return entityManager!!.
        createQuery("SELECT c FROM User c").
        resultList as List<User?>?
    }
    fun getUser(id: Long?): User {
        return entityManager!!.find(User::class.java, id)
    }
    fun getMe(identity: SecurityIdentity?): Boolean{
        return true
    }

    fun getUserByUsername(username: String): User {
        return entityManager!!.find(User::class.java, username)
    }

    @Transactional(Transactional.TxType.REQUIRED)
    fun updateMe(identity: SecurityIdentity?, user: User) {
        val userToUpdate: User = entityManager!!.find(User::class.java, identity)
        if (null != userToUpdate) {
            userToUpdate.firstName = user.firstName
            userToUpdate.lastName = user.lastName
            userToUpdate.userName = user.userName
            userToUpdate.password = user.password
        } else {
            throw RuntimeException("No such user available")
        }
    }

    @Transactional(Transactional.TxType.REQUIRED)
    fun addUser(user: User?): User? {
        if (user != null) {
            user.password = security.encodePassword(user.password)
        }
        entityManager!!.persist(user)
        return user
    }

    private fun validateUser(username:String, pwd: String): Boolean {
        val user = getUserByUsername(username)
        return (user != null && security.validPassword(pwd, user.password))
    }

    fun authenticate(username: String, password: String): Boolean {
        try{
            val user = getUserByUsername(username)
            return (user != null && validateUser(username, password))

        } catch (x: Exception){

        }
        return false
    }

    @Transactional(Transactional.TxType.REQUIRED)
    fun updateUser(id: Long?, user: User) {
        val userToUpdate: User = entityManager!!.
        find(User::class.java, id)
        if (null != userToUpdate) {
            userToUpdate.firstName = user.firstName
            userToUpdate.lastName = user.lastName
            userToUpdate.password = user.password
        } else {
            throw RuntimeException("No such user available")
        }
    }
    @Transactional(Transactional.TxType.REQUIRED)
    fun deleteUser(id: Long?) {
        val user: User = getUser(id)
        entityManager!!.remove(user)
    }
}