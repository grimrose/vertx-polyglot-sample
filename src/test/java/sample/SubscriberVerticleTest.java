package sample;

import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.vertx.java.core.Handler;
import org.vertx.java.core.eventbus.Message;
import org.vertx.java.core.json.JsonObject;
import org.vertx.testtools.TestVerticle;
import org.vertx.testtools.VertxAssert;

import static org.vertx.testtools.VertxAssert.testComplete;

public class SubscriberVerticleTest extends TestVerticle {

    @Test
    public void _expect_to_recieved_Json_Object() throws Exception {
        // Setup
        container.deployVerticle(SubscriberVerticle.class.getName());

        // perhaps bad practice :-(
        vertx.setTimer(1000L, new Handler<Long>() {
            @Override
            public void handle(Long timerID) {

                JsonObject message = new JsonObject();
                message.putString("user", "Java");
                message.putString("question", "test");

                vertx.eventBus().send("localhost", message, new Handler<Message<JsonObject>>() {

                    @Override
                    public void handle(Message<JsonObject> received) {
                        JsonObject reply = received.body();

                        container.logger().info(reply);

                        VertxAssert.assertThat(reply.getString("subscriber"), CoreMatchers.is("Java"));
                        VertxAssert.assertThat(reply.getString("answer"), CoreMatchers.is("ちゃうちゃう"));

                        testComplete();
                    }
                });
            }
        });
    }

}
