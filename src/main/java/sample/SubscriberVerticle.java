package sample;

import org.vertx.java.core.Handler;
import org.vertx.java.core.eventbus.Message;
import org.vertx.java.core.json.JsonObject;
import org.vertx.java.core.logging.Logger;
import org.vertx.java.platform.Verticle;

import java.text.MessageFormat;
import java.util.Date;

public class SubscriberVerticle extends Verticle {

    @Override
    public void start() {

        final Logger logger = container.logger();

        logger.info("subscriber started.");

        vertx.eventBus().registerHandler("localhost", new Handler<Message<JsonObject>>() {

            @Override
            public void handle(Message<JsonObject> message) {

                logger.info(MessageFormat.format("subscriber message received at {0}", new Date()));

                JsonObject question = message.body();

                logger.info("subscriber received: " + question.toString());

                JsonObject reply = new JsonObject();

                reply.putString("subscriber", "Java");
                reply.putObject("question", question);
                reply.putString("answer", "ちゃうちゃう");

                logger.info("subscriber reply: " + reply);

                message.reply(reply);

            }
        });

    }

}
