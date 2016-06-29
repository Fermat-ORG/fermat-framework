package com.bitdubai.sub_app.wallet_store.common.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.sub_app.wallet_store.common.holders.ImageViewHolder;
import com.wallet_store.bitdubai.R;

import java.util.List;

/**
 * Adapter para mostrar una lista de imagenes. Estas imagenes pueden estar representadas como Bitmap o ser un ResourceId
 *
 * @author Nelson Ramirez
 * @since 04/12/2015
 */
public class ImagesAdapter extends FermatAdapter<Object, ImageViewHolder> {

    public ImagesAdapter(Context context) {
        super(context);
    }

    public ImagesAdapter(Context context, List<Object> dataSet) {
        super(context, dataSet);
    }

    @Override
    protected ImageViewHolder createHolder(View itemView, int type) {
        return new ImageViewHolder(itemView);
    }

    @Override
    protected int getCardViewResource() {
        return R.layout.screenshot_wallet;
    }

    @Override
    protected void bindHolder(ImageViewHolder holder, Object data, int position) {
        if (data instanceof Bitmap)
            holder.getWalletScreenshot().setImageBitmap((Bitmap) data);
        else
            holder.getWalletScreenshot().setImageResource((int) data);
    }


}
