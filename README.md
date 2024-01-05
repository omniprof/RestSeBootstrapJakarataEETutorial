Embedded REST server with SeBootstrap

The examples so far have shown REST services that will run on an application server such as GlassFish. There is an alternative to using such a heavyweight server and that is to use an embedded server that supports the REST API. This is the role of the SeBootstrap. The code for a service is unchanged from what is required for the application server. The difference is in using an embedded server.
You begin with a simple Maven or Gradle project. We will look at the Maven version. In whatever IDE you prefer, create a Maven based project for any type of desktop application such as a Console app, a JavaFX app, or Swing app. When we created a REST service on an application server the only dependency required in the Maven pom.xml file was:
<dependencies>
    <dependency>
        <groupId>jakarta.platform</groupId>
        <artifactId>jakarta.jakartaee-api</artifactId>
        <version>${jakartaee-api.version}</version>
        <scope>provided</scope>
    </dependency>
</dependencies>

To create a desktop/embedded REST service we must add the required dependencies for bot the server and services. Here are the basic dependencies:

<dependencies>
    <dependency>
        <groupId>org.glassfish.jersey.core</groupId>
        <artifactId>jersey-server</artifactId>
        <version>3.1.2</version>
    </dependency>
    <dependency>
        <groupId>org.glassfish.jersey.containers</groupId>
        <artifactId>jersey-container-jdk-http</artifactId>
        <version>3.1.2</version>
    </dependency>
    <dependency>
        <groupId>org.glassfish.jersey.inject</groupId>
        <artifactId>jersey-cdi2-se</artifactId>
        <version>3.1.2</version>
    </dependency>       
</dependencies>        

In this example we are using the Eclipse Jersey Framework. For more information on this Framework please visit https://eclipse-ee4j.github.io/jersey/. At the very minimum we need these three dependencies to place an embedded server in most any Java application.

To configure and start a server we need a class that extends the jakarta.ws.rs.core.Application class. Within this class you must override Application class’s getClasses method where you will list all classes that contain a REST service. 

@ApplicationPath("services")
public class RestBootstrap extends Application {
    
    @Override
    public Set<Class<?>> getClasses() {
        return Set.of(Service1.class, Service2.class);
    }

On an application server it is not necessary to extend Application and override getClasses as the server will find all services by looking for the @Path annotation. On an embedded server you must have a class that extends Application and lists all the services in the overridden getClasses method.

You are now ready to configure and start the embedded server SeBootstrap. Create a method that will handle this task:

public void startService() throws IOException {

    SeBootstrap.Configuration configuration = 
                SeBootstrap.Configuration.builder().build();

The Configuration allows you to configure certain aspects of the embedded server. If the defaults, see next example, must be overridden that you can use this second version:

    SeBootstrap.Configuration configuration = 
                SeBootstrap.Configuration.builder()
                .host("localhost")
                .port(8080)
                .protocol("http")
                .build();

Here we can set all or any of the host URL, the port number and protocol.

To run the embedded server, you just need to add:

    SeBootstrap.start(this, configuration);

The first parameter is a reference to an object that extends Application. This may be a separate class or, as in this example, the same class as it extends Application.

SeBootstrap is now running in its own thread. To prevent the thread from ending you can join the main thread to the SeBootstrap thread. To stop the embedded server, you will have to kill the process.
Thread.currentThread().join();

The code for the actual REST services is unchanged and CDI is supported in the embedded server.




