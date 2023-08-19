package et.press.ebook.ui.reader;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import et.press.book.R;
import et.press.book.databinding.ActivityReaderBinding;
import et.press.ebook.config.EpaConfig;
import et.press.ebook.models.EpaChapter;
import et.press.ebook.models.Chapter;
import et.press.ebook.models.ReaderTheme;
import et.press.ebook.ui.reader.adapter.PageAdapter;
import et.press.ebook.ui.reader.sheet.CustomizeReader;
import et.press.ebook.ui.reader.viewmodel.ReaderViewModel;
import et.press.ebook.util.ListUtils;

import java.util.ArrayList;
import java.util.List;

public class ReaderActivity extends AppCompatActivity implements CustomizeReader.OnOptionSelection, Toolbar.OnMenuItemClickListener {
    private final List<EpaChapter> epaChapters = new ArrayList<>();
    private ActivityReaderBinding binding;
    private PageAdapter adapter;
    private ReaderViewModel viewModel;
    private List<Chapter> chapters = new ArrayList<>();
    private String currentChapterUrl;
    private String currentNovelUrl;
    private boolean isLoading = false;
    private int currentChapterIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityReaderBinding.inflate(getLayoutInflater());
        AppCompatDelegate.setDefaultNightMode(EpaConfig.getThemeMode(getApplicationContext()));
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(binding.getRoot());
        binding.customize.setOnMenuItemClickListener(this);

        currentNovelUrl = getIntent().getStringExtra(EpaConfig.KEY_NOVEL_URL);
        currentChapterUrl = getIntent().getStringExtra(EpaConfig.KEY_CHAPTER_URL);
        viewModel = new ViewModelProvider(this).get(ReaderViewModel.class);

        adapter = new PageAdapter(epaChapters);
        binding.pageList.setLayoutManager(new LinearLayoutManager(this));
        binding.pageList.setAdapter(adapter);
        binding.pageList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollVertically(1) && !isLoading) {
                    isLoading = true;
                    currentChapterIndex += 1;

                    if (currentChapterIndex < chapters.size()) {
                        binding.progress.show();
                        viewModel.chapter(currentNovelUrl, chapters.get(currentChapterIndex).url);
                    }
                }
            }
        });

        viewModel.getChapters().observe(this, this::setChapters);
        viewModel.getChapter().observe(this, this::setChapter);
        viewModel.getError().observe(this, this::setError);
        viewModel.chapters(currentNovelUrl,null);
    }

    private void setChapters(List<Chapter> items) {
        chapters = ListUtils.sortById(items);
        for (int i = 0; i < chapters.size(); i++) {
            if (chapters.get(i).url.equals(currentChapterUrl)) {
                currentChapterIndex = i;
                break;
            }
        }
        viewModel.chapter(currentNovelUrl, currentChapterUrl);
    }

    private void setUpCustomizeReader() {
        CustomizeReader sheet = new CustomizeReader(this);
        sheet.show(getSupportFragmentManager(), "customize-sheet");
    }

    private void setChapter(EpaChapter epaChapter) {
        isLoading = false;
        binding.progress.hide();
        epaChapter.chapterUuid = chapters.get(currentChapterIndex).chapterUuid;
        epaChapters.add(epaChapter);
        adapter.notifyItemInserted(epaChapters.size() - 1);
    }

    private void setError(String msg) {
        if (msg.length() == 0) return;
        Snackbar.make(binding.getRoot(), msg, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void setFontSize(float size) {
        EpaConfig.storeReaderFont(this, size);
        adapter.setFontSize(size);
        adapter.notifyItemRangeChanged(0, epaChapters.size());
    }

    @Override
    public void setReaderTheme(String themeName) {
        ReaderTheme theme = EpaConfig.themes.get(themeName);
        adapter.setTheme(theme);
        adapter.notifyItemRangeChanged(0, epaChapters.size());
        EpaConfig.storeReaderTheme(this, themeName);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.customize_settings) {
            setUpCustomizeReader();
            return true;
        }

        return false;
    }
}