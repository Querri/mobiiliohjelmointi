package com.example.android.harj5_jaakaappitietokanta;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.harj5_jaakaappitietokanta.data.FridgeContract;


public class FridgeAdapter extends RecyclerView.Adapter<FridgeAdapter.FridgeItemViewHolder> {

    private Cursor mCursor;
    private Context mContext;


    public FridgeAdapter(Context context, Cursor cursor) {
        this.mContext = context;
        this.mCursor = cursor;
    }



    @Override
    public FridgeItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.fridge_item, viewGroup, false);
        return new FridgeItemViewHolder(view);
    }


    @Override
    public void onBindViewHolder(FridgeItemViewHolder holder, int position) {
        // Move the mCursor to the position of the item to be displayed
        if (!mCursor.moveToPosition(position))
            return; // bail if returned null

        // Update the view holder with the information needed to display
        String name = mCursor.getString(mCursor.getColumnIndex(FridgeContract.FridgeEntry.COLUMN_PRODUCT_NAME));
        int expiration = mCursor.getInt(mCursor.getColumnIndex(FridgeContract.FridgeEntry.COLUMN_EXPIRATION_DATE));

        long id = mCursor.getLong(mCursor.getColumnIndex(FridgeContract.FridgeEntry._ID));

        // Display the guest name
        holder.productNameTextView.setText(name);
        // Display the party count
        holder.expirationTextView.setText(String.valueOf(expiration));
        holder.itemView.setTag(id);
    }


    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    public void swapCursor(Cursor newCursor) {
        if (mCursor != null) mCursor.close();
        mCursor = newCursor;
        if (newCursor != null) {
            this.notifyDataSetChanged();
        }
    }


    class FridgeItemViewHolder extends RecyclerView.ViewHolder {
        TextView expirationTextView;
        TextView productNameTextView;

        public FridgeItemViewHolder(View itemView) {
            super(itemView);
            expirationTextView = itemView.findViewById(R.id.et_product_expiration);
            productNameTextView = itemView.findViewById(R.id.et_product_name);
        }
    }

}




































/*
public class FridgeAdaper extends RecyclerView.Adapter<GreenAdapter.NumberViewHolder> {

    private static final String TAG = FridgeAdaper.class.getSimpleName();
    private int mFridgeItems;

    public FridgeAdaper(int numberOfItems) {
        mFridgeItems = numberOfItems;
    }

    @Override
    public FridgeViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForFridgeItem = R.layout.fridge_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdForFridgeItem, viewGroup, shouldAttachToParentImmediately);
        FridgeViewHolder viewHolder = new FridgeViewHolder(view);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(FridgeViewHolder holder, int position) {
        Log.d(TAG, "#" + position);
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mFridgeItems;
    }


    class FridgeViewHolder extends RecyclerView.ViewHolder {
        TextView fridgeItemTextView;

        public FridgeViewHolder(View itemView) {
            super(itemView);
            fridgeItemTextView = itemView.findViewById(R.id.tv_fridge_item);
        }

        void bind(int listIndex) {
            fridgeItemTextView.setText(String.valueOf(listIndex));
        }
    }

}*/



