import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
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
    void testParseFeed() {
        String html = "";
        try {
            var r = Files.lines(Path.of("feed.html"), StandardCharsets.UTF_8);
            html = String.join("\n", r.toList());
        }
        catch(IOException ioException) {
            ioException.printStackTrace();
        }


        // Parsing feed and extract urls to specific articles
        HtmlParser parser = new HtmlParser();
        try {
            var map = parser.parseFeed(html);
            int A = 1000;
        }
        catch(Exception e) { e.printStackTrace(); }

    }





}
