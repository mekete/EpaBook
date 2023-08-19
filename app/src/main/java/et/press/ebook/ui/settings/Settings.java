package et.press.ebook.ui.settings;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import et.press.book.R;
import et.press.book.databinding.FragmentSettingsBinding;
import et.press.ebook.config.EpaConfig;

public class Settings extends Fragment {

    private FragmentSettingsBinding binding;

    public Settings() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(inflater, container, false);

        binding.themeModeOption.setOnClickListener(v -> binding.themeModeView.setVisibility(getPolarVisibility(binding.themeModeView)));
        binding.lightChip.setOnClickListener(v -> selectTheme(AppCompatDelegate.MODE_NIGHT_NO));
        binding.nightChip.setOnClickListener(v -> selectTheme(AppCompatDelegate.MODE_NIGHT_YES));
        binding.autoChip.setOnClickListener(v -> selectTheme(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM));

        binding.musicPlayerLink.setOnClickListener(v -> openLink(EpaConfig.MP_LITE_GITHUB_LINK));
        binding.discordLink.setOnClickListener(v -> openLink(EpaConfig.DISCORD_INVITE_LINK));

        setCurrentThemeMode();

        return binding.getRoot();
    }

    private void setCurrentThemeMode() {
        int mode = EpaConfig.getThemeMode(requireActivity().getApplicationContext());
        if (mode == AppCompatDelegate.MODE_NIGHT_NO)
            binding.themeModeOption.setIcon(R.drawable.ic_theme_mode_light);
        else if (mode == AppCompatDelegate.MODE_NIGHT_YES)
            binding.themeModeOption.setIcon(R.drawable.ic_theme_mode_night);
        else
            binding.themeModeOption.setIcon(R.drawable.ic_theme_mode_auto);
    }

    private int getPolarVisibility(View view) {
        int mode = view.getVisibility();
        return mode == View.VISIBLE ? View.GONE : View.VISIBLE;
    }

    private void selectTheme(int theme) {
        AppCompatDelegate.setDefaultNightMode(theme);
        EpaConfig.storeThemeMode(requireActivity().getApplicationContext(), theme);
    }

    private void openLink(String url) {
        requireActivity().startActivity(new Intent(
                Intent.ACTION_VIEW,
                Uri.parse(url)
        ));
    }
}
