package com.bitdubai.sub_app.wallet_manager.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_api.layer.all_definition.runtime.FermatApp;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserInformation;
import com.bitdubai.fermat_dmp.wallet_manager.R;
import com.bitdubai.sub_app.wallet_manager.holder.FermatAppHolder;
import com.bitdubai.sub_app.wallet_manager.structure.Item;

import java.util.List;

public class DesktopAdapter extends FermatAdapter<Item, FermatAppHolder> {


        public DesktopAdapter(Context context) {
            super(context);
        }

        public DesktopAdapter(Context context, List<Item> dataSet) {
            super(context, dataSet);
        }

        @Override
        protected FermatAppHolder createHolder(View itemView, int type) {
            return new FermatAppHolder(itemView);
        }

        @Override
        protected int getCardViewResource() {
            return R.layout.shell_wallet_desktop_front_grid_item;
        }

        @Override
        protected void bindHolder(FermatAppHolder holder, Item data, int position) {

            holder.name.setText(data.getName());
//            byte[] profileImage = data.getIcon();
//            if (profileImage != null) {
//                Bitmap bitmap = BitmapFactory.decodeByteArray(profileImage, 0, profileImage.length);
//                holder.thumbnail.setImageBitmap(bitmap);
//            }
            holder.thumbnail.setImageResource(data.getIconResource());
        }

    }