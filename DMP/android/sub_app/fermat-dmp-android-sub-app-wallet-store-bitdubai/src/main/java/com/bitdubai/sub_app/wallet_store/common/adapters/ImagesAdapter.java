package com.bitdubai.sub_app.wallet_store.common.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.sub_app.wallet_store.common.holders.BitmapViewHolder;
import com.wallet_store.bitdubai.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nelson on 27/08/15.
 */
public class ImagesAdapter extends FermatAdapter<Bitmap, BitmapViewHolder> {

    public ImagesAdapter(Context context) {
        super(context);
    }

    public ImagesAdapter(Context context, List<Bitmap> dataSet) {
        super(context, dataSet);
    }

    @Override
    protected BitmapViewHolder createHolder(View itemView, int type) {
        return new BitmapViewHolder(itemView);
    }

    @Override
    protected int getCardViewResource() {
        return R.layout.screenshot_wallet;
    }

    @Override
    protected void bindHolder(BitmapViewHolder holder, Bitmap data, int position) {
        holder.getWalletScreenshot().setImageBitmap(data);
    }


}
