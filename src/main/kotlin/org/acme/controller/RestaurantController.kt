package org.acme.controller


import org.acme.entity.Restaurant
import org.acme.entity.Review
import org.acme.entity.User
import org.acme.service.RestaurantService
import org.acme.service.ReviewService
import org.acme.service.UserService
import javax.annotation.security.PermitAll
import javax.annotation.security.RolesAllowed

import javax.inject.Inject
import javax.ws.rs.*
import javax.ws.rs.core.Context
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.SecurityContext


@Path("/api/restaurants")
class RestaurantController {

    @Inject
    var userResource: UserService? = null
    @Inject
    var reviewResource: ReviewService? = null
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
    @RolesAllowed("admin")
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    fun updateRestaurant(@PathParam("id") id: Long?, restaurant: Restaurant?) {
        restaurantResource?.updateRestaurant(id, restaurant!!)
    }

    @POST
    @RolesAllowed("admin")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    fun addRestaurant(restaurant: Restaurant?): Restaurant? {
        return  restaurantResource?.addRestaurant(restaurant)
    }
    @POST
    @RolesAllowed("user"+"admin")
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    fun rateRestaurant(@Context ctx: SecurityContext, @PathParam("id") id: Long?, review: Review): Review? {
        val user: User = userResource!!.getUserByUsername(ctx.userPrincipal.name.toString())!!
        val reviewToBeCreated: Review = review
        reviewToBeCreated.userId= user.id
        reviewToBeCreated.restaurantId = id
        return reviewResource?.addReview(reviewToBeCreated)
    }

    @DELETE
    @RolesAllowed("admin")
    @Path("/{id}")
    fun deleteRestaurant(@PathParam("id") id: Long?): Unit? {
        return restaurantResource?.deleteRestaurant(id)
    }



}