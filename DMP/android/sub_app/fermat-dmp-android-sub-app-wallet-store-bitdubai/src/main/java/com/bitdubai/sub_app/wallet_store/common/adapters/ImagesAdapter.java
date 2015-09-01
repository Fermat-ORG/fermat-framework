package com.bitdubai.sub_app.wallet_store.common.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.wallet_store.bitdubai.R;

import java.util.ArrayList;

/**
 * Created by nelson on 27/08/15.
 */
public class ImagesAdapter extends FermatAdapter<Bitmap, ImagesAdapter.ViewHolder> {

    public ImagesAdapter(Context context) {
        super(context);
    }

    public ImagesAdapter(Context context, ArrayList<Bitmap> dataSet) {
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
    protected void bindHolder(ViewHolder holder, Bitmap data, int position) {
        holder.walletScreenshot.setImageBitmap(data);
    }

    class ViewHolder extends FermatViewHolder{
        ImageView walletScreenshot;

        protected ViewHolder(View itemView) {
            super(itemView);
            walletScreenshot = (ImageView) itemView.findViewById(R.id.screenshot);
        }
    }
}
