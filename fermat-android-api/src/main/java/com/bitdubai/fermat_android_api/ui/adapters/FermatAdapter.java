package com.bitdubai.fermat_android_api.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;

import java.util.ArrayList;

/**
 * Fermat Adapter
 * Use with RecyclerView Widgets
 *
 * @author Francisco Vásquez
 * @version 1.0
 */
public abstract class FermatAdapter<M, H extends FermatViewHolder> extends RecyclerView.Adapter<H> {

    protected ArrayList<M> dataSet;
    protected Context context;

    protected FermatAdapter(Context context) {
        this.context = context;
    }

    protected FermatAdapter(Context context, ArrayList<M> dataSet) {
        this.context = context;
        this.dataSet = dataSet;
    }

    @Override
    public H onCreateViewHolder(ViewGroup viewGroup, int type) {
        return createHolder(LayoutInflater.from(context).inflate(getCardViewResource(), viewGroup, false), type);
    }

    @Override
    public void onBindViewHolder(H holder, int position) {
        bindHolder(holder, getItem(position), position);
    }

    @Override
    public int getItemCount() {
        return dataSet == null ? 0 : dataSet.size();
    }

    /**
     * Get item
     *
     * @param position int position to get
     * @return Model object
     */
    public M getItem(final int position) {
        return dataSet != null ? (!dataSet.isEmpty() && position < dataSet.size()) ? dataSet.get(position) : null : null;
    }

    /**
     * Change whole dataSet and notify the adapter
     *
     * @param dataSet new ArrayList of model to change
     */
    public void changeDataSet(ArrayList<M> dataSet) {
        this.dataSet = dataSet;
        notifyDataSetChanged();
    }

    /**
     * Add an item to current dataSet
     *
     * @param item Item to insert into the dataSet
     */
    public void addItem(M item) {
        if (dataSet == null)
            return;
        int position = dataSet.size();
        dataSet.add(item);
        notifyItemInserted(position);
    }

    /**
     * Create a new holder instance
     *
     * @param itemView View object
     * @param type     int type
     * @return ViewHolder
     */
    protected abstract H createHolder(View itemView, int type);

    /**
     * Get custom layout to use it.
     *
     * @return int Layout Resource id: Example: R.layout.row_item
     */
    protected abstract int getCardViewResource();

    /**
     * Bind ViewHolder
     *
     * @param holder   ViewHolder object
     * @param data     Object data to render
     * @param position position to render
     */
    protected abstract void bindHolder(H holder, M data, int position);
}
