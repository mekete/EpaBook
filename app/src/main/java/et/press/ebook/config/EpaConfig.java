package et.press.ebook.config;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatDelegate;

import et.press.ebook.App;
import et.press.ebook.models.ReaderTheme;

import java.util.LinkedHashMap;
import java.util.Map;

public class EpaConfig {
    public static final String DEBUG = "epa-debug";
    public static final String PACKAGE_NAME = "org.epa.ebook";
    public static final String SETTINGS_THEME_MODE = "shared_pref_theme_mode";
    public static final String SETTINGS_READER_THEME = "shared_pref_reader_theme";
    public static final String SETTINGS_READER_FONT = "shared_pref_reader_font";
    public static final String SETTING_SELECTED_SOURCE = "shared_pref_selected_source";

    public static final String KEY_SOURCE_ID = "key_source_id";
    public static final String KEY_NOVEL_URL = "key_novel_url";
    public static final String KEY_NOVEL_ID = "key_novel_id";
    public static final String KEY_CHAPTER_URL = "key_chapter_url";

    public static final String NOTIF_DOWNLOAD_CHANNEL_NAME = "download_notification";

    public static final String MP_LITE_GITHUB_LINK = "https://github.com/AP-Atul/music_player_lite";
    public static final String DISCORD_INVITE_LINK = "https://discord.gg/6CQ6u64dca";

    // database configs
    public static final String DATABASE_NAME = "epa_database";
    public static final int DATABASE_VERSION = 1;

    public static final String[] SILLY_EMOJI = new String[]{
            "( ╥﹏╥) ノシ",
            "༼ つ ◕_◕ ༽つ",
            "(❍ᴥ❍ʋ)",
            "(⊙＿⊙')",
            "(=____=)"
    };


    public static class Language {
        public static final String ENGLISH = "en-us";
        public static final String AMHARIC = "am-ET";
        public static final String OROMIFA = "or-ET";
        public static final String TIGRIGNA = "sm-ET";
    }
    public static final Map<String, ReaderTheme> themes = new LinkedHashMap<String, ReaderTheme>() {{
        put("default", null);
        put("light", new ReaderTheme("#202124", "#FFFFFF"));
        put("dark", new ReaderTheme("#E8EAED", "#202124"));
        put("warm", new ReaderTheme("#6E4D2E", "#F7DFC6"));
    }};

    private static SharedPreferences.Editor getEditor(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                EpaConfig.PACKAGE_NAME, Context.MODE_PRIVATE
        );
        return sharedPreferences.edit();
    }

    private static SharedPreferences getSharedPref(Context context) {
        return context.getSharedPreferences(
                EpaConfig.PACKAGE_NAME, Context.MODE_PRIVATE
        );
    }

    public static void storeThemeMode(Context context, int theme) {
        getEditor(context).putInt(EpaConfig.SETTINGS_THEME_MODE, theme).apply();
    }

    public static int getThemeMode(Context context) {
        return getSharedPref(context).getInt(EpaConfig.SETTINGS_THEME_MODE, AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
    }

    public static void storeReaderTheme(Context context, String theme) {
        getEditor(context).putString(EpaConfig.SETTINGS_READER_THEME, theme).apply();
    }

    public static String getReaderTheme(Context context) {
        return getSharedPref(context).getString(EpaConfig.SETTINGS_READER_THEME, "default");
    }

    public static void storeReaderFont(Context context, Float size) {
        getEditor(context).putFloat(EpaConfig.SETTINGS_READER_FONT, size).apply();
    }

    public static Float getReaderFont(Context context) {
        return getSharedPref(context).getFloat(EpaConfig.SETTINGS_READER_FONT, 15);
    }

    public static void saveCurrentSource(int sourceId) {
        getEditor(App.getContext()).putInt(EpaConfig.SETTING_SELECTED_SOURCE, sourceId).apply();
    }

    public static int getCurrentSource() {
        return getSharedPref(App.getContext()).getInt(EpaConfig.SETTING_SELECTED_SOURCE, 0);
    }
}
