package org.acme.entity

import io.quarkus.security.identity.SecurityIdentity
import org.jboss.resteasy.spi.touri.MappedBy
import java.io.Serializable
import javax.annotation.security.PermitAll
import javax.inject.Inject
import javax.persistence.*
import javax.print.attribute.standard.JobOriginatingUserName
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@Table(name="users")
@Entity
class User : Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null
    var firstName: String? = null
    var lastName: String? = null
    var userName: String = ""
    var password: String = ""
    var role: String? = null

    @OneToMany(cascade = [CascadeType.ALL])
    @JoinColumn(name = "userId")
    open var reviews: MutableSet<Review> = mutableSetOf()

    @OneToMany(cascade = [CascadeType.ALL])
    @JoinColumn(name = "userId")
    open var restaurant: MutableSet<Restaurant> = mutableSetOf()


    constructor(id: Long?, firstName: String?,
                lastName: String?, userName: String, password:String, role : String?) : super() {

        this.id = id
        this.firstName = firstName
        this.lastName = lastName
        this.userName = userName
        this.password = password
        this.role = role
    }
    constructor() : super() {}
}