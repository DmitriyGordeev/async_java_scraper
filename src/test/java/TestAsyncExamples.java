import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLParameters;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Authenticator;
import java.net.CookieHandler;
import java.net.ProxySelector;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;

public class TestAsyncExamples {

    @Test
    void testCallableExample() {
        // This is synchronous
        Main.callableExample();
    }


    @Test
    void testFutureExample() {
        Main.futureExample();
    }


    @Test
    void testCompletableFuture() {
        Main.completableFutureExample();
    }


    @Test
    void testSimpleHttpRequest()  {
        // Simple example of async GET request
        var future = SimpleHttpClient.asyncGET("https://www.kommersant.ru/rubric/3");

        try {
            var response = future.get();
            System.out.println("response: " + response);

            FileOutputStream outputStream = new FileOutputStream("feed.html");
            byte[] strToBytes = response.getBytes();
            outputStream.write(strToBytes);
            outputStream.close();
        }
        catch(InterruptedException | ExecutionException | IOException e) {
            e.printStackTrace();
        }
    }


}
