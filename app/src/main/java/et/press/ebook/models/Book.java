package et.press.ebook.models;


import androidx.annotation.NonNull;
import androidx.room.PrimaryKey;

import et.press.ebook.util.SourceUtils;

public class Book {
    @PrimaryKey
    public long bookUuid;
//    public int sourceId;
    public String bookName;
    public String coverUrl;
    public String url;

    public Book(String url) {
        this.bookUuid = SourceUtils.generateId(url);
        this.url = url;
    }

    @NonNull
    @Override
    public String toString() {
        return "Book{" +
                "id=" + bookUuid +
//                ", sourceId=" + sourceId +
                ", name='" + bookName + '\'' +
                ", cover='" + coverUrl + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
