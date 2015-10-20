package com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.adapters;

import android.content.Context;
import android.view.View;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.R;
import com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.holders.DigitalAssetViewHolder;
import com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.interfaces.PopupMenu;
import com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.models.DigitalAsset;

import java.util.List;

/**
 * Created by francisco on 19/10/15.
 */
public class DigitalAssetAdapter extends FermatAdapter<DigitalAsset, DigitalAssetViewHolder> {

    private PopupMenu popupMenu;

    public DigitalAssetAdapter(Context context) {
        super(context);
    }

    public DigitalAssetAdapter(Context context, List<DigitalAsset> dataSet) {
        super(context, dataSet);
    }

    @Override
    protected DigitalAssetViewHolder createHolder(View itemView, int type) {
        return new DigitalAssetViewHolder(itemView);
    }

    @Override
    protected int getCardViewResource() {
        return R.layout.row_digital_asset;
    }

    @Override
    protected void bindHolder(final DigitalAssetViewHolder holder, final DigitalAsset data, final int position) {
        holder.name.setText(data.getName());
        holder.balance.setText(data.getAmount());
        if (popupMenu != null) {
            holder.options.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    popupMenu.onMenuItemClickListener(holder.options, data, position);
                }
            });
        }
    }

    public void setPopupMenu(PopupMenu popupMenu) {
        this.popupMenu = popupMenu;
    }
}
