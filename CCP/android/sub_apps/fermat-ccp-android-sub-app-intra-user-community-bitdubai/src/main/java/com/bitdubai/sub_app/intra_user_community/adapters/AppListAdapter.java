package com.bitdubai.sub_app.intra_user_community.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserInformation;
import com.bitdubai.sub_app.intra_user_community.R;
import com.bitdubai.sub_app.intra_user_community.holders.IntraUserInformationHolder;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AppListAdapter extends FermatAdapter<IntraUserInformation, IntraUserInformationHolder> {
        
        
        public AppListAdapter(Context context) {
            super(context);
        }

        public AppListAdapter(Context context, List<IntraUserInformation> dataSet) {
            super(context, dataSet);
        }

        @Override
        protected IntraUserInformationHolder createHolder(View itemView, int type) {
            return new IntraUserInformationHolder(itemView);
        }

        @Override
        protected int getCardViewResource() {
            return R.layout.world_frament_row;
        }

        @Override
        protected void bindHolder(IntraUserInformationHolder holder, IntraUserInformation data, int position) {

            holder.name.setText(data.getName());

            byte[] profileImage = data.getProfileImage();
            if (profileImage != null) {
               // BitmapFactory.Options options = new BitmapFactory.Options();
               // options.inPurgeable = true;
                Bitmap bitmap = BitmapFactory.decodeByteArray(profileImage, 0, profileImage.length);
                holder.thumbnail.setImageBitmap(bitmap);
            }
        }

    }