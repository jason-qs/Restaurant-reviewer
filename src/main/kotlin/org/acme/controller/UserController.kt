package org.acme.controller


import org.acme.config.Certification
import org.acme.config.Token
import org.acme.entity.Restaurant
import org.acme.entity.Review
import org.acme.entity.User
import org.acme.service.RestaurantService
import org.acme.service.ReviewService
import org.acme.service.UserService
import org.eclipse.microprofile.jwt.JsonWebToken
import javax.annotation.security.PermitAll
import javax.annotation.security.RolesAllowed
import javax.inject.Inject
import javax.ws.rs.*
import javax.ws.rs.core.Context
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response
import javax.ws.rs.core.SecurityContext

@Path("/api/users")
class UserController{
    @Inject
    var jwt: JsonWebToken? = null
    @Inject
    var token : Token? = null
    @Inject
    var userResource: UserService? = null
    @Inject
    var reviewResource: ReviewService? = null
    @Inject
    var restaurantResource: RestaurantService? = null

    @GET
    @Path("/test")
    @PermitAll
    fun getResponseString(@Context ctx: SecurityContext): String {
        val name: String
        if(ctx.userPrincipal == null) {
            name = "anon"
        } else if (!ctx.userPrincipal.name.equals(jwt?.name)) {
            throw InternalServerErrorException("names do not match")
        } else {
            name = ctx.userPrincipal.name
        }
        return name
    }

    @GET
    @Path("/test2")
    @RolesAllowed("user")
    @Produces(MediaType.TEXT_PLAIN)
    fun getOidcString(): String {
        return "Hello" + jwt.toString()
    }

    @POST
    @PermitAll
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    fun login(user: User): Any? {
        return if (userResource?.authenticate(user.userName,user.password) == true) {
            val token = token?.getToken(user)
            Response.ok(token).build()
        } else Response.status(Response.Status.BAD_REQUEST).entity("Invalid credentials!").build()
    }

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
    fun addUser( user: User): Response {
        val result = userResource?.addUser(user)
        return if (result != null) {
            Response .status(Response.Status.CREATED).entity("created $result").build()
        } else Response.status(Response.Status.BAD_REQUEST).entity("unable to create user!").build()
    }
    @GET
    @RolesAllowed("user")
    @Path("/me")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    fun getMe(@Context ctx: SecurityContext): User? {
        return userResource!!.getUserByUsername(ctx.userPrincipal.name)
    }


    @GET
    @RolesAllowed("user")
    @Path("/me/reviews")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    fun getMeReviews(@Context ctx: SecurityContext): MutableSet<Review> {
        return userResource!!.getUserByUsername(ctx.userPrincipal.name.toString())!!.reviews
    }

    @GET
    @RolesAllowed("user")
    @Path("/me/reviews/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    fun getMeReviewsIndex(@PathParam("id") id: Int?,@Context ctx: SecurityContext): Any {
       val reviews: MutableSet<Review> = userResource!!.getUserByUsername(ctx.userPrincipal.name)!!.reviews
        return reviews.elementAt(id!!)
    }

    @PUT
    @RolesAllowed("user")
    @Path("/me/reviews/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    fun updateMeReviewsIndex(@PathParam("id") id: Int?,@Context ctx: SecurityContext, review: Review): Unit? {
        val reviews: MutableSet<Review> = userResource!!.getUserByUsername(ctx.userPrincipal.name)!!.reviews
        val reviewToBeUpdated: Review = reviews.elementAt(id!!)
        return reviewResource?.updateReview(reviewToBeUpdated.id, review)
    }

    @DELETE
    @RolesAllowed("user")
    @Path("/me/reviews/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    fun deleteMeReviewsIndex(@PathParam("id") id: Int?,@Context ctx: SecurityContext): Any {
        val reviews: MutableSet<Review> = userResource!!.getUserByUsername(ctx.userPrincipal.name)!!.reviews
        val reviewToBeDeleted: Review = reviews.elementAt(id!!)
        return reviewResource!!.deleteReview(reviewToBeDeleted.id)
    }

    @POST
    @RolesAllowed("user")
    @Path("/me/reviews")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    fun createMeReviews(@Context ctx: SecurityContext,review: Review): Review? {
        val user: User= userResource!!.getUserByUsername(ctx.userPrincipal.name.toString())!!
        val reviewToBeCreated: Review = review
        reviewToBeCreated.userId= user.id
        return reviewResource?.addReview(reviewToBeCreated)
    }

    @GET
    @RolesAllowed("user")
    @Path("/me/restaurants/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    fun getMeRestaurantsIndex(@PathParam("id") id: Int?,@Context ctx: SecurityContext): Any {
        val restaurants: MutableSet<Restaurant> = userResource!!.getUserByUsername(ctx.userPrincipal.name)!!.restaurant
        return restaurants.elementAt(id!!)
    }

    @POST
    @RolesAllowed("user")
    @Path("/me/restaurants")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    fun createMeRestaurant(@Context ctx: SecurityContext,restaurant: Restaurant): Restaurant? {
        val user: User= userResource!!.getUserByUsername(ctx.userPrincipal.name)!!
        val restaurantToBeCreated: Restaurant = restaurant
        restaurantToBeCreated.userId= user.id
        return restaurantResource?.addRestaurant(restaurantToBeCreated)
    }

    @DELETE
    @RolesAllowed("user")
    @Path("/me/restaurants/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    fun deleteMeRestaurant(@PathParam("id") id : Long?,@Context ctx: SecurityContext) {
        val restaurants: MutableSet<Restaurant> = userResource!!.getUserByUsername(ctx.userPrincipal.name)!!.restaurant
        val restaurantToBeDeleted: Restaurant = restaurants.elementAt(id!!.toInt())
        return restaurantResource!!.deleteRestaurant(restaurantToBeDeleted.id)
    }

    @PUT
    @RolesAllowed("user")
    @Path("/me/restaurants/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    fun updateMeRestaurant(@PathParam("id") id : Long?,@Context ctx: SecurityContext,restaurant: Restaurant): Unit?{
        val restaurants: MutableSet<Restaurant> = userResource!!.getUserByUsername(ctx.userPrincipal.name)!!.restaurant
        val restaurantToBeUpdated: Restaurant = restaurants.elementAt(id!!.toInt())
        return restaurantResource?.updateRestaurant(restaurantToBeUpdated.id,restaurant)
    }

}