import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {
    public static void print(String value) {
        System.out.println(value);
    }


    public static void callableExample() {
        Callable<Integer> c = () -> {
            int s = 0;
            for(int i = 0; i < 100000; i++) {
                for(int j = 0; j < 100000; j++) {
                    s += 1 + 2 * j;
                }
            }
            System.out.println("callable has finished");
            return s;
        };

        try {
            int s = c.call();
            print("s = " + s);
        }
        catch(Exception e) {e.printStackTrace();}

        System.out.println("callableExample() end");
    }


    public static void futureExample() {
        ExecutorService executor = Executors.newFixedThreadPool(1);
        Future<Integer> future = executor.submit(() -> {
            int s = 0;
            for(int i = 0; i < 100000; i++) {
                for(int j = 0; j < 100000; j++) {
                    s += 1 + 2 * j;
                }
            }
            System.out.println("future has finished, s = " + s);
            return 1;
        });

        System.out.println("futureExample() end");

        // Wait while future is Done
        while (!future.isDone()) {
            int A = 1000;
            try {
                print("future.isDone() = " + future.isDone() + " , future.get() = " + future.get());
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }

        try { print("AFTER: " + future.get() + ", future.isDone() = " + future.isDone()); }
        catch(Exception e) {e.printStackTrace();}
    }


    public static void main(String[] args) {
        callableExample();
//        futureExample();
    }
}
