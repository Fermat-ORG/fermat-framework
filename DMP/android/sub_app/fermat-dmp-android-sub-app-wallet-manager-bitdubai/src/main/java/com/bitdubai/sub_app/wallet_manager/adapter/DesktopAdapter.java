package com.bitdubai.sub_app.wallet_manager.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_api.layer.all_definition.runtime.FermatApp;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserInformation;
import com.bitdubai.fermat_dmp.wallet_manager.R;
import com.bitdubai.sub_app.wallet_manager.commons.helpers.ItemTouchHelperAdapter;
import com.bitdubai.sub_app.wallet_manager.holder.FermatAppHolder;
import com.bitdubai.sub_app.wallet_manager.structure.Item;

import java.util.Collections;
import java.util.List;

public class DesktopAdapter extends FermatAdapter<Item, FermatAppHolder> implements ItemTouchHelperAdapter {


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
            if(data.getIconResource()!=0)
            holder.thumbnail.setImageResource(data.getIconResource());
        }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(dataSet, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(dataSet, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public void onItemDismiss(int position) {
        dataSet.remove(position);
        notifyItemRemoved(position);
    }
}