package ninja.siili.karabiineri;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

import ninja.siili.karabiineri.interfaces.PlaceDao;

public class PlaceRepository {
    private PlaceDao mPlaceDao;
    private LiveData<List<Place>> mAllPlaces;


    PlaceRepository(Application application) {
        mPlaceDao = AppDatabase.getDatabase(application).placeDao();
        mAllPlaces = mPlaceDao.getAllPlaces();
    }


    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    LiveData<List<Place>> getAllPlaces() {
        return mAllPlaces;
    }


    // must be called on a non-UI thread or the app will crash.
    void insert(Place place) {
        new insertAsyncTask(mPlaceDao).execute(place);
    }

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
