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


    DataRepository(Application application) {
        mPlaceDao = AppDatabase.getDatabase(application).placeDao();
        mRouteDao = AppDatabase.getDatabase(application).routeDao();
    }


    /** Initiate with all Places */
    public void init() {
        mAllPlacesLiveData = mPlaceDao.getAllPlaces();
    }


    /** Initiate with a Place with specific ID */
    public void init(int placeId) {
        mPlaceLiveData = mPlaceDao.getPlaceWithId(placeId);
        mAllRoutesInPlaceLiveData = mRouteDao.getRoutesInPlace(placeId);
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
}
