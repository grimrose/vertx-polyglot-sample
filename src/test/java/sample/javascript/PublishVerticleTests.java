package sample.javascript;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.vertx.testtools.ScriptClassRunner;
import org.vertx.testtools.TestVerticleInfo;

@TestVerticleInfo(filenameFilter="publishVerticleTest\\.js", funcRegex="function[\\s]+(test[^\\s(]+)")
@RunWith(ScriptClassRunner.class)
public class PublishVerticleTests {
    @Test
    public void __vertxDummy() {
    }
}
