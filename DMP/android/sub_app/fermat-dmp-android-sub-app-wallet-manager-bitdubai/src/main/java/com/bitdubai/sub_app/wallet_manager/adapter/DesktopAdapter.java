package com.bitdubai.sub_app.wallet_manager.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;

import com.bitdubai.fermat_android_api.ui.adapters.AdapterChangeListener;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_api.layer.all_definition.runtime.FermatApp;
import com.bitdubai.fermat_api.layer.interface_objects.InterfaceType;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserInformation;
import com.bitdubai.fermat_dmp.wallet_manager.R;
import com.bitdubai.sub_app.wallet_manager.commons.helpers.ItemTouchHelperAdapter;
import com.bitdubai.sub_app.wallet_manager.holder.DesktopHolderClickCallback;
import com.bitdubai.sub_app.wallet_manager.holder.FermatAppHolder;
import com.bitdubai.sub_app.wallet_manager.structure.Item;

import java.util.Collections;
import java.util.List;

public class DesktopAdapter extends FermatAdapter<Item, FermatAppHolder> implements ItemTouchHelperAdapter {

    public static final int DEKSTOP = 1;
    public static final int FOLDER = 2;

    private int fragmentWhoUseThisAdapter;
    private DesktopHolderClickCallback desktopHolderClickCallback;
    private AdapterChangeListener adapterChangeListener;

    public DesktopAdapter(Context context) {
            super(context);
        }

        public DesktopAdapter(Context context, List<Item> dataSet,DesktopHolderClickCallback desktopHolderClickCallback,int fragmentWhoUseThisAdapter) {
            super(context, dataSet);
            this.desktopHolderClickCallback = desktopHolderClickCallback;
            this.fragmentWhoUseThisAdapter = fragmentWhoUseThisAdapter;
        }

        @Override
        protected FermatAppHolder createHolder(View itemView, int type) {
            FermatAppHolder fermatAppHolder = new FermatAppHolder(itemView);
            return fermatAppHolder;
        }

        @Override
        protected int getCardViewResource() {
            if(DEKSTOP==fragmentWhoUseThisAdapter)
            return R.layout.shell_wallet_desktop_front_grid_item;
            else return R.layout.grid_folder;
        }

        @Override
        protected void bindHolder(FermatAppHolder holder, Item data, final int position) {

            holder.name.setText(data.getName());
//            byte[] profileImage = data.getIcon();
//            if (profileImage != null) {
//                Bitmap bitmap = BitmapFactory.decodeByteArray(profileImage, 0, profileImage.length);
//                holder.thumbnail.setImageBitmap(bitmap);
//            }
            if(data.getIconResource()!=0)
                if(data.getType()!= InterfaceType.EMPTY)
                    holder.thumbnail.setImageResource(data.getIconResource());

            holder.thumbnail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dataSet.get(position).selected = !dataSet.get(position).selected;
                    Item item = dataSet.get(position);
                    notifyItemChanged(position);
                    desktopHolderClickCallback.onHolderItemClickListener(item, position);
                    if (adapterChangeListener != null)
                        adapterChangeListener.onDataSetChanged(dataSet);
                }
            });

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
        getItem(fromPosition).setPosition(toPosition);
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public void onItemDismiss(int position) {
        dataSet.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }


    public void setAdapterChangeListener(AdapterChangeListener adapterChangeListener) {
        this.adapterChangeListener = adapterChangeListener;
    }
}