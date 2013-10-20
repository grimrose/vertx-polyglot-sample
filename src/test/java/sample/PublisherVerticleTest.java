package sample;


import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.vertx.java.core.Handler;
import org.vertx.java.core.eventbus.Message;
import org.vertx.java.core.json.JsonObject;
import org.vertx.testtools.TestVerticle;
import org.vertx.testtools.VertxAssert;

import static org.vertx.testtools.VertxAssert.testComplete;

public class PublisherVerticleTest extends TestVerticle {

    @Test
    public void _expect_to_return_Json_Object() throws Exception {
        // Setup
        container.deployVerticle(PublisherVerticle.class.getName());
        // Verify
        vertx.eventBus().registerHandler("localhost", new Handler<Message<JsonObject>>() {

            @Override
            public void handle(Message<JsonObject> message) {
                JsonObject body = message.body();

                VertxAssert.assertThat(body.getString("question"), CoreMatchers.is("ちゃうちゃう？"));
                VertxAssert.assertThat(body.getString("user"), CoreMatchers.is("Java"));

                JsonObject reply = new JsonObject();
                reply.putString("message", "received!");
                message.reply(reply);

                testComplete();
            }

        });

    }

}
