import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class Main {


    public static void main(String[] args) {

        String rootUrl = "https://www.kommersant.ru/rubric/3";

        var future = SimpleHttpClient.asyncGET(rootUrl);

        // getting the feed
        String feedHtml = "";
        try {
            feedHtml = future.get();        // waits for the future to be finished
        }
        catch(InterruptedException | ExecutionException e) { e.printStackTrace(); }


        // parsing the feed page
        HtmlParser parser = new HtmlParser();
        HashMap<String, String> url2headline = new HashMap<>();
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

            // this future will download html of article and will try parse
            // the result into Article object
            CompletableFuture<Article> f = SimpleHttpClient.asyncGET(url).thenApply((html) -> {
                System.out.println("this future has finished");
                Article article = null;
                try {
                    article = parser.parseArticle(html);
                    System.out.println(article);
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
