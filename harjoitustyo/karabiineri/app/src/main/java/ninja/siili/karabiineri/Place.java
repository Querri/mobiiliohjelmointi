package ninja.siili.karabiineri;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.location.Location;
import android.support.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;

@Entity(tableName = "place_table")
public class Place {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int mID;

    @ColumnInfo(name = "location")
    public LatLng mLocation;

    @ColumnInfo(name = "name")
    public String mName;

    @ColumnInfo(name = "desc")
    public String mDesc;

    @ColumnInfo(name = "access")
    public String mAccess;


    public Place(String name, String desc, String access, LatLng location) {
        setName(name);
        setDesc(desc);
        setLocation(location);
        setAccess(access);
}


    public int getId() { return mID; }
    public String getName() { return mName; }
    public LatLng getLocation() { return mLocation; }
    public String getDesc() { return mDesc; }

    public void setName(String name) { mName = name; }
    public void setLocation(LatLng location) { mLocation = location; }
    public void setDesc(String desc) { mDesc = desc; }
    public void setAccess(String access) {mAccess = access; }
}
