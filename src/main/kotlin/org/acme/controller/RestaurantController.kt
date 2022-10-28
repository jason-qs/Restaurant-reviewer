package org.acme.controller

import io.quarkus.security.Authenticated
import org.acme.entity.Restaurant
import org.acme.service.RestaurantService
import org.acme.service.UserService
import javax.annotation.security.PermitAll
import javax.annotation.security.RolesAllowed

import javax.inject.Inject
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.SecurityContext

@Path("/api/restaurants")
class RestaurantController {
    @Inject
    var restaurantResource: RestaurantService? = null

    @Inject
    var userResource: UserService? = null

    @GET
    @RolesAllowed("admin")
    @Produces(MediaType.APPLICATION_JSON)
    fun getRestaurants(): List<Restaurant?>? {
        return restaurantResource?.getRestaurants()
    }

    @GET
    @RolesAllowed("admin")
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    fun getRestaurant(@PathParam("id") id: Long?): Restaurant? {
        return  restaurantResource?.getRestaurant(id)
    }

    @PUT
    @RolesAllowed("admin")
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    fun updateRestaurant(@PathParam("id") id: Long?, restaurant: Restaurant?) {
        restaurantResource?.updateRestaurant(id, restaurant!!)
    }

    @POST
    @RolesAllowed("admin", "user")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    fun addRestaurant(restaurant: Restaurant?): Restaurant? {
        return  restaurantResource?.addRestaurant(restaurant)
    }

    @DELETE
    @RolesAllowed("admin")
    @Path("/{id}")
    fun deleteRestaurant(@PathParam("id") id: Long?): Unit? {
        return restaurantResource?.deleteRestaurant(id)
    }

    @PUT
    @RolesAllowed("user")
    @Path("/me/{id}")
    fun updateUserRestaurant(@PathParam("id") id: Long?, ctx : SecurityContext, restaurant: Restaurant?): Any? {
        if(userResource?.getUserByUsername(ctx.userPrincipal.name)?.restaurant!! ==   restaurantResource?.getRestaurant(id)) {
           return restaurantResource?.updateRestaurant(id, restaurant!!)
        } else {
            return null
        }
    }

    @DELETE
    @RolesAllowed("user")
    @Path("/me/{id}")
    fun deleteUserRestaurant(@PathParam("id") id: Long?, ctx : SecurityContext): Restaurant?{
        if(userResource?.getUserByUsername(ctx.userPrincipal.name)?.restaurant!! ==   restaurantResource?.getRestaurant(id)) {
            restaurantResource?.deleteRestaurant(id)
        }
    }

}