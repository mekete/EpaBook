package et.press.ebook.network.repository;

import et.press.ebook.config.EpaSettings;
import et.press.ebook.models.Book;
import et.press.ebook.models.Chapter;
import et.press.ebook.models.EpaChapter;
import et.press.ebook.models.EpaBook;
import et.press.ebook.models.Filter;
import et.press.ebook.sources.Source;
import et.press.ebook.sources.SourceManager;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Repository {
    private final Executor executor;
    private final Source source;

    public Repository() {
        this.executor = Executors.newCachedThreadPool();
        this.source = SourceManager.getSource(EpaSettings.get().getCurrentSource());
    }

    public Repository(int sourceId) {
        this.executor = Executors.newFixedThreadPool(1);
        this.source = SourceManager.getSource(sourceId);
    }

    public void novels(  Callback<List<Book>> callback) {
        executor.execute(() -> {
            try {
                List<Book> result = source.novels();
                callback.onComplete(result);
            } catch (Exception e) {
                callback.onError(e);
            }
        });
    }

    public void details(String novelUrl, Callback<EpaBook> callback) {
        executor.execute(() -> {
            try {
                EpaBook result = source.details(novelUrl);
                callback.onComplete(result);
            } catch (Exception e) {
                callback.onError(e);
            }
        });
    }

    public void chapters(String bookUrl, String chapterUrl, Callback<List<Chapter>> callback) {
        executor.execute(() -> {
            try {
                //TODO check this???
                String url=(chapterUrl!=null)?chapterUrl:bookUrl;
                List<Chapter> items = source.chapters(url);
                callback.onComplete(items);
            } catch (Exception e) {
                callback.onError(e);
            }
        });
    }

    public void chapter(String novelUrl, String chapterUrl, Callback<EpaChapter> callback) {
        executor.execute(() -> {
            try {
                EpaChapter item = source.chapter(novelUrl, chapterUrl,"");
                callback.onComplete(item);
            } catch (Exception e) {
                callback.onError(e);
            }
        });
    }

    public void search(Filter filter, int page, Callback<List<Book>> callback) {
        executor.execute(() -> {
            try {
                List<Book> items = source.search(filter, page);
                callback.onComplete(items);
            } catch (Exception e) {
                callback.onError(e);
            }
        });
    }

    public interface Callback<T> {
        void onComplete(T result);

        void onError(Exception e);
    }
}
