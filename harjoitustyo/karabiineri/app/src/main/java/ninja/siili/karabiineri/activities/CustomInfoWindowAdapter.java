package ninja.siili.karabiineri.activities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.google.gson.Gson;

import ninja.siili.karabiineri.R;
import ninja.siili.karabiineri.utilities.MapMarkerInfo;

class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    Context context;
    LayoutInflater inflater;


    public CustomInfoWindowAdapter(Context context) {
        this.context = context;
    }


    /** It just exists **/
    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }


    @Override
    public View getInfoWindow(Marker marker) {
        inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.custom_infowindow, null);

        Gson gson = new Gson();
        MapMarkerInfo markerInfo = gson.fromJson(marker.getSnippet(), MapMarkerInfo.class);

        TextView title = view.findViewById(R.id.title);
        TextView subtitle = view.findViewById(R.id.subtitle);
        ImageView imageView = view.findViewById(R.id.image);

        title.setText(markerInfo.getTitle());
        subtitle.setText(markerInfo.getDesc());
        imageView.setImageDrawable(this.context.getDrawable(markerInfo.getImage()));

        return view;
    }
}