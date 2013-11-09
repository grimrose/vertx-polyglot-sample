var container = require("vertx/container")
var vertx = require("vertx");
var vertxTests = require("vertx_tests");
var vertxAssert = require("vertx_assert");

var console = require('vertx/console')

function test_expect_to_return_Json_Object() {

    container.deployVerticle("publish_verticle.js", function (error, deploymentID) {
        vertxAssert.assertNull(error);
    });

    vertx.eventBus.registerHandler("localhost-js", function(message, replier) {

        vertxAssert.assertEquals(message.question, "ちゃうちゃう？");
        vertxAssert.assertEquals(message.user, "JavaScript");

        var reply = {
            "message": "received!"
        };

        replier(reply);


        vertxAssert.testComplete();

    });

}

vertxTests.startTests(this);
