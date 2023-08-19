package et.press.ebook.ui.details;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.google.android.material.chip.Chip;

import et.press.book.R;
import et.press.book.databinding.FragmentDetailsBinding;
import et.press.ebook.config.EpaConfig;
import et.press.ebook.models.EpaBook;
import et.press.ebook.ui.details.viewmodel.DetailsViewModel;
import et.press.ebook.ui.error.Error;

import java.util.List;

public class Details extends Fragment {
    private FragmentDetailsBinding binding;
    private DetailsViewModel viewModel;

    private String novelUrl;
    private Long novelId;

    public Details() {
        // Required
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            novelUrl = getArguments().getString(EpaConfig.KEY_NOVEL_URL);
            novelId = getArguments().getLong(EpaConfig.KEY_NOVEL_ID, -1L);
        }
        viewModel = new ViewModelProvider(requireActivity()).get(DetailsViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDetailsBinding.inflate(inflater, container, false);
        setUpListeners();
        setUpObservers();
        return binding.getRoot();
    }

    private void setUpListeners() {
        binding.readChapter.setOnClickListener(v -> navigateToChapterList());
        binding.progress.show();
    }

    private void setUpObservers() {
        viewModel.getError().observe(requireActivity(), this::setUpError);
        viewModel.details(novelUrl).observe(getViewLifecycleOwner(), this::setUpUi);
    }

    private void setUpError(String error) {
        binding.progress.hide();
        Error.navigateToErrorFragment(requireActivity(), error);
    }

    private void navigateToChapterList() {
        if (novelUrl == null) return;
        NavController controller = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main);
        Bundle bundle = new Bundle();
        bundle.putString(EpaConfig.KEY_NOVEL_URL, novelUrl);
        controller.navigate(R.id.details_fragment_to_chapters, bundle);
    }

    private void setUpUi(EpaBook epaBook) {
        Glide.with(binding.novelCover.getContext()).load(epaBook.coverUrl).into(binding.novelCover);
        binding.novelName.setText(epaBook.bookName);
        binding.rating.setRating(epaBook.version);
        binding.summary.setText(epaBook.summary);
        binding.status.setText(epaBook.status);
        addChips(epaBook.genres);

        if (epaBook.authors != null) {
            binding.authors.setText(String.join(", ", epaBook.authors));
        }
        if (epaBook.alternateNames != null) {
            binding.alternativeNames.setText(String.join(", ", epaBook.alternateNames));
        }
        if (epaBook.publishedYear > 0) {
            binding.year.setText(String.valueOf(epaBook.publishedYear));
        }

        binding.progress.hide();
    }

    private void addChips(List<String> genres) {
        if (genres == null) return;

        binding.genresLayout.removeAllViews();
        for (String genre : genres) {
            Chip chip = new Chip(binding.genresLayout.getContext());
            chip.setText(genre);
            binding.genresLayout.addView(chip);
        }
    }
}
