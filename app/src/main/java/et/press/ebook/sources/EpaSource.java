package et.press.ebook.sources;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import et.press.ebook.models.Book;
import et.press.ebook.models.Chapter;
import et.press.ebook.models.DataSource;
import et.press.ebook.models.EpaBook;
import et.press.ebook.models.EpaChapter;
import et.press.ebook.models.Filter;
import et.press.ebook.network.HttpClient;
import et.press.ebook.util.NumberUtils;
import et.press.ebook.util.SourceUtils;

public class EpaSource implements Source {

    @Override
    public DataSource metadata() {
        DataSource source = new DataSource();
        source.jsonUrl = "https://ranobe-org.github.io/";
        //We may add some fields if necessary like tokenUrl....
        source.name = "Some name";
        return source;
    }

    @Override
    public List<Book> novels() throws Exception {
        List<Book> items = new ArrayList<>();


        EpaBook epaBook = new EpaBook("https://ranobe-org.github.io/level-unknown", "What is your epaBook?", "https://github.com/ranobe-org/level-unknown/raw/main/cover.jpg","");
        items.add(epaBook);

        EpaBook epaBook1 = new EpaBook("https://ranobe-org.github.io/elevator", "Elevator", "https://github.com/ranobe-org/elevator/raw/main/cover.jpg","");
        items.add(epaBook1);

        return items;
    }

    @Override
    public EpaBook details(String url) throws IOException {
        Element doc = Jsoup.parse(HttpClient.GET(url, new HashMap<>()));

        String bookName = doc.select("h1.menu-title").text().trim();
        String coverUrl = doc.select("img").attr("src").trim();
        String summary = doc.select("h2#summary").nextAll().select("p").text();
        EpaBook epaBook = new EpaBook(url, bookName, coverUrl,summary);

        for (Element element : doc.select("main > ul > li")) {
            String value = element.text().trim();

            if (value.contains("Alternative")) {
                epaBook.alternateNames = Arrays.asList(value.replace("Alternative Names:", "").trim().split(","));
            } else if (value.contains("Author")) {
                epaBook.authors = Arrays.asList(value.replace("Author:", "").trim().split(","));
            } else if (value.contains("Genre")) {
                epaBook.genres = Arrays.asList(value.replace("Genre:", "").trim().split(","));
            } else if (value.contains("Year")) {
                epaBook.publishedYear = NumberUtils.toInt(value.replace("Year:", "").trim());
            } else if (value.contains("Status")) {
                epaBook.status = value.replace("Status:", "").trim();
            }
        }

        return epaBook;
    }


    @Override
    public List<Chapter> chapters(String url) throws IOException {
        List<Chapter> items = new ArrayList<>();
        Element doc = Jsoup.parse(HttpClient.GET(url, new HashMap<>()));

        for (Element element : doc.select("li.chapter-item")) {
            if (element.select("a").hasClass("active")) {
                continue;
            }

            Chapter item = new Chapter(url);

            item.url = url.concat("/").concat(element.select("a").attr("href").trim());
            String tagName = element.select("a").text();
            item.chapterName = tagName.substring(tagName.indexOf("C"));
            item.chapterUuid = NumberUtils.toFloat(item.chapterName);
            items.add(item);
        }

        return items;
    }

    @Override
    public EpaChapter chapter(String novelUrl, String chapterUrl, String chapterUuid) throws IOException {
        EpaChapter epaChapter = new EpaChapter(novelUrl);
        Element doc = Jsoup.parse(HttpClient.GET(chapterUrl, new HashMap<>()));
        Element main = doc.select("main").first();

        if (main == null) {
            return null;
        }

        epaChapter.url = chapterUrl;
        epaChapter.content = "";
        main.select("p").append("::");
        main.select("h1").append("::");
        epaChapter.content = SourceUtils.cleanContent(main.text().replaceAll("::", "\n\n").trim());

        return epaChapter;
    }

    @Override
    public List<Book> search(Filter filters, int page) throws Exception {
        throw new Exception("Not Implemented. Has no results!");
    }
}
