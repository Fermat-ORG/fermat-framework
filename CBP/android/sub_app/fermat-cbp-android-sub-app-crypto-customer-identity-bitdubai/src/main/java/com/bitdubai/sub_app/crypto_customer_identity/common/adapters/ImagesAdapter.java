package com.bitdubai.sub_app.crypto_customer_identity.common.adapters;


import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;

import com.bitdubai.desktop.wallet_manager.R;
import com.bitdubai.desktop.wallet_manager.common.holders.BitmapViewHolder;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;

import java.util.ArrayList;

/**
 * Created by nelson on 27/08/15.
 */
public class ImagesAdapter extends FermatAdapter<Bitmap, BitmapViewHolder> {

    public ImagesAdapter(Context context) {
        super(context);
    }

    public ImagesAdapter(Context context, ArrayList<Bitmap> dataSet) {
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
