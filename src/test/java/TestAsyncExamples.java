import org.junit.jupiter.api.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.ExecutionException;


public class TestAsyncExamples {

    @Test
    void testCallableExample() {
        // This is synchronous
        AsyncExamples.callableExample();
    }


    @Test
    void testFutureExample() {
        AsyncExamples.futureExample();
    }


    @Test
    void testCompletableFuture() {
        AsyncExamples.completableFutureExample();
    }


    @Test
    void testSimpleHttpRequest()  {
        // Simple example of async GET request
        var future = SimpleHttpClient.asyncGET("https://www.kommersant.ru//doc/5651441");

        try {
            var response = future.get();        // waits for the future to be finished
            System.out.println("response: " + response);

            FileOutputStream outputStream = new FileOutputStream("article.html");
            byte[] strToBytes = response.getBytes();
            outputStream.write(strToBytes);
            outputStream.close();
        }
        catch(InterruptedException | ExecutionException | IOException e) {
            e.printStackTrace();
        }
    }


}
