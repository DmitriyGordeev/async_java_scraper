import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;


public class TestHtmlParser {
    @Test
    void testParseFeed() throws Exception {
        String html = "";

        var r = Files.lines(Path.of("feed.html"), StandardCharsets.UTF_8);
        html = String.join("\n", r.toList());
        Assertions.assertTrue(html.length() > 0);

        // Parsing feed and extract urls to specific articles
        HtmlParser parser = new HtmlParser();
        var map = parser.parseFeed(html);
        Assertions.assertTrue(map.size() > 0);
        Assertions.assertTrue(map.containsKey("https://www.kommersant.ru//doc/5651441"));
        Assertions.assertTrue(map.containsKey("https://www.kommersant.ru//doc/5650907"));
        Assertions.assertTrue(map.containsKey("https://www.kommersant.ru//doc/5650596"));
    }


    @Test
    void testParseArticle() throws Exception {
        String html = "";
        var r = Files.lines(Path.of("article.html"), StandardCharsets.UTF_8);
        html = String.join("\n", r.toList());

        Assertions.assertTrue(html.length() > 0);

        // Parsing feed and extract urls to specific articles
        HtmlParser parser = new HtmlParser();
        var article = parser.parseArticle(html);

        Assertions.assertTrue(article.topic.length() > 0);
        Assertions.assertTrue(article.headline.length() > 0);
        Assertions.assertTrue(article.text.length() > 0);
        Assertions.assertNull(article.parsingErrors[0]);
        Assertions.assertNull(article.parsingErrors[1]);
    }



}
