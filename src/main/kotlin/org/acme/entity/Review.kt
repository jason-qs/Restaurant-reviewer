package org.acme.entity

import java.io.Serializable
import javax.persistence.*

@Table(name = "reviews")
@Entity
class Review : Serializable {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    var id: Long? = null
    var content: String? = null
    var rating: Float? = null
    var restaurantId: Long? = null
    var userId: Long? = null

    constructor(id: Long?, content: String?, rating: Float?, restaurantId: Long?, UserId: Long?) {
        this.id = id
        this.content = content
        this.rating = rating
        this.restaurantId = restaurantId
        this.userId = userId
    }

    constructor() : super() {}
}