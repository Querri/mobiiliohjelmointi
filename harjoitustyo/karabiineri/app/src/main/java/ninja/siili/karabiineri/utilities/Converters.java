package ninja.siili.karabiineri.utilities;

import android.arch.persistence.room.TypeConverter;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class Converters {
    @TypeConverter
    public static LatLng fromStringToLatLng(String value) {
        Type locationType = new TypeToken<LatLng>() {}.getType();
        return new Gson().fromJson(value, locationType);
    }


    @TypeConverter
    public static String fromLatLngToString(LatLng location) {
        Gson gson = new Gson();
        String json = gson.toJson(location);
        return json;
    }
}
