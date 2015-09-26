package com.bitdubai.sub_app.wallet_publisher.adapters;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.sub_app.wallet_publisher.R;
import com.bitdubai.sub_app.wallet_publisher.holders.ScreenShootViewHolder;

import java.util.ArrayList;

/**
 * ScreenShootAdapter
 *
 * @author Francisco Vasquez
 * @version 1.0
 */
public class ScreenShootAdapter extends FermatAdapter<byte[], ScreenShootViewHolder> {

    private OnScreenShootItemClickListener itemListener;


    public ScreenShootAdapter(Context context) {
        super(context);
    }

    public ScreenShootAdapter(Context context, ArrayList<byte[]> dataSet) {
        super(context, dataSet);
    }

    @Override
    protected ScreenShootViewHolder createHolder(View itemView, int type) {
        return new ScreenShootViewHolder(itemView);
    }

    @Override
    protected int getCardViewResource() {
        return R.layout.wfp_screen_shoot_item;
    }

    @Override
    protected void bindHolder(final ScreenShootViewHolder holder, byte[] data, final int position) {
        if (data == null) {
            holder.screenShoot.setImageResource(R.drawable.ic_camera_green);
        } else {
            holder.screenShoot.setImageDrawable(new BitmapDrawable(context.getResources(),
                    BitmapFactory.decodeByteArray(data, 0, data.length)));
        }
        holder.screenShoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (itemListener != null)
                    itemListener.onScreenShootClickListener(holder.screenShoot, position);
            }
        });
    }

    public void setItemListener(OnScreenShootItemClickListener itemListener) {
        this.itemListener = itemListener;
    }

    public interface OnScreenShootItemClickListener {
        void onScreenShootClickListener(View itemView, int position);
    }
}
