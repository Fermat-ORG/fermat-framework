package com.mati.horizontalscrollview;

import android.content.Context;
import android.view.View;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapterImproved;

import java.util.List;

/**
 * Created by mati on 2016.02.19..
 */
public class HorizontalRecyclerAdapter extends FermatAdapterImproved<SetttingsItems,HorizontalRecyclerViewHolder> {

    protected HorizontalRecyclerAdapter(Context context) {
        super(context);
    }

    protected HorizontalRecyclerAdapter(Context context, List<SetttingsItems> dataSet) {
        super(context, dataSet);
    }

    @Override
    protected HorizontalRecyclerViewHolder createHolder(View itemView, int type) {
        return new HorizontalRecyclerViewHolder(itemView,type);
    }

    @Override
    protected int getCardViewResource(int type) {
        return R.layout.holder;
    }

    @Override
    protected void bindHolder(HorizontalRecyclerViewHolder holder, SetttingsItems data, int position) {
        if(data.getSrc()>0) holder.getImageButton().setBackgroundResource(data.getSrc());
        holder.getImageButton().getLayoutParams().height = 130;
        holder.getImageButton().getLayoutParams().width = 200;
        if(data.getName()!=null) holder.getTxtName().setText(data.getName());
    }


}
