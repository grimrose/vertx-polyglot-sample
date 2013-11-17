def appConfig = container.config

container.deployModule("org.crashub~vertx.shell~2.0.2", appConfig["crashConfig"])
container.deployModule('io.vertx~mod-mongo-persistor~2.0.0-final', appConfig["mongoPersistorConfig"])

container.deployVerticle(sample.SubscriberVerticle.name)
container.deployVerticle("groovy:sample.MongoStatsVerticle")
