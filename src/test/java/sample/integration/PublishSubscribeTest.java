package sample.integration;

import org.junit.Test;
import org.vertx.java.core.AsyncResult;
import org.vertx.java.core.Handler;
import org.vertx.testtools.TestVerticle;
import org.vertx.testtools.VertxAssert;
import sample.PublisherVerticle;
import sample.SubscriberVerticle;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.vertx.testtools.VertxAssert.testComplete;

public class PublishSubscribeTest extends TestVerticle {

    @Test
    public void _expect_to_publish_subscribe() throws Exception {

        final CountDownLatch latch = new CountDownLatch(1);
        // Setup
        container.deployVerticle(PublisherVerticle.class.getName());
        // Verify
        container.deployVerticle(SubscriberVerticle.class.getName(), new Handler<AsyncResult<String>>() {
            @Override
            public void handle(AsyncResult<String> event) {
                latch.countDown();
                VertxAssert.assertTrue(event.result(), event.succeeded());
                testComplete();
            }
        });

        latch.await(5, TimeUnit.SECONDS);
    }

}
