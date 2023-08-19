package et.press.ebook.ui.chapters.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import et.press.ebook.models.Chapter;
import et.press.ebook.models.EpaChapter;
import et.press.ebook.network.repository.Repository;

import java.util.List;

public class ChaptersViewModel extends ViewModel {
    private MutableLiveData<String> error = new MutableLiveData<>();
    private MutableLiveData<List<Chapter>> chapters;
    private String oldUrl = "";

    public MutableLiveData<String> getError() {
        return error = new MutableLiveData<>();
    }

    public MutableLiveData<List<Chapter>> getChapters(String novelUrl) {
        if (chapters == null || !oldUrl.equals(novelUrl)) {
            oldUrl = novelUrl;
            chapters = new MutableLiveData<>();
        }
        return chapters;
    }

    public void chapters(String novelUrl) {
        new Repository().chapters(novelUrl, null, new Repository.Callback<List<Chapter>>() {
            @Override
            public void onComplete(List<Chapter> result) {
                chapters.postValue(result);
            }

            @Override
            public void onError(Exception e) {
                error.postValue(e.getLocalizedMessage());
            }
        } );
    }

    public MutableLiveData<EpaChapter> chapter(String novelUrl, String chapterUrl) {
        MutableLiveData<EpaChapter> chapter = new MutableLiveData<>();
        new Repository().chapter(novelUrl, chapterUrl, new Repository.Callback<EpaChapter>() {
            @Override
            public void onComplete(EpaChapter result) {
                chapter.postValue(result);
            }

            @Override
            public void onError(Exception e) {
                error.postValue(e.getLocalizedMessage());
            }
        });
        return chapter;
    }
}
