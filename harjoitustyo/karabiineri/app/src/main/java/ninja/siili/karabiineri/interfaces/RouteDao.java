package ninja.siili.karabiineri.interfaces;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import ninja.siili.karabiineri.Route;

@Dao
public interface RouteDao {

    @Query("SELECT * FROM route_table")
    LiveData<List<Route>> getAllRoutes();

    @Query(("SELECT * FROM route_table WHERE mPlaceID LIKE :placeid"))
    LiveData<List<Route>> getRoutesInPlace(int placeid);

    @Query(("SELECT * FROM route_table WHERE mID LIKE :id"))
    LiveData<Route> getRouteWithId(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Route route);

    @Query("DELETE FROM route_table")
    void deleteAll();
}
