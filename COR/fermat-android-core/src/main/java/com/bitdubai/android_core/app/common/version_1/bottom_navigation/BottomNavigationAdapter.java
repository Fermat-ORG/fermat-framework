package com.bitdubai.android_core.app.common.version_1.bottom_navigation;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;

import com.bitdubai.fermat.R;
import com.bitdubai.fermat_android_api.engine.DesktopHolderClickCallback;
import com.bitdubai.fermat_android_api.ui.adapters.AdapterChangeListener;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_api.layer.desktop.Item;
import com.bitdubai.fermat_api.layer.interface_objects.InterfaceType;
import com.bitdubai.sub_app.wallet_manager.commons.helpers.ItemTouchHelperAdapter;
import com.bitdubai.sub_app.wallet_manager.holder.FermatAppHolder;

import java.util.Collections;
import java.util.List;

public class BottomNavigationAdapter extends FermatAdapter<Item, FermatAppHolder> implements ItemTouchHelperAdapter {


    private final Typeface tf;
    private DesktopHolderClickCallback desktopHolderClickCallback;
    private AdapterChangeListener adapterChangeListener;

    public BottomNavigationAdapter(Context context) {
        super(context);
        tf = Typeface.createFromAsset(context.getAssets(), "fonts/CaviarDreams.ttf");
    }

    public BottomNavigationAdapter(Context context, List<Item> dataSet, DesktopHolderClickCallback desktopHolderClickCallback) {
        super(context, dataSet);
        this.desktopHolderClickCallback = desktopHolderClickCallback;
        tf = Typeface.createFromAsset(context.getAssets(), "fonts/CaviarDreams.ttf");
    }


    @Override
    protected FermatAppHolder createHolder(View itemView, int type) {
        FermatAppHolder fermatAppHolder = new FermatAppHolder(itemView);
        return fermatAppHolder;
    }

    @Override
    protected int getCardViewResource() {
        return R.layout.grid_launcher;
    }

    @Override
    protected void bindHolder(FermatAppHolder holder, Item data, final int position) {

        holder.name.setText(data.getName());
//            byte[] profileImage = data.getIcon();
//            if (profileImage != null) {
//                Bitmap bitmap = BitmapFactory.decodeByteArray(profileImage, 0, profileImage.length);
//                holder.thumbnail.setImageBitmap(bitmap);
//            }
        if (data.getIconResource() != 0)
            if (data.getType() != InterfaceType.EMPTY)
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

    public void setDesktopHolderClickCallback(DesktopHolderClickCallback desktopHolderClickCallback) {
        this.desktopHolderClickCallback = desktopHolderClickCallback;
    }

    public void setAdapterChangeListener(AdapterChangeListener adapterChangeListener) {
        this.adapterChangeListener = adapterChangeListener;
    }
}