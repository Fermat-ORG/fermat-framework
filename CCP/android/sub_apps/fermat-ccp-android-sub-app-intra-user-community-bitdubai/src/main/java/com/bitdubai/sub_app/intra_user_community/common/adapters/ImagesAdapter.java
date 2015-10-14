package com.bitdubai.sub_app.intra_user_community.common.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.sub_app.intra_user_community.R;

import java.util.ArrayList;

/**
 * Created by nelson on 27/08/15.
 */
public class ImagesAdapter extends FermatAdapter<Drawable, ImagesAdapter.ViewHolder> {

    public ImagesAdapter(Context context) {
        super(context);
    }

    public ImagesAdapter(Context context, ArrayList<Drawable> dataSet) {
        super(context, dataSet);
    }

    @Override
    protected ViewHolder createHolder(View itemView, int type) {
        return new ViewHolder(itemView);
    }

    @Override
    protected int getCardViewResource() {
        return R.layout.screenshot_wallet;
    }

    @Override
    protected void bindHolder(ViewHolder holder, Drawable data, int position) {
        holder.walletScreenshot.setImageDrawable(data);
    }

    class ViewHolder extends FermatViewHolder{
        ImageView walletScreenshot;

        protected ViewHolder(View itemView) {
            super(itemView);
            walletScreenshot = (ImageView) itemView.findViewById(R.id.screenshot);
        }
    }
}
