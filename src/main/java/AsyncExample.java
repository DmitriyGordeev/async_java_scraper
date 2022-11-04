import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.FutureTask;

public class AsyncExample extends FutureTask {
    public AsyncExample(Callable callable) {
        super(callable);
    }
}
