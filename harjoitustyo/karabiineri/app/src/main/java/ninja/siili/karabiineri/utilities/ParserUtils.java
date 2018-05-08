package ninja.siili.karabiineri.utilities;

import android.content.Context;
import android.util.JsonToken;
import android.widget.Toast;

import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

/**
 * Utilities for parsing JSON.
 */
public abstract class ParserUtils {

    /**
     * Calculates and returns the count of JSONObjects in the JSONArray.
     * @param context   Context for the action.
     * @return          Count of places.
     */
    public static int getPlacesCount(Context context) {
        JSONArray places = getJSONArray(context);
        int count = 0;

        if (places != null) {
            for (Object place : places) {
                if (place instanceof JSONObject) {
                    count++;
                }
            }
        }
        return count;
    }


    /**
     * Returns names of the used key values.
     * @return  An array containing keys.
     */
    public static String[] getKeys() {
        String[] keys = {"title", "place_type", "routes_type", "routes_diff_start",
                "routes_diff_end", "desc", "notes", "parking_paid"};
        return keys;
    }


    /**
     * Used to get a value corresponding to given fieldName in JSONObject with the given id.
     * @param context   App's context for finding the Asset file
     * @param id        ID identifies the place and is used to find the correct data from the JSONArray
     * @param fieldName Name of the field that is requested. As a key in JSONObject.
     * @return          Returns either a value corresponding to the fieldName or an empty string.
     */
    public static String getPlaceFieldText(Context context, String id, String fieldName) {

        JSONArray places = getJSONArray(context);

        if (places != null) {
            for (Object place : places) {
                if (place instanceof JSONObject) {
                    JSONObject placeObject = (JSONObject) ((JSONObject) place).get("place");

                    if (placeObject.get("id").equals(id)) {
                        return (String) placeObject.get(fieldName);
                    }
                }
            }
        } else {
            Toast.makeText(context, "JSONArray null", Toast.LENGTH_SHORT).show();
            //TODO exception
        }
        return "";
    }


    /**
     * Used to get the coordinates of the JSONObject with the given id.
     * @param context   App's context for finding the asset file.
     * @param id        ID identifies the place and is used to find the correct data from the JSONArray
     * @return          Returns either the LatLng of the place or a null.
     */
    public static LatLng getPlaceFieldLatLng(Context context, String id) {
        JSONArray places = getJSONArray(context);

        if (places != null) {
            for (Object place : places) {
                if (place instanceof JSONObject) {
                    JSONObject placeObject = (JSONObject) ((JSONObject) place).get("place");

                    if (placeObject.get("id").equals(id)) {

                        String latStr = (String) placeObject.get("lat");
                        double lat = Double.parseDouble(latStr);

                        String lngStr = (String) placeObject.get("lng");
                        double lng = Double.parseDouble(lngStr);

                        return new LatLng(lat, lng);
                    }
                }
            }
        } else {
            Toast.makeText(context, "JSONArray null", Toast.LENGTH_SHORT).show();
            //TODO exception
        }
        return null;
    }


    // will this ever be used?
    /**
     * Makes and returns a hashmap of key:value pairs in the JSONObject with the given id.
     * @param context   Context for the action.
     * @param id        Used to find correct JSONObject.
     * @return          A Hashmap containing all key:value pairs of the specified JSONObject.
     */
    public static HashMap<String, String> getMap(Context context, String id) {
        JSONArray places = getJSONArray(context);

        HashMap<String, String> map = new HashMap<>();

        if (places != null) {
            for (Object place : places) {

                if (place instanceof JSONObject) {
                    JSONObject placeObject = (JSONObject) ((JSONObject) place).get("place");

                    if (placeObject.get("id").equals(id)) {
                        for (Iterator iterator = placeObject.keySet().iterator(); iterator.hasNext();) {
                            String key = (String) iterator.next();
                            String value = (String) placeObject.get(key);
                            map.put(key, value);
                        }

                    }
                }
            }
        } else {
            Toast.makeText(context, "JSONArray null", Toast.LENGTH_SHORT).show();
            //TODO exception
        }
        return map;
    }


    /**
     * Finds the asset file and returns the JSONArray containing all places.
     * @param context   App's context where it'll look for the correct file.
     * @return          Returns either the JSONArray or a null.
     */
    private static JSONArray getJSONArray(Context context) {
        org.json.simple.parser.JSONParser parser = new org.json.simple.parser.JSONParser();

        try {
            InputStream stream = context.getAssets().open("places.json");
            InputStreamReader streamReader = new InputStreamReader((stream));

            return (JSONArray) parser.parse(streamReader);

        } catch (FileNotFoundException e) {
            Toast.makeText(context, "File not found", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        } catch (IOException e) {
            Toast.makeText(context, "IO exeption", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        } catch (ParseException e) {
            Toast.makeText(context, "parse exeption", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        return null;
    }


    /**
     * I wish this worked.
     * @param context   Context for the action.
     * @param entries   A map of user inputted entries.
     */
    public static void writePlace(Context context, Map<String, String> entries) {

        //JSONObject obj = new JSONObject();

        /*for (Map.Entry<String, String> entry : entries.entrySet()) {
            obj.put(entry.getKey(), entry.getValue());
        }*/

        //Toast.makeText(context, obj.get("id").toString(), Toast.LENGTH_SHORT).show();
    Toast.makeText(context, entries.get("id") + " lisätty", Toast.LENGTH_SHORT).show();

    }
}