package sample

import org.vertx.groovy.platform.Verticle
import org.vertx.java.core.logging.Logger

class MongoStatsVerticle extends Verticle {

    @Override
    def start() {
        container.logger.info("mongo stats started.");

        vertx.eventBus.registerHandler('mongo.ping') { message ->

            def command = [
                    "action": "command",
                    "command": "{ ping: 1 }"
            ]
            vertx.eventBus.send('vertx.mongopersistor', command) { reply ->
                container.logger.info "mongo stats is ${reply.body}"
                message.reply "mongo ping return: ${reply.body}".toString()
            }

        }

    }

}
