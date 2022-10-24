package org.acme.controller

import io.quarkus.security.Authenticated
import org.acme.entity.User
import org.acme.service.UserService
import javax.annotation.security.PermitAll
import javax.annotation.security.RolesAllowed
import javax.inject.Inject
import javax.ws.rs.*
import javax.ws.rs.core.MediaType

@Authenticated
@Path("/api/users")
class UserController {
    @Inject
    var userResource: UserService? = null

    @GET
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    fun getUsers(): List<User?>? {
        return userResource?.getUsers()
    }

    @GET
    @PermitAll
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    fun getUser(@PathParam("id") id: Long?): User? {
        return userResource?.getUser(id)
    }

    @PUT
    @RolesAllowed("user", "admin")
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    fun updateUser(@PathParam("id") id: Long?, user: User?) {
        userResource?.updateUser(id, user!!)
    }

    @POST
    @RolesAllowed("user","admin")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    fun addUser(user: User?): User? {
        return userResource?.addUser(user)
    }

    @DELETE
    @RolesAllowed("user","admin")
    @Path("/{id}")
    fun deleteUser(@PathParam("id") id: Long?) {
        userResource?.deleteUser(id)
    }
}