package com.bitdubai.fermat_dap_android_sub_app_asset_factory_bitdubai.adapters;

import android.content.Context;
import android.view.View;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_dap_android_sub_app_asset_factory_bitdubai.R;
import com.bitdubai.fermat_dap_android_sub_app_asset_factory_bitdubai.holders.AssetHolder;
import com.bitdubai.fermat_dap_android_sub_app_asset_factory_bitdubai.interfaces.PopupMenu;
import com.bitdubai.fermat_dap_api.layer.dap_middleware.dap_asset_factory.interfaces.AssetFactory;

import java.util.List;

/**
 * Created by francisco on 01/10/15.
 */
public class AssetFactoryAdapter extends FermatAdapter<AssetFactory, AssetHolder> {

    private PopupMenu menuItemClick;

    public AssetFactoryAdapter(Context context) {
        super(context);
    }

    public AssetFactoryAdapter(Context context, List<AssetFactory> dataSet) {
        super(context, dataSet);
    }

    @Override
    protected AssetHolder createHolder(View itemView, int type) {
        return new AssetHolder(itemView);
    }

    @Override
    protected int getCardViewResource() {
        return R.layout.row_draf_asset;
    }

    @Override
    protected void bindHolder(final AssetHolder holder, final AssetFactory data, final int position) {
        holder.title.setText(data.getName() != null ? data.getName() : "No name given...");
        holder.description.setText(data.getDescription() != null ? data.getDescription() : "");
        holder.state.setText(data.getState().toString());
        if (holder.options != null && menuItemClick != null) {
            holder.options.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    menuItemClick.onMenuItemClickListener(holder.options, data, position);
                }
            });
        }
    }

    public void setMenuItemClick(PopupMenu menuItemClick) {
        this.menuItemClick = menuItemClick;
    }
}
