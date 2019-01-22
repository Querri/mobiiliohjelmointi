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


    /**
     * Converter from integer to a readable route grade value.
     * @param value integer
     * @return String value
     */
    public static String fromIntToGrade(int value) {
        String number;
        String letter = "";

        // TODO more grades
        if (value < 9) {
            number = "4";
        } else if (value < 18) {
            number = "5";
        } else if (value < 27) {
            number = "6";
        } else {
            number = "7";
        }

        switch(value) {
            case 0: case 9: case 18: case 27:
                letter = "A-";
                break;
            case 1: case 10: case 19: case 28:
                letter = "A ";
                break;
            case 2: case 11: case 20: case 29:
                letter = "A+";
                break;
            case 3: case 12: case 21: case 30:
                letter = "B-";
                break;
            case 4: case 13: case 22: case 31:
                letter = "B ";
                break;
            case 5: case 14: case 23: case 32:
                letter = "B+";
                break;
            case 6: case 15: case 24: case 33:
                letter = "C-";
                break;
            case 7: case 16: case 25: case 34:
                letter = "C ";
                break;
            case 8: case 17: case 26: case 35:
                letter = "C+";
                break;
        }
        return number + letter;
    }
}
