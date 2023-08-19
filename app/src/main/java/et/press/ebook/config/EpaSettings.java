package et.press.ebook.config;

public class EpaSettings {
    private static EpaSettings INSTANCE = null;
    private int currentSource;

    private EpaSettings() {
        currentSource = EpaConfig.getCurrentSource();
    }

    public static EpaSettings get() {
        if (INSTANCE != null) return INSTANCE;
        return INSTANCE = new EpaSettings();
    }

    public void save() {
        EpaConfig.saveCurrentSource(currentSource);
    }

    public int getCurrentSource() {
        return currentSource;
    }

    public EpaSettings setCurrentSource(int sourceId) {
        currentSource = sourceId;
        return INSTANCE;
    }
}
