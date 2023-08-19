package et.press.ebook.ui.explore;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import et.press.book.databinding.FragmentExploreBinding;
import et.press.ebook.config.EpaSettings;
import et.press.ebook.models.DataSource;
import et.press.ebook.sources.Source;
import et.press.ebook.sources.SourceManager;
import et.press.ebook.ui.explore.adapter.SourceAdapter;
import et.press.ebook.ui.main.MainActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Explore extends Fragment implements SourceAdapter.OnSourceSelected {
    private FragmentExploreBinding binding;

    public Explore() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentExploreBinding.inflate(inflater, container, false);
        binding.sourceList.setLayoutManager(new LinearLayoutManager(requireActivity()));
//        setSourcesListToUi();

        return binding.getRoot();
    }

//    private void setSourcesListToUi() {
//        HashMap<Integer, Class<?>> sources = SourceManager.getSources();
//        List<DataSource> dataSources = new ArrayList<>();
//        for (Integer id : sources.keySet()) {
//            Source src = SourceManager.getSource(id);
//            dataSources.add(src.metadata());
//        }
//        binding.sourceList.setAdapter(new SourceAdapter(dataSources, this));
//    }

    @Override
    public void select(DataSource source) {
        int oldSource = EpaSettings.get().getCurrentSource();
        int newSource = source.sourceId;

        if (oldSource != newSource) {
            Toast.makeText(requireActivity(), "Updating source to " + source.name, Toast.LENGTH_SHORT).show();
            EpaSettings.get().setCurrentSource(source.sourceId).save();
            restartApp();
        }
    }

    private void restartApp() {
        Intent intent = new Intent(requireContext(), MainActivity.class);
        requireActivity().startActivity(intent);
        requireActivity().finishActivity(101);
    }
}