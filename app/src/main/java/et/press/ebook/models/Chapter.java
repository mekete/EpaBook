package et.press.ebook.models;

import androidx.annotation.NonNull;
import androidx.room.PrimaryKey;

import et.press.ebook.util.SourceUtils;

public class Chapter {
    public float chapterUuid;
    public long bookUuid;
    public String chapterName;
    public String updated;
    @PrimaryKey
    @NonNull
    public String url;

    public Chapter() {
        this.url = "";
    }

    public Chapter(String novelUrl) {
        this.bookUuid = SourceUtils.generateId(novelUrl);
        this.url = "";
    }

    @NonNull
    @Override
    public String toString() {
        return "Chapter{" +
                "id=" + chapterUuid +
                ", name='" + chapterName + '\'' +
                ", updated='" + updated + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
