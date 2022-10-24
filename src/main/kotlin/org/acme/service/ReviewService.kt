package org.acme.service

import org.acme.entity.Review
import java.util.LongSummaryStatistics
import javax.inject.Inject
import javax.inject.Singleton
import javax.persistence.EntityManager
import javax.transaction.Transactional

@Singleton
class ReviewService {
    @Inject
    SecurityIdentity identiy;

    @Inject
    var entityManager: EntityManager? = null

    fun getReviews(): kotlin.collections.List<Review?>? {
        return entityManager!!.createQuery("SELECT c From Review c").resultList as List<Review?>?
    }

    fun getReview(id: Long?): Review {
        return  entityManager!!.find(Review::class.java, id)
    }


    @Transactional(Transactional.TxType.REQUIRED)
    fun addReview(review: Review?): Review? {
        entityManager!!.persist(review)
        return review
    }

    @Transactional(Transactional.TxType.REQUIRED)
    fun updateReview(id: Long?, review: Review) {
        val reviewToUpdate: Review = entityManager!!.
                find(Review::class.java, id)
        if( null!= reviewToUpdate) {
            reviewToUpdate.content = review.content
            reviewToUpdate.rating = review.rating
        } else  {
            throw RuntimeException("No such review available")
        }
    }

    @Transactional(Transactional.TxType.REQUIRED)
    fun deleteReview(id:Long?) {
        val review: Review = getReview(id)
        entityManager!!.remove(review)
    }

}
