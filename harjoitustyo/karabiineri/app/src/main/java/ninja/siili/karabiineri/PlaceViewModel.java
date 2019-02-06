package ninja.siili.karabiineri;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;


public class PlaceViewModel extends AndroidViewModel {
    private PlaceRepository mPlaceRepository;

    private LiveData<Place> mPlaceLiveData;
    private LiveData<List<Place>> mAllPlacesLiveData;


    public PlaceViewModel(@NonNull Application application) {
        super(application);
        mPlaceRepository = new PlaceRepository(application);
    }


    /** Initiate with all Places */
    public void init() {
        mPlaceRepository.init();
        mAllPlacesLiveData = mPlaceRepository.getAllPlacesLiveData();
    }


    /** Initiate with a Place with specific ID */
    public void init(int placeId) {
        mPlaceRepository.init(placeId);
        mPlaceLiveData = mPlaceRepository.getPlaceLiveData();
    }


    /** Get a list of the LiveDataObjects of the initiated Places */
    public LiveData<List<Place>> getAllPlacesLiveData() {
        return mAllPlacesLiveData;
    }


    /** Get the LiveDataObject of the initiated Place */
    public LiveData<Place> getPlaceLiveData() {
        return mPlaceLiveData;
    }


    /** Insert a new Place */
    public void insert(Place place) {
        mPlaceRepository.insertPlace(place);
    }
}
