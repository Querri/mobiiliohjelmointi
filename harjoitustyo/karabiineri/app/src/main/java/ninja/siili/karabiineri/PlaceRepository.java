package ninja.siili.karabiineri;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

import ninja.siili.karabiineri.interfaces.PlaceDao;


public class PlaceRepository {
    private PlaceDao mPlaceDao;

    private LiveData<List<Place>> mAllPlacesLiveData;
    private LiveData<Place> mPlaceLiveData;


    PlaceRepository(Application application) {
        mPlaceDao = AppDatabase.getDatabase(application).placeDao();
    }


    /** Initiate with all Places */
    public void init() {
        mAllPlacesLiveData = mPlaceDao.getAllPlaces();
    }


    /** Initiate with a Place with specific ID */
    public void init(int placeId) {
        mPlaceLiveData = mPlaceDao.getPlaceWithId(placeId);
    }


    /** Get a list of the LiveDataObjects of the initiated Places */
    LiveData<List<Place>> getAllPlacesLiveData() {
        return mAllPlacesLiveData;
    }


    /** Get the LiveDataObject of the initiated Place */
    LiveData<Place> getPlaceLiveData() {
        return mPlaceLiveData;
    }


    /** Insert a new Place */
    void insert(Place place) {
        // must be done on a non-UI thread or the app will crash.
        new insertAsyncTask(mPlaceDao).execute(place);
    }


    /** Asynchronous task for inserting a Place to database */
    private static class insertAsyncTask extends AsyncTask<Place, Void, Void> {
        private PlaceDao mAsyncTaskDao;

        insertAsyncTask(PlaceDao dao) {
            mAsyncTaskDao = dao;
        }


        @Override
        protected Void doInBackground(final Place... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
