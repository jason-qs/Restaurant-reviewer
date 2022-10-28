package org.acme.controller

import io.quarkus.security.Authenticated
import io.quarkus.security.identity.SecurityIdentity
import org.acme.entity.Review
import org.acme.service.ReviewService
import org.acme.service.UserService
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
import javax.ws.rs.core.SecurityContext

@Path("/api/reviews")
class ReviewController {
    @Inject
    var reviewResource: ReviewService? = null

    @Inject
    var userResource: UserService? = null
    @GET
    @RolesAllowed("admin")
    @Produces(MediaType.APPLICATION_JSON)
    fun getReviews(): List<Review?>? {
        return reviewResource?.getReviews()
    }

    @GET
    @Path("/{id}")
    @RolesAllowed("admin")
    @Produces(MediaType.APPLICATION_JSON)
    fun getReview(@PathParam("id") id: Long?): Review? {
        return reviewResource?.getReview(id)
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed("admin")
    @Consumes(MediaType.APPLICATION_JSON)
    fun updateReview(@PathParam("id") id:Long?, review: Review?) {
        reviewResource?.updateReview(id,review!!)
    }

    @POST
    @RolesAllowed("admin", "user")
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

    @DELETE
    @RolesAllowed("user")
    @Path("/me/{id}")
    fun deleteUserReview(@PathParam("id") id: Long?, ctx: SecurityContext ): Review {
     if(userResource?.getUserByUsername(ctx.userPrincipal.name)?.restaurant!! ==  reviewResource.getReview(id)) {
         reviewResource?.deleteReview(id)
     }
    }
    @PUT
    @RolesAllowed("user")
    @Path("/me/{id}")
    fun updateUserReview(@PathParam("id") id: Long?, ctx: SecurityContext, review: Review? ): Review {
        if(userResource?.getUserByUsername(ctx.userPrincipal.name)?.restaurant!! ==  reviewResource.getReview(id)) {
            reviewResource?.updateReview(id,review!!)
        }
    }
}