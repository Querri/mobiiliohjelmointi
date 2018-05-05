package ninja.siili.karabiineri;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.HashMap;

public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.ViewHolder> {

    private HashMap<String, String> mMap;
    private String[] mKeys;


    class ViewHolder extends RecyclerView.ViewHolder {

        TextView listItemKeyView;
        TextView listItemValueView;

        /**
         * Constructor for the ViewHolder.
         * @param itemView The View that you inflated in
         */
        public ViewHolder(View itemView) {
            super(itemView);

            listItemKeyView = itemView.findViewById(R.id.tv_item_key);
            listItemValueView = itemView.findViewById(R.id.tv_item_value);
        }
    }

    /**
     * Constructor for the Adapter.
     * @param map The map that contains the data the ViewHolder will be showing.
     * @param keys A list of keys in order that they will be laid out in the ViewHolder.
     */
    public PlaceAdapter(HashMap<String, String> map, String[] keys) {
        mMap = map;
        mKeys = keys;
    }


    /**
     * This gets called when each new ViewHolder is created. This happens when the RecyclerView
     * is laid out. Enough ViewHolders will be created to fill the screen and allow for scrolling.
     *
     * @param viewGroup The ViewGroup that these ViewHolders are contained within.
     * @param viewType  If your RecyclerView has more than one type of item (which mine doesn't)
     *                  this can be used to provide a different layout.
     * @return A new NumberViewHolder that holds the View for each list item
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.place_list_item, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }


    /**
     * OnBindViewHolder is called by the RecyclerView to display the data at the specified
     * position. In this method, we update the contents of the ViewHolder to display the correct
     * indices in the list for this particular position, using the "position" argument.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        String keyInPosition = mKeys[position];
        String valueInPosition = mMap.get(keyInPosition);

        holder.listItemKeyView.setText(keyInPosition);
        holder.listItemValueView.setText(valueInPosition);
    }


    /**
     * This method simply returns the number of items to display.
     *
     * @return The number of items available
     */
    @Override
    public int getItemCount() {
        return mKeys.length;
    }
}
