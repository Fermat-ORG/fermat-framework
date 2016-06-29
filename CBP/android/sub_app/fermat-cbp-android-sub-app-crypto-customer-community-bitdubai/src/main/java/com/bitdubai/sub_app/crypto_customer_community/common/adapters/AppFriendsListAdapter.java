package com.bitdubai.sub_app.crypto_customer_community.common.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;

import com.bitdubai.fermat_android_api.layer.definition.wallet.utils.ImagesUtils;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.interfaces.CryptoCustomerCommunityInformation;
import com.bitdubai.sub_app.crypto_customer_community.R;
import com.bitdubai.sub_app.crypto_customer_community.common.holders.AppFriendsListHolder;

import java.util.List;

/**
 * Created by Alejandro Bicelis on 04/02/2016.
 */
public class AppFriendsListAdapter extends FermatAdapter<CryptoCustomerCommunityInformation, AppFriendsListHolder> {

    public AppFriendsListAdapter(Context context, List<CryptoCustomerCommunityInformation> dataSet) {
        super(context, dataSet);
    }

    @Override
    protected AppFriendsListHolder createHolder(View itemView, int type) {
        return new AppFriendsListHolder(itemView);
    }

    @Override
    protected int getCardViewResource() {
        return R.layout.ccc_row_connection_list;
    }

    @Override
    protected void bindHolder(AppFriendsListHolder holder, CryptoCustomerCommunityInformation data, int position) {
        if (data.getPublicKey() != null) {
            holder.friendName.setText(data.getAlias());
            if (data.getImage() != null) {
                Bitmap bitmap;
                if (data.getImage().length > 0) {
                    bitmap = BitmapFactory.decodeByteArray(data.getImage(), 0, data.getImage().length);
                } else {
                    bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.profile_image);
                }
                bitmap = Bitmap.createScaledBitmap(bitmap, 40, 40, true);
                holder.friendAvatar.setImageDrawable(ImagesUtils.getRoundedBitmap(context.getResources(), bitmap));
            }else{
                Bitmap bitmap;
                bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.profile_image);
                bitmap = Bitmap.createScaledBitmap(bitmap, 40, 40, true);
                holder.friendAvatar.setImageDrawable(ImagesUtils.getRoundedBitmap(context.getResources(), bitmap));
            }
        }
    }

    public int getSize() {
        if (dataSet != null)
            return dataSet.size();
        return 0;
    }
}
