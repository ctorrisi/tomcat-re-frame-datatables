package ctorrisi.tomcatbackend.app;

import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;

@ApplicationPath("rest")
public class RestApplication extends ResourceConfig {

    public RestApplication() {
        packages("au.lmal.rest;au.lmal.sse");
    }

}
