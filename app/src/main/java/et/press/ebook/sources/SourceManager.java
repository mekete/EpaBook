package et.press.ebook.sources;

public class SourceManager {
    public static Source getSource(int sourceId) {
        return new EpaSource();

    }

//    public static HashMap<Integer, Class<?>> getSources() {
//        HashMap<Integer, Class<?>> sources = new HashMap<>();
//        sources.put(0, EpaSource.class);
//
//        return sources;
//    }
}
