package ninja.siili.karabiineri;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

import ninja.siili.karabiineri.interfaces.PlaceDao;
import ninja.siili.karabiineri.interfaces.RouteDao;


public class DataRepository {
    private PlaceDao mPlaceDao;
    private RouteDao mRouteDao;

    private LiveData<List<Place>> mAllPlacesLiveData;
    private LiveData<Place> mPlaceLiveData;

    private LiveData<List<Route>> mAllRoutesLiveData;
    private LiveData<List<Route>> mAllRoutesInPlaceLiveData;
    private LiveData<Route> mRouteInPlace;


    DataRepository(Application application) {
        mPlaceDao = AppDatabase.getDatabase(application).placeDao();
        mRouteDao = AppDatabase.getDatabase(application).routeDao();
    }


    /** Initiate with all Places */
    public void init() {
        mAllPlacesLiveData = mPlaceDao.getAllPlaces();
    }


    /** Initiate with a Place with specific ID */
    public void init(String placeId) {
        mPlaceLiveData = mPlaceDao.getPlaceWithId(placeId);
        mAllRoutesInPlaceLiveData = mRouteDao.getRoutesInPlace(placeId);
    }


    public void init(String placeID, String routeID) {
        mPlaceLiveData = mPlaceDao.getPlaceWithId(placeID);
        mRouteInPlace = mRouteDao.getRouteWithId(routeID);
    }


    /** Get a list of the LiveDataObjects of the initiated Places */
    LiveData<List<Place>> getAllPlacesLiveData() {
        return mAllPlacesLiveData;
    }


    /** Get the LiveDataObject of the initiated Place */
    LiveData<Place> getPlaceLiveData() {
        return mPlaceLiveData;
    }


    LiveData<List<Route>> getAllRoutesInPlaceLiveData() {
        return mAllRoutesInPlaceLiveData;
    }


    LiveData<Route> getRouteInPlaceLiveData() { return mRouteInPlace; }


    void updateRoute(String id, String name, int diff, String type,
                     boolean isSitStart, boolean isTopOut, String notes) {
        new updateRouteAsyncTask(mRouteDao).execute(id, name, diff, type, isSitStart, isTopOut, notes);
    }



    // TODO combine
    /** Insert a new Place */
    void insertPlace(Place place) {
        // must be done on a non-UI thread or the app will crash.
        new insertPlaceAsyncTask(mPlaceDao).execute(place);
    }


    /** Insert a new Route **/
    void insertRoute(Route route) {
        new insertRouteAsyncTask(mRouteDao).execute(route);
    }


    /** Asynchronous task for inserting a Place to database */
    private static class insertPlaceAsyncTask extends AsyncTask<Place, Void, Void> {
        private PlaceDao mAsyncTaskDao;

        insertPlaceAsyncTask(PlaceDao dao) {
            mAsyncTaskDao = dao;
        }


        @Override
        protected Void doInBackground(final Place... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }


    /** Asynchronous task for inserting a Route to database */
    private static class insertRouteAsyncTask extends AsyncTask<Route, Void, Void> {
        private RouteDao mAsyncTaskDao;

        insertRouteAsyncTask(RouteDao dao) {
            mAsyncTaskDao = dao;
        }


        @Override
        protected Void doInBackground(final Route... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }


    public static class updateRouteAsyncTask extends AsyncTask<Object, Void, Void> {
        private RouteDao mAsyncTaskDao;

        updateRouteAsyncTask(RouteDao dao) { mAsyncTaskDao = dao; }


        @Override
        protected Void doInBackground(final Object... params) {
            mAsyncTaskDao.update((String) params[0], (String) params[1], (int) params[2],
                    (String) params[3], (boolean) params[4], (boolean) params[5], (String) params[6]);
            return null;
        }
    }


    public void deleteAllPlaces() {
        mPlaceDao.deleteAll();
    }
}
