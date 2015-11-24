package com.bitdubai.fermat_dap_android_sub_app_asset_factory_bitdubai.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
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
        return R.layout.dap_row_asset;
    }

    @Override
    protected void bindHolder(final AssetHolder holder, final AssetFactory data, final int position) {
        holder.itemView.setVisibility(View.VISIBLE);
        switch (data.getState()) {
            case DRAFT:
                renderDraf(holder, data, position);
                break;
            case FINAL:
                renderFinal(holder, data, position);
                break;
            case PENDING_FINAL:
                renderPendingFinal(holder, data, position);
                break;
            default:
                holder.itemView.setVisibility(View.INVISIBLE);
                break;
        }
    }

    private void renderPendingFinal(AssetHolder holder, AssetFactory data, int position) {
        holder.rowView.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
        holder.state.setTextColor(ContextCompat.getColor(context, R.color.state_color_publishing));
        holder.state.setBackgroundResource(R.drawable.white);
        holder.name.setTextColor(ContextCompat.getColor(context, R.color.white_asset_name));
        holder.amount.setTextColor(ContextCompat.getColor(context, R.color.white_amount));
        holder.bitcoins.setTextColor(ContextCompat.getColor(context, R.color.white_bitcoins));
        holder.name.setText(data.getName() != null ? data.getName() : context.getString(R.string.app_unnamed));
        holder.state.setText(R.string.home_asset_state_publishing);
        holder.amount.setText(String.format(context.getString(R.string.home_row_asset_amount), data.getQuantity()));
        holder.bitcoins.setText(String.format(context.getString(R.string.home_row_asset_bitcoins), data.getAmount()));
    }

    private void renderFinal(AssetHolder holder, AssetFactory data, int position) {
        holder.rowView.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
        holder.state.setTextColor(ContextCompat.getColor(context, R.color.state_color_publishing));
        holder.state.setBackgroundResource(R.drawable.white);
        holder.name.setTextColor(ContextCompat.getColor(context, R.color.white_asset_name));
        holder.amount.setTextColor(ContextCompat.getColor(context, R.color.white_amount));
        holder.bitcoins.setTextColor(ContextCompat.getColor(context, R.color.white_bitcoins));
        holder.name.setText(data.getName() != null ? data.getName() : context.getString(R.string.app_unnamed));
        holder.state.setText(R.string.home_asset_state_published);
        holder.amount.setText(String.format(context.getString(R.string.home_row_asset_amount), data.getQuantity()));
        holder.bitcoins.setText(String.format(context.getString(R.string.home_row_asset_bitcoins), data.getAmount()));
    }

    private void renderDraf(AssetHolder holder, AssetFactory data, int position) {
        holder.rowView.setBackgroundColor(ContextCompat.getColor(context, R.color.blue));
        holder.state.setTextColor(ContextCompat.getColor(context, R.color.state_color_editable));
        holder.state.setBackgroundResource(R.drawable.blue);
        holder.name.setTextColor(ContextCompat.getColor(context, R.color.blue_asset_name));
        holder.amount.setTextColor(ContextCompat.getColor(context, R.color.blue_amount));
        holder.bitcoins.setTextColor(ContextCompat.getColor(context, R.color.blue_bitcoins));
        holder.name.setText(data.getName() != null ? data.getName() : context.getString(R.string.app_unnamed));
        holder.state.setText(R.string.home_asset_state_editable);
        holder.amount.setText(String.format(context.getString(R.string.home_row_asset_amount), data.getQuantity()));
        holder.bitcoins.setText(String.format(context.getString(R.string.home_row_asset_bitcoins), data.getAmount()));
    }
}
