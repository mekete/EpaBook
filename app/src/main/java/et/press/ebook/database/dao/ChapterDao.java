package et.press.ebook.database.dao;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import et.press.ebook.models.EpaChapter;

import java.util.List;

@Dao
public interface ChapterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void save(EpaChapter epaChapter);

    @Query("SELECT * FROM EpaChapter WHERE bookUuid=:bookUuid ORDER BY chapterUuid ASC")
    LiveData<List<EpaChapter>> list(long bookUuid);

    @Query("DELETE FROM EpaChapter WHERE url=:chapterUrl")
    int delete(String chapterUrl);

    @Query("SELECT * FROM EpaChapter WHERE url=:chapterUrl")
    LiveData<EpaChapter> get(String chapterUrl);
}
