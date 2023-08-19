package et.press.ebook.services.download;

import et.press.ebook.database.EpaDatabase;
import et.press.ebook.models.Chapter;
import et.press.ebook.models.EpaChapter;
import et.press.ebook.network.repository.Repository;

import java.util.List;

public class DownloadManager {
    private final DownloadStatusListener listener;
    private final String novelUrl;
    private final int sourceId;

    public DownloadManager(int sourceId, String novelUrl, DownloadStatusListener listener) {
        this.listener = listener;
        this.novelUrl = novelUrl;
        this.sourceId = sourceId;
    }

    public void start() {
        new Repository(sourceId).chapters(novelUrl, null, new Repository.Callback<List<Chapter>>() {
            @Override
            public void onComplete(List<Chapter> result) {
                downloadChapters(result);
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
                listener.error(e.getLocalizedMessage());
            }
        });
    }

    private void downloadChapters(List<Chapter> items) {
        int max = items.size();
        int current = 0;
        Repository repository = new Repository(sourceId);

        for (int i = 0; i < items.size(); i++) {
            Chapter item = items.get(i);
            current += 1;

            int finalCurrent = current;
            repository.chapter(novelUrl, item.url, new Repository.Callback<EpaChapter>() {
                @Override
                public void onComplete(EpaChapter epaChapter) {
                    epaChapter.chapterName = item.chapterName;
                    epaChapter.updated = item.updated;
                    epaChapter.chapterUuid = item.chapterUuid;
                    EpaDatabase.databaseExecutor.execute(() -> {
                        EpaDatabase.database().chapters().save(epaChapter);
                        listener.complete(finalCurrent, max);
                    });
                }

                @Override
                public void onError(Exception e) {
                    e.printStackTrace();
                    listener.error(e.getLocalizedMessage());
                }
            });
        }
    }

    public interface DownloadStatusListener {
        void complete(int current, int max);

        void error(String error);
    }
}
