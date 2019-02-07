package ninja.siili.karabiineri.interfaces;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import ninja.siili.karabiineri.Place;

@Dao
public interface PlaceDao {

    @Query("SELECT * FROM place_table")
    LiveData<List<Place>> getAllPlaces();

    @Query(("SELECT * FROM place_table WHERE mID LIKE :id"))
    LiveData<Place> getPlaceWithId(String id);

    @Query(("SELECT * FROM place_table WHERE mID LIKE :name"))
    LiveData<Place> getPlaceWithName(String name);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Place place);

    @Query("DELETE FROM place_table")
    void deleteAll();
}
