package com.bitdubai.sub_app.intra_user_community.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserInformation;
import com.bitdubai.sub_app.intra_user_community.R;
import com.bitdubai.sub_app.intra_user_community.holders.IntraUserInformationHolder;

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

            byte[] profileImage = null;
            try {
                profileImage = data.getProfileImage();
            } catch (Exception e) {

            }

            if (profileImage != null) {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPurgeable = true;
                Bitmap bitmap = BitmapFactory.decodeByteArray(profileImage, 0, profileImage.length, options);
                holder.thumbnail.setImageBitmap(bitmap);
            } else {
                    //holder.thumbnail.setImageResource(R.drawable.piper_profile_picture);
                    switch (position) {
                        case 0:
                            holder.thumbnail.setImageResource(R.drawable.piper_profile_picture);
                            break;
                        case 1:
                            holder.thumbnail.setImageResource(R.drawable.luis_profile_picture);
                            break;
                        case 2:
                            holder.thumbnail.setImageResource(R.drawable.brant_profile_picture);
                            break;
                        case 3:
                            holder.thumbnail.setImageResource(R.drawable.louis_profile_picture);
                            break;
                        case 4:
                            holder.thumbnail.setImageResource(R.drawable.madaleine_profile_picture);
                            break;
                        case 5:
                            holder.thumbnail.setImageResource(R.drawable.adrian_profile_picture);
                            break;
                        case 6:
                            holder.thumbnail.setImageResource(R.drawable.deniz_profile_picture);
                            break;
                        case 7:
                            holder.thumbnail.setImageResource(R.drawable.dea_profile_picture);
                            break;
                        case 8:
                            holder.thumbnail.setImageResource(R.drawable.florence_profile_picture);
                            break;
                        case 9:
                            holder.thumbnail.setImageResource(R.drawable.alexandra_profile_picture);
                            break;
                        default:
                            holder.thumbnail.setImageResource(R.drawable.robert_profile_picture);
                            break;
                    }

                
            }
        }

    }