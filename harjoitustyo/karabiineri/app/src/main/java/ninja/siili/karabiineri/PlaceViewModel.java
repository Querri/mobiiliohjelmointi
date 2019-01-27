package ninja.siili.karabiineri;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import ninja.siili.karabiineri.interfaces.PlaceDao;


public class PlaceViewModel extends AndroidViewModel {

    private PlaceDao mPlaceDao;

    private LiveData<Place> mPlaceLiveData;
    private LiveData<List<Place>> mAllPlacesLiveData;


    public PlaceViewModel(@NonNull Application application) {
        super(application);
        mPlaceDao = AppDatabase.getDatabase(application).placeDao();
    }


    /** Initiate with all Places */
    public void init() {
        mAllPlacesLiveData = mPlaceDao.getAllPlaces();
    }


    /** Initiate with a Place with spesific ID */
    public void init(int placeId) {
        mPlaceLiveData = mPlaceDao.getPlaceWithId(placeId);
    }


    /** Get a list of the LiveDataObjects of the initiated Places */
    public LiveData<List<Place>> getAllPlacesLiveData() {
        return mAllPlacesLiveData;
    }


    /** Get the LiveDataObject of the initiated Place */
    public LiveData<Place> getPlaceLiveData() {
        return mPlaceLiveData;
    }


    public void insert(Place place) {
        mPlaceDao.insert(place);
    }
}
