import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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




}
