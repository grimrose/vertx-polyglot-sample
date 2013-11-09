var vertx = require('vertx')
var console = require('vertx/console')

console.log("publisher started.");

var message= {
    "question": "ちゃうちゃう？",
    "user": "JavaScript"
};

console.log("publisher publish: " + JSON.stringify(message));

vertx.eventBus.send("localhost-js", message, function(reply) {
    console.log("publisher reply: " + JSON.stringify(reply));
    console.log("publisher received.");
});

console.log("publisher published.");
