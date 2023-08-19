package et.press.ebook.ui.reader.sheet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import et.press.book.databinding.SheetCustomizeReaderBinding;
import et.press.ebook.App;
import et.press.ebook.config.EpaConfig;
import et.press.ebook.ui.reader.sheet.adapter.ReaderThemeAdapter;

public class CustomizeReader extends BottomSheetDialogFragment implements ReaderThemeAdapter.OnReaderThemeSelected {
    private final OnOptionSelection listener;

    public CustomizeReader(OnOptionSelection listener) {
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheetCustomizeReaderBinding binding = SheetCustomizeReaderBinding.inflate(inflater, container, false);

        binding.fontSlider.setValue(EpaConfig.getReaderFont(App.getContext()));
        binding.fontSlider.addOnChangeListener((slider, value, fromUser) -> listener.setFontSize(value));

        binding.readerThemeList.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.readerThemeList.setAdapter(new ReaderThemeAdapter(this));
        return binding.getRoot();
    }

    @Override
    public void select(String themeName) {
        listener.setReaderTheme(themeName);
    }

    public interface OnOptionSelection {
        void setFontSize(float size);

        void setReaderTheme(String themeName);
    }
}
