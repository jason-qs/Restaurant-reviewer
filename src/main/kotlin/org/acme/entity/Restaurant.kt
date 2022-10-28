package org.acme.entity

import java.io.Serializable
import java.util.Locale.Category
import javax.persistence.*
import javax.transaction.Transactional
import kotlin.jvm.Transient

@Table(name="restaurants")
@Entity
class Restaurant: Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null;
    var name: String? = null
    var address: String? = null
    var category: String? = null
    var userId: Long? = null

    @OneToMany(cascade = [CascadeType.ALL])
    @JoinColumn(name = "restaurantId")
    open var reviews: MutableSet<Review> = mutableSetOf()

    constructor(id: Long?, name: String?, address: String?, category: String?, userId: Long?) {
        this.id = id
        this.name = name
        this.address = address
        this.category = category
        this.userId = userId
    }

    constructor() : super() {}
}