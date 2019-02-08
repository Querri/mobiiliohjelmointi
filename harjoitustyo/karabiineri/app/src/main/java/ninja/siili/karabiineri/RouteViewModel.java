package ninja.siili.karabiineri;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.widget.Toast;

import java.util.List;


public class RouteViewModel extends AndroidViewModel {
    private DataRepository mDataRepository;

    private LiveData<Route> mRouteLiveData;
    private LiveData<List<Route>> mAllRoutesLiveData;


    public RouteViewModel(@NonNull Application application) {
        super(application);
        mDataRepository = new DataRepository(application);
    }


    /** Initiate with all Routes in one Place **/
    public void init(String placeID) {
        mDataRepository.init(placeID);
        mAllRoutesLiveData = mDataRepository.getAllRoutesInPlaceLiveData();
    }


    public void init(String placeID, String routeID) {
        mDataRepository.init(placeID, routeID);
        mRouteLiveData = mDataRepository.getRouteInPlaceLiveData();
    }


    /** Get a list of the LiveDataObjects of the Routes in the initiated Place **/
    public LiveData<List<Route>> getAllRoutesLiveData() {
        return mAllRoutesLiveData;
    }


    public LiveData<Route> getRouteLiveData() { return mRouteLiveData; }


    /** Insert a new Route **/
    public void insertRoute(Route route) {
        mDataRepository.insertRoute(route);
    }
}