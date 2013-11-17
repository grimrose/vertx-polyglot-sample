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

import static org.hamcrest.CoreMatchers.is;
import static org.vertx.testtools.VertxAssert.assertThat;
import static org.vertx.testtools.VertxAssert.testComplete;

public class PublishSubscribeTest extends TestVerticle {

    @Test
    public void _expect_to_publish_subscribe() throws Exception {

        // Setup
        container.deployVerticle(SubscriberVerticle.class.getName(), new Handler<AsyncResult<String>>() {
            @Override
            public void handle(AsyncResult<String> event) {
                final CountDownLatch latch = new CountDownLatch(1);

                VertxAssert.assertTrue(event.result(), event.succeeded());

                container.deployVerticle(PublisherVerticle.class.getName(), new Handler<AsyncResult<String>>() {
                    @Override
                    public void handle(AsyncResult<String> event) {

                        VertxAssert.assertTrue(event.result(), event.succeeded());

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
