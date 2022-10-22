package org.acme.entity

import java.io.Serializable
import java.util.Locale.Category
import javax.persistence.*

@Table(name="restaurants")
@Entity
class  Restaurant : Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null;
    var name: String? = null
    var address: String? = null
    var category: String? = null


    constructor(id: Long?, name: String?, address: String?, category: String?) {
        this.id = id
        this.name = name
        this.address = address
        this.category = category
    }

    constructor() : super() {}
}