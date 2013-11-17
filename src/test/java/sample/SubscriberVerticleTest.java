package sample;

import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.vertx.java.core.AsyncResult;
import org.vertx.java.core.Handler;
import org.vertx.java.core.eventbus.Message;
import org.vertx.java.core.json.JsonObject;
import org.vertx.testtools.TestVerticle;
import org.vertx.testtools.VertxAssert;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.is;
import static org.vertx.testtools.VertxAssert.assertThat;
import static org.vertx.testtools.VertxAssert.testComplete;

public class SubscriberVerticleTest extends TestVerticle {

    @Test
    public void _expect_to_received_Json_Object() throws Exception {
        // Setup
        container.deployVerticle(SubscriberVerticle.class.getName(), new Handler<AsyncResult<String>>() {
            @Override
            public void handle(AsyncResult<String> event) {
                final CountDownLatch latch = new CountDownLatch(1);

                VertxAssert.assertTrue(event.result(), event.succeeded());

                JsonObject message = new JsonObject();
                message.putString("user", "Java");
                message.putString("question", "test");

                vertx.eventBus().send("localhost", message, new Handler<Message<JsonObject>>() {

                    @Override
                    public void handle(Message<JsonObject> received) {
                        JsonObject reply = received.body();

                        container.logger().info("reply: " + reply);

                        VertxAssert.assertThat(reply.getString("subscriber"), CoreMatchers.is("Java"));
                        VertxAssert.assertThat(reply.getString("answer"), CoreMatchers.is("ちゃうちゃう"));

                        latch.countDown();

                        // Verify
                        try {
                            assertThat(latch.await(1, TimeUnit.SECONDS), is(true));
                        } catch (InterruptedException e) {
                            container.logger().error(e.getMessage(), e);
                        }

                        testComplete();
                    }
                });
            }
        });

    }

}
