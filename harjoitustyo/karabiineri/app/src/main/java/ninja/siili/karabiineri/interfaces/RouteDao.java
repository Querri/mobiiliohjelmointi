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
    LiveData<List<Route>> getRoutesInPlace(String placeid);

    @Query(("SELECT * FROM route_table WHERE mID LIKE :id"))
    LiveData<Route> getRouteWithId(String id);


    @Query(("UPDATE route_table SET mName = :name, mDiff = :diff, " +
            "mType = :type, mIsSitStart = :isSitStart, mIsTopOut = :isTopOut, " +
            "mNotes = :notes WHERE mID LIKE :id"))
    void update(String id, String name, int diff, String type,
                boolean isSitStart, boolean isTopOut, String notes);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Route route);

    @Query("DELETE FROM route_table")
    void deleteAll();
}
