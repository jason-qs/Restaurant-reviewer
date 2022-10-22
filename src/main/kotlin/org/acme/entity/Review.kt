package org.acme.entity

import java.io.Serializable
import javax.persistence.*

@Table(name="restaurant")
@Entity
class Review : Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null
    var content: String? = null
    var rating: Float? = null

    constructor(id: Long?, content: String?, rating: Float?) {
        this.id = id
        this.content = content
        this.rating = rating
    }

    constructor() : super() {}
}