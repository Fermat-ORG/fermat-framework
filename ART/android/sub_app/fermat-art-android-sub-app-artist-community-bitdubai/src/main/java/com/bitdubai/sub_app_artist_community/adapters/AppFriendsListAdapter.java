package com.bitdubai.sub_app_artist_community.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;

import com.bitdubai.fermat_android_api.layer.definition.wallet.utils.ImagesUtils;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.ArtCommunityInformation;
import com.bitdubai.sub_app.artist_community.R;
import com.bitdubai.sub_app_artist_community.holders.AppFriendsListHolder;

import java.util.List;

/**
 * Created by Gabriel Araujo (gabe_512@hotmail.com) on 08/04/16.
 */
public class AppFriendsListAdapter extends FermatAdapter<ArtCommunityInformation, AppFriendsListHolder> {

    public AppFriendsListAdapter(Context context, List<ArtCommunityInformation> dataSet) {
        super(context, dataSet);
    }

    @Override
    protected AppFriendsListHolder createHolder(View itemView, int type) {
        return new AppFriendsListHolder(itemView);
    }

    @Override
    protected int getCardViewResource() {
        return R.layout.aac_row_connection_list;
    }

    @Override
    protected void bindHolder(AppFriendsListHolder holder, ArtCommunityInformation data, int position) {
        if (data.getPublicKey() != null) {
            holder.friendName.setText(data.getAlias());
            if (data.getImage() != null) {
                Bitmap bitmap;
                if (data.getImage().length > 0) {
                    bitmap = BitmapFactory.decodeByteArray(data.getImage(), 0, data.getImage().length);
                } else {
                    bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.aac_profile_image);
                }
              //  bitmap = Bitmap.createScaledBitmap(bitmap, 40, 40, true);
                holder.friendAvatar.setImageDrawable(ImagesUtils.getRoundedBitmap(context.getResources(), bitmap));
            }else{
                Bitmap bitmap;
                bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.aac_profile_image);
           //     bitmap = Bitmap.createScaledBitmap(bitmap, 40, 40, true);
                holder.friendAvatar.setImageDrawable(ImagesUtils.getRoundedBitmap(context.getResources(), bitmap));
            }
            Actors actorType = data.getActorType();
            Bitmap bitmap;
            switch (actorType){
                case ART_ARTIST:
                    bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.aac_artist);
                    break;
                case ART_FAN:
                    bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.aac_people);
                    break;
                default:
                    bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.aac_people);
                    break;
                }
         //   bitmap = Bitmap.createScaledBitmap(bitmap, 40, 40, true);
            holder.actorIcon.setImageBitmap(bitmap);
        }
    }

    public int getSize() {
        if (dataSet != null)
            return dataSet.size();
        return 0;
    }
}
