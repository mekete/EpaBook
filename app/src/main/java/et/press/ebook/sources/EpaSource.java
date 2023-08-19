package et.press.ebook.sources;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import et.press.ebook.config.EpaConfig;
import et.press.ebook.models.Book;
import et.press.ebook.models.Chapter;
import et.press.ebook.models.EpaChapter;
import et.press.ebook.models.DataSource;
import et.press.ebook.models.EpaBook;
import et.press.ebook.models.Filter;
import et.press.ebook.network.HttpClient;
import et.press.ebook.util.NumberUtils;
import et.press.ebook.util.SourceUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class EpaSource implements Source {

    @Override
    public DataSource metadata() {
        DataSource source = new DataSource();
         source.jsonUrl = "https://ranobe-org.github.io/";
        source.name = "EpaConfig Originals";
        source.lang = EpaConfig.Language.ENGLISH;
        source.logo = "https://ranobe-org.github.io/.github/tiny.png";
        return source;
    }

    @Override
    public List<Book> novels(int page) throws Exception {
        List<Book> items = new ArrayList<>();
        if (page > 1) {
            throw new Exception("well");
        }

        EpaBook epaBook = new EpaBook("https://ranobe-org.github.io/level-unknown");
        epaBook.bookName = "What is your epaBook?";
        epaBook.coverUrl = "https://github.com/ranobe-org/level-unknown/raw/main/cover.jpg";
//        epaBook.sourceId = sourceId;
        items.add(epaBook);

        EpaBook epaBook1 = new EpaBook("https://ranobe-org.github.io/elevator");
        epaBook1.bookName = "Elevator";
        epaBook1.coverUrl = "https://github.com/ranobe-org/elevator/raw/main/cover.jpg";
//        epaBook1.sourceId = sourceId;
        items.add(epaBook1);

        return items;
    }

    @Override
    public EpaBook details(String url) throws IOException {
        EpaBook epaBook = new EpaBook(url);
        Element doc = Jsoup.parse(HttpClient.GET(url, new HashMap<>()));

//        epaBook.sourceId = sourceId;
        epaBook.bookName = doc.select("h1.menu-title").text().trim();
        epaBook.coverUrl = doc.select("img").attr("src").trim();
        epaBook.summary = doc.select("h2#summary").nextAll().select("p").text();
        epaBook.version = 0;

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
