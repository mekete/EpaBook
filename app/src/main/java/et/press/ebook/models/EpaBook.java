package et.press.ebook.models;

import androidx.room.Entity;

import et.press.ebook.util.SourceUtils;

import java.util.List;

@Entity
public class EpaBook extends Book {

    public String summary;
    public List<String> alternateNames;
    public List<String> authors;
    public List<String> genres;
    public String status;//NotDownloaded, Downloaded,Opened,
    public float version;
    public int publishedYear;

    public EpaBook(String url) {
        super(url);
        this.bookUuid = SourceUtils.generateId(url);
        this.url = url;
    }

    public EpaBook(String contentUrl, String bookName, List<String> alternateNames, String summary, List<String> authors, List<String> genres, String status, float version, int publishedYear) {
        super(contentUrl);
        this.bookName = bookName;
        this.alternateNames = alternateNames;
        this.authors = authors;
        this.summary = summary;
        this.status = status;
        this.genres = genres;
        this.version = version;
        this.publishedYear = publishedYear;
    }


}
