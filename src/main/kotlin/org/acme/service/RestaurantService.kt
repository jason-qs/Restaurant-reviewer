package org.acme.service

import org.acme.entity.Restaurant
import javax.inject.Inject
import javax.inject.Singleton
import javax.persistence.EntityManager
import javax.persistence.Table
import javax.transaction.Transactional

@Singleton
class RestaurantService {
    @Inject
    var entityManager: EntityManager? = null

    fun getRestaurants(): kotlin.collections.List<Restaurant?>? {
        return entityManager!!.
        createQuery("Select c from Restaurant c").
        resultList as List<Restaurant?>?
    }

    fun getRestaurant(id: Long?): Restaurant {
        return  entityManager!!.find(Restaurant::class.java, id)
    }

    @Transactional(Transactional.TxType.REQUIRED)
    fun addRestaurant(restaurant: Restaurant?): Restaurant? {
        entityManager!!.persist(restaurant)
        return restaurant
    }

    @Transactional(Transactional.TxType.REQUIRED)
    fun updateRestaurant(id: Long?, restaurant: Restaurant) {
        val restaurantToUpdate: Restaurant = entityManager!!.
        find(Restaurant::class.java, id)
        if(null != restaurantToUpdate) {
            restaurantToUpdate.address = restaurant.address
            restaurantToUpdate.name = restaurant.address
        } else {
            throw RuntimeException("no such restaurant available")
        }
    }

    @Transactional(Transactional.TxType.REQUIRED)
    fun deleteRestaurant(id: Long?) {
        val restaurant: Restaurant = getRestaurant(id)
        entityManager!!.remove(restaurant)
    }
}