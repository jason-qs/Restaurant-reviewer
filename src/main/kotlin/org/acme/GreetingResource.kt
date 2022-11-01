package org.acme

import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.core.Context
import javax.ws.rs.core.SecurityContext

@Path("/hello")
class GreetingResource {

    @GET
    fun hello() = Greeting("Hello")

    @GET
    @Path("/auth")
    fun greet(@Context ctx: SecurityContext) = "Hello User ... ${ctx.userPrincipal.name}"
}