
public class Article {
    public String topic = "";
    public String url = "";
    public String headline = "";
    public String text = "";
    public String[] parsingErrors;

    public Article() {
        parsingErrors = new String[2];
    }
}
