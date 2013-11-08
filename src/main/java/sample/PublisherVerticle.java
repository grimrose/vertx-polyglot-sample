package sample;

import org.vertx.java.core.Handler;
import org.vertx.java.core.eventbus.Message;
import org.vertx.java.core.json.JsonObject;
import org.vertx.java.core.logging.Logger;
import org.vertx.java.platform.Verticle;

public class PublisherVerticle extends Verticle {

    public void start() {

        final Logger logger = container.logger();

        logger.info("publisher started.");

        JsonObject message = new JsonObject();
        message.putString("question", "ちゃうちゃう？");
        message.putString("user", "Java");

        logger.info("publisher publish: " + message.toString());

        vertx.eventBus().send("localhost", message, new Handler<Message<JsonObject>>() {

            @Override
            public void handle(Message<JsonObject> message) {
                JsonObject reply = message.body();
                logger.info("publisher reply: " + reply.toString());
                logger.info("publisher received.");
            }

        });

        logger.info("publisher published.");
    }

}
