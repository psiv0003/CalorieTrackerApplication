package poornima.com.calorietrackerapplication.Models;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao

public interface StepsDAO {

    @Query("SELECT * FROM steps")
        List<Steps> getAll();

    @Query("SELECT * FROM steps WHERE time LIKE :time LIMIT 1")
    Steps findByFirstandLastName( String time);

    @Query("SELECT * FROM steps WHERE stepsId = :stepsId LIMIT 1")
    Steps findByID(int stepsId);

    @Insert
    void insertAll(Steps... steps);

    @Insert
    long insert(Steps steps);

    @Delete
    void delete(Steps steps);

    @Update(onConflict = REPLACE)
    public void updateUsers(Steps... customers);

    @Query("DELETE FROM Steps")
    void deleteAll();

    @Query("DELETE FROM steps where stepsId = :stepsId")
    void delSteps(int stepsId);

}
