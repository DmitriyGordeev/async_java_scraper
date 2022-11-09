import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;



/**
 * This program is a quick cheat-sheet of using basic async programming in java
 *
 * The app parses news feed from kommersant.ru from specified url (news topic)
 * And performs async download and parsing of specific articles from specified page */


public class Main {

    public static void main(String[] args) {

        // specify news feed url to parse links from
        String rootUrl = "https://www.kommersant.ru/rubric/3";

        // Get html body with GET request
        // This is the same as synchronous behavior
        var future = SimpleHttpClient.asyncGET(rootUrl);
        String feedHtml = "";
        try {
            feedHtml = future.get();        // waits for the future to be finished
        }
        catch(InterruptedException | ExecutionException e) { e.printStackTrace(); }


        // parsing the feed page - getting urls leading to specific pages
        HtmlParser parser = new HtmlParser();
        HashMap<String, String> url2headline;
        try {
            url2headline = parser.parseFeed(feedHtml);
        }
        catch (Exception e) {
            e.printStackTrace();
            return;
        }


        System.out.println("Found " + url2headline.size() + " articles");
        System.out.println("Start parsing....");


        // Start async download of articles and then parse each of them
        ArrayList<CompletableFuture<Article>> futures = new ArrayList<>();
        for (String url : url2headline.keySet()) {

            // this future will download html of article and then will parse
            // the result into 'Article' object
            CompletableFuture<Article> f = SimpleHttpClient.asyncGET(url).thenApply((html) -> {
                Article article = null;
                try {
                    article = parser.parseArticle(html);
                    System.out.println("parsed Article: " + article);
                }
                catch(Exception e) { e.printStackTrace(); }
                return article;
            });
            futures.add(f);
        }

        // Wrap all futures into one
        CompletableFuture<Void> combinedFuture
                = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));

        try {
            // wait until all futures are finished
            // .get() blocks execution until future is finished
            combinedFuture.get();
            System.out.println("allOf() has finished");
        }
        catch(Exception e) { e.printStackTrace(); }

    }
}
