package et.press.ebook.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;

import et.press.ebook.util.SourceUtils;

@Entity
public class EpaChapter extends Chapter {
    public String content;
    public int reads;

    public EpaChapter() {
        super();
    }

    public EpaChapter(String novelUrl) {
        super(novelUrl);
        this.bookUuid = SourceUtils.generateId(novelUrl);
    }

    @NonNull
    @Override
    public String toString() {
        return "EpaChapter{" +
                "content='" + content + '\'' +
                ", id=" + chapterUuid +
                ", name='" + chapterName + '\'' +
                ", updated='" + updated + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
