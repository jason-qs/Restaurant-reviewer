package org.acme.controller

import io.quarkus.security.Authenticated
import org.acme.entity.Restaurant
import org.acme.service.RestaurantService
import javax.annotation.security.PermitAll
import javax.annotation.security.RolesAllowed

import javax.inject.Inject
import javax.ws.rs.*
import javax.ws.rs.core.MediaType

@Path("/api/restaurants")
@Authenticated
class RestaurantController {
    @Inject
    var restaurantResource: RestaurantService? = null

    @GET
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    fun getRestaurants(): List<Restaurant?>? {
        return restaurantResource?.getRestaurants()
    }

    @GET
    @PermitAll
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    fun getRestaurant(@PathParam("id") id: Long?): Restaurant? {
        return  restaurantResource?.getRestaurant(id)
    }

    @PUT
    @RolesAllowed("owner","admin")
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    fun updateRestaurant(@PathParam("id") id: Long?, restaurant: Restaurant?) {
        restaurantResource?.updateRestaurant(id, restaurant!!)
    }

    @POST
    @RolesAllowed("owner","admin")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    fun addRestaurant(restaurant: Restaurant?): Restaurant? {
        return  restaurantResource?.addRestaurant(restaurant)
    }

    @DELETE
    @RolesAllowed("owner","admin")
    @Path("/{id}")
    fun deleteRestaurant(@PathParam("id") id: Long?) {
        restaurantResource?.deleteRestaurant(id)
    }

 }