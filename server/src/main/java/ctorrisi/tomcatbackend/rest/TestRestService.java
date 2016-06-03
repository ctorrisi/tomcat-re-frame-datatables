package ctorrisi.tomcatbackend.rest;

import com.google.gson.Gson;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/hello")
public class TestRestService {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String sayHello() {
        return "Hello";
    }

    @GET
    @Path("/hi")
    @Produces(MediaType.APPLICATION_JSON)
    public String getArray() {
        Gson gson = new Gson();
        String[] arr = new String[]{ "hi" , "there" };
        return gson.toJson(arr);
    }

    @GET
    @Path("/addOne/{message}")
    @Produces(MediaType.TEXT_PLAIN)
    public String getMessage(@PathParam("message") String message) {
        return String.valueOf(Integer.parseInt(message) + 1);
    }

}
