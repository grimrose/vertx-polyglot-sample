package sample;

import org.vertx.java.core.json.JsonObject;
import org.vertx.java.core.logging.Logger;
import org.vertx.java.platform.Verticle;

public class PublisherVerticle extends Verticle {

    public void start() {

        final Logger logger = container.logger();

        logger.info("Verticle started.");

        JsonObject message = new JsonObject();
        message.putString("question", "ちゃうちゃう？");
        message.putString("user", "Java");

        logger.info(message);

        vertx.eventBus().publish("localhost", message);

        logger.info("Verticle Published.");
    }

}
