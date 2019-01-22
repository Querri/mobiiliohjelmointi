package ninja.siili.karabiineri;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

import ninja.siili.karabiineri.interfaces.PlaceDao;

public class PlaceRepository {
    private PlaceDao mPlaceDao;
    private LiveData<List<Place>> mAllPlaces;

    // Note that in order to unit test the WordRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    PlaceRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        mPlaceDao = db.placeDao();
        mAllPlaces = mPlaceDao.getAllPlaces();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    LiveData<List<Place>> getAllPlaces() {
        return mAllPlaces;
    }

    // You must call this on a non-UI thread or your app will crash.
    // Like this, Room ensures that you're not doing any long running operations on the main
    // thread, blocking the UI.
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
