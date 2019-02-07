package ninja.siili.karabiineri;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.android.gms.maps.model.LatLng;

@Entity(tableName = "place_table")
public class Place {

    @PrimaryKey(autoGenerate = true)
    public int mID;

    public String mName;
    public String mDesc;
    public String mAccess;
    public LatLng mLocation;


    public Place(String name, String desc, String access, LatLng location) {
        mName = name;
        mDesc = desc;
        mAccess = access;
        mLocation = location;
    }


    public int getId() { return mID; }
    public String getName() { return mName; }
    public LatLng getLocation() { return mLocation; }
    public String getDesc() { return mDesc; }
    public String getAccess() { return mAccess; }

    public void setName(String name) { mName = name; }
    public void setLocation(LatLng location) { mLocation = location; }
    public void setDesc(String desc) { mDesc = desc; }
    public void setAccess(String access) {mAccess = access; }
}
