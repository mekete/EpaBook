package et.press.ebook.sources;

import et.press.ebook.models.EpaChapter;
import et.press.ebook.models.Chapter;
import et.press.ebook.models.DataSource;
import et.press.ebook.models.Filter;
import et.press.ebook.models.EpaBook;
import et.press.ebook.models.Book;

import java.util.List;

public interface Source {
    DataSource metadata();

    // get the list of novels based on page
    List<Book> novels(int page) throws Exception;

    // get all the fields for a single novel
    EpaBook details(String url) throws Exception;

    // get all chapters for a novel
    List<Chapter> chapters(String url) throws Exception;

    // get content of the chapter from the url
    EpaChapter chapter(String novelUrl, String chapterUrl, String chapterUuid) throws Exception;

    // search novels
    List<Book> search(Filter filters, int page) throws Exception;
}
