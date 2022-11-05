import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

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

        HttpURLConnection connection = null;
        try {
            //Create connection
            URL url = new URL("https://tradingeconomics.com/stream");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "text/html");
            connection.setRequestProperty("User-Agent",
                            "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 " +
                            "(KHTML, like Gecko) Chrome/44.0.2403.157 Safari/537.36");

            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
            String line;
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\n');
            }
            rd.close();
            System.out.println(response);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }


    }
}
