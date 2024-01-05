package com.eclipse.restsebootstrap;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.SeBootstrap;
import java.io.IOException;
import jakarta.ws.rs.core.Application;
import java.util.Set;

/**
 * This class configures and starts the SeBootstrap. This is the embedded
 * server. It uses the class that extended Application, RestConfig, to define
 * the classes that contain web service methods.
 *
 * @author Ken Fogel
 */
@ApplicationPath("services") 
public class RestBootstrap extends Application {
    
    /**
     * With SeBootstrap you must supply a list of classes as a set, each 
     * containing a RESTful web service. When server based, such as GlassFish, 
     * the server will identify the services and so this is not used.
     * 
     * @return a set of all REST services classes
     */
    @Override
    public Set<Class<?>> getClasses() {
        return Set.of(CompoundInterestService.class);
    }

    public void startService() throws IOException, InterruptedException {
        SeBootstrap.Configuration configuration = SeBootstrap.Configuration.builder()
                .host("localhost")
                .port(8080)
                .protocol("http")
                .build();

        SeBootstrap.start(this, configuration);

        // Used when the service must run until you 'kill' the process
        Thread.currentThread().join();

        // Used when you want to end the service by pressing Enter rather than 'kill'.
        // System.out.println("Press Enter to end this process"); 
        // System.in.read();
    }

    public static void main(final String[] args) throws InterruptedException, IOException {
        new RestBootstrap().startService();
        System.exit(0);
    }
}
