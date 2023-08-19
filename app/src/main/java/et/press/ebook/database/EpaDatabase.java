package et.press.ebook.database;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import et.press.ebook.App;
import et.press.ebook.config.EpaConfig;
import et.press.ebook.database.converters.ListConverter;
import et.press.ebook.database.dao.ChapterDao;
import et.press.ebook.database.dao.NovelDao;
import et.press.ebook.models.EpaChapter;
import et.press.ebook.models.EpaBook;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {EpaBook.class, EpaChapter.class}, version = EpaConfig.DATABASE_VERSION)
@TypeConverters({ListConverter.class})
public abstract class EpaDatabase extends RoomDatabase {
    public static final ExecutorService databaseExecutor = Executors.newSingleThreadExecutor();
    private static volatile EpaDatabase INSTANCE;

    public static EpaDatabase database() {
        if (INSTANCE == null) {
            synchronized (EpaDatabase.class) {
                if (INSTANCE != null) return INSTANCE;

                INSTANCE = Room.databaseBuilder(
                        App.getContext().getApplicationContext(),
                        EpaDatabase.class,
                        EpaConfig.DATABASE_NAME
                ).build();
            }
        }
        return INSTANCE;
    }

    // tables
    public abstract NovelDao novels();
    public abstract ChapterDao chapters();
}
