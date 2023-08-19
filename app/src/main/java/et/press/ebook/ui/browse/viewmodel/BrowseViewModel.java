package et.press.ebook.ui.browse.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import et.press.ebook.models.Book;
import et.press.ebook.network.repository.Repository;

import java.util.ArrayList;
import java.util.List;

public class BrowseViewModel extends ViewModel {
    private MutableLiveData<String> error = new MutableLiveData<>();
    private MutableLiveData<List<Book>> items;

    public MutableLiveData<List<Book>> getNovels() {
        if (items == null) {
            items = new MutableLiveData<>();
        }
        return items;
    }

    public MutableLiveData<String> getError() {
        return error = new MutableLiveData<>();
    }

    public void novels() {

        new Repository().novels( new Repository.Callback<List<Book>>() {
            @Override
            public void onComplete(List<Book> result) {
                List<Book> old = items.getValue();
                if (old == null) {
                    old = new ArrayList<>();
                }
                old.addAll(result);
                items.postValue(old);
            }

            @Override
            public void onError(Exception e) {
                error.postValue(e.getLocalizedMessage());
            }
        });
    }
}
