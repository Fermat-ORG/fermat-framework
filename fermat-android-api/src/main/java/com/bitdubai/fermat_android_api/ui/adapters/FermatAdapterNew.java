package com.bitdubai.fermat_android_api.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.fermat_android_api.ui.inflater.ViewInflater;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatListItemListeners;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;

import java.util.List;

/**
 * Fermat Adapter
 * Use with RecyclerView Widgets
 *
 * @author Matias Furszyfer
 * @version 1.0
 */
public abstract class FermatAdapterNew<M, H extends FermatViewHolder> extends RecyclerView.Adapter<H> {

    protected List<M> dataSet;
    protected Context context;
    protected FermatListItemListeners<M> eventListeners;

    protected ViewInflater viewInflater;

    protected ResourceProviderManager resourceProviderManager;

    protected FermatAdapterNew(Context context, ViewInflater viewInflater, ResourceProviderManager resourceProviderManager) {
        this.context = context;
        this.viewInflater = viewInflater;
        this.resourceProviderManager = resourceProviderManager;
    }

    protected FermatAdapterNew(Context context, List<M> dataSet, ViewInflater viewInflater, ResourceProviderManager resourceProviderManager) {
        this.context = context;
        this.dataSet = dataSet;
        this.viewInflater = viewInflater;
        this.resourceProviderManager = resourceProviderManager;
    }

