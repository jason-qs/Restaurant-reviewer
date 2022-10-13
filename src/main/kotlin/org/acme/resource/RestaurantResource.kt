package org.acme.resource

import org.acme.Greeting
import javax.ws.rs.DELETE
import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.PUT
import javax.ws.rs.Path

@Path("/restaurants")
class RestaurantResource {
    //TODO Get all restaurants, address, and
    @GET
    fun getAll() = Greeting("Here is the list of all the restaurants")
}

@Path("/restaurant/{id}")
class RestaurantByIdResource {
    //TODO Add Functionality to add restaurant by id
    @GET
    fun GetById()= Greeting("Restaurant By ID")
    //TODO Add Updated by Id Functionality
    @PUT
    fun updateById() = Greeting("Update Restaurant By ID")
    //TODO Add POST by ID Functionality
    @POST
    fun addById()= Greeting("Added restaurant")
    //TODO Add Delete by ID Functionality
    @DELETE
    fun deleteById() = Greeting("deleted by Id")
}