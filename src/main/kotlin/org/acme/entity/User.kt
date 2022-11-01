package org.acme.entity

import java.io.Serializable
import javax.persistence.*

@Table(name="users")
@Entity
class User : Serializable{
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