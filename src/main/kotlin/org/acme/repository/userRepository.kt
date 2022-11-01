package org.acme.repository

import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepository
import org.acme.entity.User
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class userRepository: PanacheRepository<User> {
    fun findByName(userName: String) = find("userName", userName).firstResult()
}