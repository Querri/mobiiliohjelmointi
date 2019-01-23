package ninja.siili.karabiineri;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

public class PlaceViewModel extends AndroidViewModel {
    private PlaceRepository mRepository;
    private LiveData<List<Place>> mAllPlaces;


    public PlaceViewModel(Application application) {
        super(application);
        mRepository = new PlaceRepository(application);
        mAllPlaces = mRepository.getAllPlaces();
    }


    /** Return all places in database */
    public LiveData<List<Place>> getAllPlaces() {
        return mAllPlaces;
    }


    /** Insert a new place into database */
    public void insert(Place place) {
        mRepository.insert(place);
    }
}
