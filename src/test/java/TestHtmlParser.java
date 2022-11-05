import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.ExecutionException;

public class TestHtmlParser {

    @Test
    void testSimpleFileReading() {
        // TODO: remove this test
        try {
            var r = Files.lines(Path.of("file.txt"), StandardCharsets.UTF_8);
            String text = String.join("\n", r.toList());
            int A = 100;
        }
        catch(IOException ioException) {
            ioException.printStackTrace();
        }
    }

    @Test
    void testSimpleFileWriting() throws IOException {
        // TODO: remove this test
        String str = "HelloW";
        FileOutputStream outputStream = new FileOutputStream("file_w.txt");
        byte[] strToBytes = str.getBytes();
        outputStream.write(strToBytes);
        outputStream.close();
    }


    @Test
    void testSimpleHttpRequest()  {
        // Simple example of async GET request
        var future = SimpleHttpClient.asyncGET("https://tradingeconomics.com/stream");
        System.out.println("[false] f.isDone() = " + future.isDone());

        try {
            var response = future.get();
            System.out.println("response: " + response);
        }
        catch(InterruptedException | ExecutionException e ) {
            e.printStackTrace();
        }
    }
}
