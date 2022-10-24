package org.acme.entity

import org.jboss.resteasy.spi.touri.MappedBy
import java.io.Serializable
import javax.persistence.*

@Table(name="users")
@Entity
class User : Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null
    var firstName: String? = null
    var lastName: String? = null
    var emailId: String? = null

    @OneToMany(cascade = [CascadeType.ALL])
    @JoinColumn(name = "restaurantId")
    open var reviews: MutableSet<Review> = mutableSetOf()

    constructor(id: Long?, firstName: String?,
                lastName: String?, emailId: String?) : super() {

        this.id = id
        this.firstName = firstName
        this.lastName = lastName
        this.emailId = emailId
    }
    constructor() : super() {}
}