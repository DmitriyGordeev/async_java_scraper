import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashMap;

public class HtmlParser {

    public HashMap<String, String> parseFeed(String html) throws Exception {
        String baseUrl = "https://www.kommersant.ru/";
        Document doc = Jsoup.parse(html);
        Elements items = doc.select("body > main > div > div > " +
                "section:nth-child(1) > div.grid-col.grid-col-s3 > div.rubric_lenta");

        if (items.size() == 0) {
            throw new Exception("Couldn't find div.rubric_lenta");
        }

        HashMap<String, String> out = new HashMap<>();
        for (var item: items) {
            Elements articles = item.select("article");
            if (articles.size() == 0)
                throw new Exception("Couldn't find <article>");

            Element article = articles.get(0);
            String headline = article.attr("data-article-title");
            String link = article.select("a.uho__link").get(0).attr("href");
            out.put(baseUrl + link, headline);
        }
        return out;
    }


}
