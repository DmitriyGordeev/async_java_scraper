import java.util.concurrent.*;


// this is just simple examples showing how to work with Callable, Future and CompletableFuture
public class AsyncExamples {

    public static void print(String value) {
        System.out.println(value);
    }


    public static void callableExample() {
        Callable<Long> c = () -> {
            long s = 0;
            for(int i = 0; i < 100000; i++) {
                for(int j = 0; j < 100000; j++) {
                    s += 1 + 2 * j;
                }
            }
            System.out.println("callable has finished");
            return s;
        };

        try {
            print("Starting loop, will take some time");
            long result = c.call();
            print("c.call() = " + result);
        }
        catch(Exception e) {e.printStackTrace();}

        System.out.println("callableExample() end");
    }


    public static void futureExample() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<Integer> future = executor.submit(() -> {
            long s = 0;
            for(var i = 0; i < 100000; i++) {
                for(var j = 0; j < 100000; j++) {
                    s += 1 + 2 * j;
                }
            }
            System.out.println("future has finished, s = " + s);
            return 1;
        });

        System.out.println("reaching this line while 'future' is still running");

        // Wait while future is Done
        while (!future.isDone()) {
            try {
                print("future.isDone() = " + future.isDone() + " , future.get() = " + future.get());
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }

        try { print("On done: future.get() = " + future.get() + ", future.isDone() = " + future.isDone()); }
        catch(Exception e) {e.printStackTrace();}
    }


    public static void completableFutureExample() {
        ExecutorService exec = Executors.newSingleThreadExecutor();

        CompletableFuture<Integer> f1 = CompletableFuture.supplyAsync(() -> {
            int s = 0;
            for(int i = 0; i < 100000; i++) {
                for(int j = 0; j < 100000; j++) {
                    s += i - j;
                }
            }
            System.out.println("completableFuture f1 has finished, s = " + s);
            return 0;
        }, exec);

        System.out.println("Reaching this line while 'future' f1 is still running, f1.isDone() = " + f1.isDone());

        // The main difference from a regular Future is that we can chain
        // functions and schedule some execution right after CompletableFuture isDone
        CompletableFuture<Long> f2 = f1.thenApply((input) -> {
            long s = 0;
            for(var i = 0; i < 100000; i++) {
                for(var j = 0; j < 100000; j++) {
                    s += i - j;
                }
            }
            return input - s;
        });

        System.out.println("Reaching this line while 'future' f2 is still running, f2.isDone() = " + f2.isDone());

        try {
            // completableFuture.get() will wait (block) until task is finished and then pass the execution further
            var result = f2.get();
            System.out.println("f2.get() = " + result);
        }
        catch(Exception e) {e.printStackTrace();}

        // both are true
        print("f1.isDone() = " + f1.isDone());
        print("f2.isDone() = " + f2.isDone());
    }

}
