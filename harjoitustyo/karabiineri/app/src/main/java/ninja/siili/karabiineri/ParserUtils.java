package ninja.siili.karabiineri;

import android.content.Context;
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


public abstract class ParserUtils {

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


    public static int getValuesCount(Context context, String id) {
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
        }
        return map.size();
    }


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
        }
        return map;
    }


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
        }
        return "";
    }

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
        }
        return null;
    }


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



    public static void writePlace(Context context, Map<String, String> entries) {

        //JSONObject obj = new JSONObject();

        /*for (Map.Entry<String, String> entry : entries.entrySet()) {
            obj.put(entry.getKey(), entry.getValue());
        }*/

        //Toast.makeText(context, obj.get("id").toString(), Toast.LENGTH_SHORT).show();
    Toast.makeText(context, entries.get("id") + " lisätty", Toast.LENGTH_SHORT).show();

    }
}
