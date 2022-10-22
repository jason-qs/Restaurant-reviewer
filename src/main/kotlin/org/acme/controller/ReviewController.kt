package org.acme.controller

import io.quarkus.security.Authenticated
import org.acme.entity.Review
import org.acme.service.ReviewService
import javax.annotation.security.PermitAll
import javax.annotation.security.RolesAllowed
import javax.inject.Inject
import javax.ws.rs.Consumes
import javax.ws.rs.DELETE
import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.PUT
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@Path("/api/reviews")
@Authenticated
class ReviewController {
    @Inject
    var reviewResource: ReviewService? = null

    @GET
    @RolesAllowed("admin")
    @Produces(MediaType.APPLICATION_JSON)
    fun getReviews(): List<Review?>? {
        return reviewResource?.getReviews()
    }

    @GET
    @PermitAll
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    fun getReview(@PathParam("id") id: Long?): Review? {
        return reviewResource?.getReview(id)
    }

    @PUT
    @RolesAllowed("user")
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    fun updateReview(@PathParam("id") id:Long?, review: Review?) {
        reviewResource?.updateReview(id,review!!)
    }

    @POST
    @RolesAllowed("user")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    fun addReview(review: Review?): Review? {
        return reviewResource?.addReview(review)
    }

    @DELETE
    @RolesAllowed("user")
    @Path("/{id}")
    fun deleteReview(@PathParam("id") id: Long?) {
        reviewResource?.deleteReview(id)
    }
}