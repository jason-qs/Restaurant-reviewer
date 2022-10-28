package org.acme.controller

import io.quarkus.security.Authenticated
import io.quarkus.security.identity.SecurityIdentity
import io.smallrye.jwt.build.Jwt
import org.acme.config.Certification
import org.acme.config.Token
import org.acme.entity.Restaurant
import org.acme.entity.Review
import org.acme.entity.User
import org.acme.service.UserService
import javax.annotation.security.PermitAll
import javax.annotation.security.RolesAllowed
import javax.inject.Inject
import javax.validation.Valid
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response
import javax.ws.rs.core.SecurityContext

@Path("/api/users")
class UserController(){
    @Inject
    var token : Token? = null
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

    @GET
    @RolesAllowed("admin")
    @Path("/{id}/reviews")
    @Produces(MediaType.APPLICATION_JSON)
    fun getUserReviews(@PathParam("id") id: Long?): MutableSet<Review> {
        return userResource?.getUser(id)?.reviews!!
    }

    @GET
    @RolesAllowed("admin")
    @Path("/keys")
    @Produces(MediaType.APPLICATION_JSON)
    fun getKeys(certification: Certification) {
        return certification.generateKeyPairs()
    }

    @GET
    @RolesAllowed("admin")
    @Path("/{id}/restaurants")
    @Produces(MediaType.APPLICATION_JSON)
    fun getUserRestaurants(@PathParam("id") id: Long?): MutableSet<Restaurant> {
        return userResource?.getUser(id)?.restaurant!!
    }

    @PUT
    @RolesAllowed("admin")
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    fun updateUser(@PathParam("id") id: Long?, user: User?) {
        userResource?.updateUser(id, user!!)
    }
    @DELETE
    @RolesAllowed("admin")
    @Path("/{id}")
    fun deleteUser(@PathParam("id") id: Long?) {
        userResource?.deleteUser(id)
    }

    @POST
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    fun addUser(@Valid user: User): Response {
        val result = userResource?.addUser(user)
        return if (result != null) {
            Response .status(Response.Status.CREATED).entity("created $result").build()
        } else Response.status(Response.Status.BAD_REQUEST).entity("unable to create user!").build()
    }

    @POST
    @PermitAll
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    fun login(@Valid user: User): Response? {
        return if (userResource?.authenticate(user.userName,user.password) == true) {
            val token = token?.getToken(user)
            Response.ok(token).build()
        } else Response.status(Response.Status.BAD_REQUEST).entity("Invalid credentials!").build()
    }


    @GET
    @PermitAll
    @Path("/me")
    @Produces(MediaType.APPLICATION_JSON)
    fun getMe(ctx: SecurityContext): User? {
        return userResource?.getUserByUsername(ctx.userPrincipal.name)
    }
    @GET
    @RolesAllowed("users")
    @Path("/me/reviews")
    @Produces(MediaType.APPLICATION_JSON)
    fun getMeReviews(ctx: SecurityContext): MutableSet<Review> {
        return userResource?.getUserByUsername(ctx.userPrincipal.name)?.reviews!!
    }

    @GET
    @RolesAllowed
    @Path("/me/restaurants")
    @Produces(MediaType.APPLICATION_JSON)
    fun getMeRestaurants(ctx: SecurityContext): MutableSet<Restaurant> {
        return userResource?.getUserByUsername(ctx.userPrincipal.name)?.restaurant!!
    }

}