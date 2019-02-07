package ninja.siili.karabiineri;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;

import java.util.UUID;

@Entity(tableName = "place_table")
public class Place {

    @NonNull
    @PrimaryKey
    public String mID;

    public String mName;
    public String mDesc;
    public String mAccess;
    public LatLng mLocation;


    public Place(String name, String desc, String access, LatLng location) {
        mID = UUID.randomUUID().toString();
        mName = name;
        mDesc = desc;
        mAccess = access;
        mLocation = location;
    }


    public String getId() { return mID; }
    public String getName() { return mName; }
    public LatLng getLocation() { return mLocation; }
    public String getDesc() { return mDesc; }
    public String getAccess() { return mAccess; }

    public void setName(String name) { mName = name; }
    public void setLocation(LatLng location) { mLocation = location; }
    public void setDesc(String desc) { mDesc = desc; }
    public void setAccess(String access) {mAccess = access; }
}
