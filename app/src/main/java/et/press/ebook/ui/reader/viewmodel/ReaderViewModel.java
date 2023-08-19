package et.press.ebook.ui.reader.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import et.press.ebook.models.Chapter;
import et.press.ebook.models.EpaChapter;
import et.press.ebook.network.repository.Repository;

import java.util.List;

public class ReaderViewModel extends ViewModel {
    private MutableLiveData<EpaChapter> chapter;
    private MutableLiveData<List<Chapter>> chapters;
    private MutableLiveData<String> error;

    public MutableLiveData<EpaChapter> getChapter() {
        if (chapter == null) {
            chapter = new MutableLiveData<>();
        }
        return chapter;
    }

    public MutableLiveData<List<Chapter>> getChapters() {
        if (chapters == null) {
            chapters = new MutableLiveData<>();
        }
        return chapters;
    }

    public MutableLiveData<String> getError() {
        return error = new MutableLiveData<>();
    }

    public void chapter(String bookUrl, String chapterUrl) {
        new Repository().chapter(bookUrl, chapterUrl, new Repository.Callback<EpaChapter>() {
            @Override
            public void onComplete(EpaChapter result) {
                chapter.postValue(result);
            }

            @Override
            public void onError(Exception e) {
                error.postValue(e.getLocalizedMessage());
            }
        });
    }

    public void chapters(String bookUrl, String chapterUrl ) {
        new Repository().chapters(bookUrl, chapterUrl , new Repository.Callback<List<Chapter>>() {
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
}
