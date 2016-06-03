package ctorrisi.tomcatbackend.sse;

import ctorrisi.tomcatbackend.entity.Person;
import com.google.gson.Gson;
import org.glassfish.jersey.media.sse.EventOutput;
import org.glassfish.jersey.media.sse.OutboundEvent;
import org.glassfish.jersey.media.sse.SseBroadcaster;
import org.glassfish.jersey.media.sse.SseFeature;

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Singleton
@Path("events")
public class TestSse {

    private final SseBroadcaster broadcaster = new SseBroadcaster();

    private List<Person> people = new ArrayList<>();

    public TestSse() {
        people.add(new Person("Mark", 20));
        people.add(new Person("Jim", 25));
        people.add(new Person("Tim", 38));
        people.add(new Person("Jan", 26));
        people.add(new Person("John", 35));
        people.add(new Person("Debby", 40));
        people.add(new Person("Sam", 33));
        people.add(new Person("Brad", 41));
        people.add(new Person("Tom", 36));

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    OutboundEvent.Builder builder = new OutboundEvent.Builder();
                    OutboundEvent outboundEvent = builder.name("message")
                            .mediaType(MediaType.TEXT_PLAIN_TYPE)
                            .data(String.class, String.valueOf(System.currentTimeMillis()))
                            .id(UUID.randomUUID().toString())
                            .build();
                    broadcaster.broadcast(outboundEvent);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        /* ignore */
                    }
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {

                while (true) {
                    OutboundEvent.Builder builder = new OutboundEvent.Builder();
                    OutboundEvent outboundEvent = builder.name("people")
                            .mediaType(MediaType.TEXT_PLAIN_TYPE)
                            .data(String.class, new Gson().toJson(people.toArray(new Person[people.size()])))
                            .id(UUID.randomUUID().toString())
                            .build();
                    broadcaster.broadcast(outboundEvent);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        /* ignore */
                    }
                    people.stream().forEach(Person::incrementAge);
                }
            }
        }).start();
    }

    @GET
    @Produces(SseFeature.SERVER_SENT_EVENTS)
    public EventOutput getServerSentEvents() throws IOException {
        final EventOutput eventOutput = new EventOutput();
        broadcaster.add(eventOutput);
        OutboundEvent outboundEvent = new OutboundEvent.Builder().name("people")
                .mediaType(MediaType.TEXT_PLAIN_TYPE)
                .data(String.class, new Gson().toJson(people.toArray(new Person[people.size()])))
                .id(UUID.randomUUID().toString())
                .build();
        eventOutput.write(outboundEvent);
        return eventOutput;
    }
}
