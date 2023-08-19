package et.press.ebook.ui.details.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import et.press.ebook.models.EpaBook;
import et.press.ebook.network.repository.Repository;

public class DetailsViewModel extends ViewModel {
    private MutableLiveData<String> error = new MutableLiveData<>();
    private MutableLiveData<EpaBook> details;
    private String oldUrl = "";

    public MutableLiveData<String> getError() {
        return error = new MutableLiveData<>();
    }

    public MutableLiveData<EpaBook> details(String novelUrl) {
        if (details != null && novelUrl.equals(oldUrl)) return details;

        oldUrl = novelUrl;
        details = new MutableLiveData<>();
        new Repository().details(novelUrl, new Repository.Callback<EpaBook>() {
            @Override
            public void onComplete(EpaBook result) {
                details.postValue(result);
            }

            @Override
            public void onError(Exception e) {
                error.postValue(e.getLocalizedMessage());
            }
        });
        return details;
    }
}