    @Override
    public H onCreateViewHolder(ViewGroup viewGroup, int type) {
        //pensar como sería sin esto
        String layout = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<LinearLayout xmlns:android=\"http://schemas.android.com/apk/res/android\"\n" +
                "    android:orientation=\"horizontal\" android:layout_width=\"match_parent\"\n" +
                "    android:layout_height=\"wrap_content\"\n" +
                "    android:background=\"#aafbca\"\n" +
                "    >\n" +
                "\n" +
                "    <TextView\n" +
                "        android:layout_width=\"7dp\"\n" +
                "        android:layout_height=\"match_parent\"\n" +
                "        android:background=\"#ab31a4\"\n" +
                "        />\n" +
                "\n" +
                "    <ImageView\n" +
                "        android:layout_width=\"75dp\"\n" +
                "        android:layout_height=\"75dp\"\n" +
                "        android:src=\"@drawable/mati_profile\"\n" +
                "        android:layout_marginLeft=\"20dp\"\n" +
                "        android:layout_marginTop=\"20dp\"\n" +
                "        android:layout_marginBottom=\"30dp\"\n" +
                "        android:id=\"@+id/imageView_contact\"\n" +
                "\n" +
                "        />\n" +
                "\n" +
                "    <LinearLayout\n" +
                "        android:layout_width=\"wrap_content\"\n" +
                "        android:layout_height=\"match_parent\"\n" +
                "        android:orientation=\"vertical\"\n" +
                "        android:layout_marginLeft=\"7dp\"\n" +
                "        android:paddingLeft=\"20dp\"\n" +
                "        android:layout_marginTop=\"20dp\"\n" +
                "        android:background=\"#1222a2\">\n" +
                "\n" +
                "        <TextView\n" +
                "            android:layout_width=\"match_parent\"\n" +
                "            android:layout_height=\"wrap_content\"\n" +
                "            android:text=\"25 btc\"\n" +
                "            android:id=\"@+id/txt_amount\"\n" +
                "            android:background=\"#12a222\"\n" +
                "            />\n" +
                "\n" +
                "        <TextView\n" +
                "            android:layout_width=\"match_parent\"\n" +
                "            android:layout_height=\"wrap_content\"\n" +
                "            android:text=\"Matias Furszyfer\"\n" +
                "            android:id=\"@+id/txt_contact_name\"\n" +
                "            android:background=\"#12a222\"\n" +
                "            />\n" +
                "\n" +
                "    </LinearLayout>\n" +
                "\n" +
                "    <LinearLayout\n" +
                "        android:layout_width=\"match_parent\"\n" +
                "        android:layout_height=\"match_parent\"\n" +
                "        android:orientation=\"vertical\"\n" +
                "        android:paddingTop=\"20dp\"\n" +
                "        android:paddingRight=\"5dp\"\n" +
                "        android:background=\"#aa2222\"\n" +
                "        >\n" +
                "        <LinearLayout\n" +
                "            android:layout_width=\"match_parent\"\n" +
                "            android:layout_height=\"wrap_content\"\n" +
                "            android:orientation=\"vertical\"\n" +
                "            android:gravity=\"right\"\n" +
                "            android:background=\"#aaaaaa\"\n" +
                "            >\n" +
                "\n" +
                "            <TextView\n" +
                "                android:layout_width=\"wrap_content\"\n" +
                "                android:layout_height=\"wrap_content\"\n" +
                "                android:text=\"Conference ticket\"\n" +
                "                android:id=\"@+id/txt_notes\"\n" +
                "                />\n" +
                "\n" +
                "            <TextView\n" +
                "                android:layout_width=\"wrap_content\"\n" +
                "                android:layout_height=\"wrap_content\"\n" +
                "                android:text=\"09 ago 2015\"\n" +
                "                android:id=\"@+id/txt_time\"\n" +
                "                android:layout_below=\"@+id/txt_notes\"\n" +
                "                />\n" +
                "\n" +
                "\n" +
                "\n" +
                "        </LinearLayout>\n" +
                "\n" +
                "\n" +
                "        <LinearLayout\n" +
                "            android:layout_width=\"wrap_content\"\n" +
                "            android:layout_height=\"wrap_content\"\n" +
                "            android:layout_marginTop=\"30dp\"\n" +
                "            android:background=\"#acacee\"\n" +
                "            >\n" +
                "\n" +
                "            <Button\n" +
                "                android:layout_width=\"wrap_content\"\n" +
                "                android:layout_height=\"wrap_content\"\n" +
                "                android:text=\"accept\"\n" +
                "                android:textSize=\"10dp\"\n" +
                "                android:visibility=\"gone\"/>\n" +
                "\n" +
                "            <Button\n" +
                "                android:layout_width=\"wrap_content\"\n" +
                "                android:layout_height=\"wrap_content\"\n" +
                "                android:text=\"refuse\"\n" +
                "                android:textSize=\"10dp\"\n" +
                "                android:visibility=\"gone\"/>\n" +
                "\n" +
                "\n" +
                "        </LinearLayout>\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "    </LinearLayout>\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "</LinearLayout>";

        LinearLayout linearLayout = new LinearLayout(context);

        linearLayout.setOrientation(LinearLayout.VERTICAL);

        LinearLayout.LayoutParams lps = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        lps.setMargins(0, 15, 5, 0);

        linearLayout.setLayoutParams(lps);


        try {


            //return createHolder(viewInflater.inflate(resourceProviderManager.getLayoutResource(getCardViewResourceName(), ScreenOrientation.PORTRAIT, UUID.fromString("f39421a2-0b63-4d50-aba6-51b70d492c3e"), "reference_wallet")),type);

            return createHolder(viewInflater.inflate(layout, linearLayout), type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return createHolder(viewInflater.inflate(layout, linearLayout), type);
        //return createHolder(LayoutInflater.from(context).inflate(getCardViewResource(), viewGroup, false), type);
    }

    @Override
    public void onBindViewHolder(H holder, final int position) {
        // setting up custom listeners
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (eventListeners != null) {
                    eventListeners.onItemClickListener(getItem(position), position);
                }
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (eventListeners != null) {
                    eventListeners.onLongItemClickListener(getItem(position), position);
                    return true;
                }
                return false;
            }
        });
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
    public void changeDataSet(List<M> dataSet) {
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

    public void setFermatListEventListener(FermatListItemListeners<M> onEventListeners) {
        this.eventListeners = onEventListeners;
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
    protected abstract String getCardViewResourceName();

    /**
     * Bind ViewHolder
     *
     * @param holder   ViewHolder object
     * @param data     Object data to render
     * @param position position to render
     */
    protected abstract void bindHolder(H holder, M data, int position);


}
