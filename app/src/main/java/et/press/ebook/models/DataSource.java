package et.press.ebook.models;

import androidx.annotation.NonNull;

public class DataSource {
    public int sourceId;
    public String jsonUrl;
    public String name;
    public String lang;
    public String logo;

    @NonNull
    @Override
    public String toString() {
        return "DataSource{" +
                "sourceId=" + sourceId +
                ", url='" + jsonUrl + '\'' +
                '}';
    }
}
