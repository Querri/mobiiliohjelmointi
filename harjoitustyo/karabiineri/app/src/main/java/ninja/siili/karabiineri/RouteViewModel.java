package ninja.siili.karabiineri;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;


public class RouteViewModel extends AndroidViewModel {
    private DataRepository mDataRepository;

    private LiveData<Route> mRouteLiveData;
    private LiveData<List<Route>> mAllRoutesLiveData;


    public RouteViewModel(@NonNull Application application) {
        super(application);
        mDataRepository = new DataRepository(application);
    }
}