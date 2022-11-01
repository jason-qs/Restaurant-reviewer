package org.acme.controller

import org.acme.entity.Review
import org.acme.service.ReviewService
import javax.annotation.security.PermitAll
import javax.annotation.security.RolesAllowed
import javax.inject.Inject
import javax.ws.rs.*
import javax.ws.rs.core.MediaType

@Path("/api/reviews")
class ReviewController {
    @Inject
    var reviewResource: ReviewService? = null

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun getReviews(): List<Review?>? {
        return reviewResource?.getReviews()
    }

    @GET
    @Path("/{id}")
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    fun getReview(@PathParam("id") id: Long?): Review? {
        return reviewResource?.getReview(id)
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed("admin")
    @Consumes(MediaType.APPLICATION_JSON)
    fun updateReview(@PathParam("id") id:Long?, review: Review?): Unit? {
        return reviewResource?.updateReview(id,review!!)
    }

    @POST
    @RolesAllowed("admin")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    fun addReview(review: Review?): Review? {
        return reviewResource?.addReview(review)
    }

    @DELETE
    @RolesAllowed("admin")
    @Path("/{id}")
    fun deleteReview(@PathParam("id") id: Long?) {
        reviewResource?.deleteReview(id)
    }

}