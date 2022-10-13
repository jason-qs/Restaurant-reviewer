package org.acme.resource

import org.acme.Greeting
import javax.ws.rs.POST
import javax.ws.rs.PUT
import javax.ws.rs.Path

@Path("/owner")
class OwnerResource {
    //TODO Add Register Functionality
    @POST
    fun register() = Greeting("You are registered")
    //TODO Add login functionality
    @PUT
    fun login(username:String) = Greeting("Hello $username welcome to your account")
}