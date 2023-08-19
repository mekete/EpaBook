package et.press.ebook.database.dao;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import et.press.ebook.models.EpaBook;

import java.util.List;

@Dao
public interface NovelDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void save(EpaBook epaBook);

    @Query("SELECT * FROM EpaBook ORDER BY bookName ASC")
    LiveData<List<EpaBook>> list();

    @Query("DELETE FROM EpaBook WHERE bookUuid=:id")
    int delete(long id);

    @Query("SELECT * FROM EpaBook WHERE bookUuid=:id")
    LiveData<EpaBook> get(long id);
}
