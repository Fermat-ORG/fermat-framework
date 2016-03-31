package com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.v3.common.adapters;

import android.content.Context;
import android.view.View;
import android.widget.Filter;
import android.widget.Filterable;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.FermatSession;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.R;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.sessions.AssetUserSession;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.v2.models.Asset;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.v3.common.filters.HomeCardAdapterFilter;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.v3.common.holders.HomeCardViewHolder;
import com.bitdubai.fermat_dap_api.layer.dap_module.wallet_asset_user.interfaces.AssetUserWalletSubAppModuleManager;

import java.util.List;

/**
 * Created by frank on 12/8/15.
 */
public class HomeCardAdapter extends FermatAdapter<Asset, HomeCardViewHolder> implements Filterable {

    private AssetUserWalletSubAppModuleManager manager;
    private AssetUserSession assetUserSession;

    private View.OnClickListener onClickListenerRedeem;
    private View.OnClickListener onClickListenerTransfer;
    private View.OnClickListener onClickListenerAppropriate;
    private View.OnClickListener onClickListenerSell;
    private View.OnClickListener onClickListenerTransactions;

    public HomeCardAdapter(Context context, List<Asset> digitalAssets, AssetUserWalletSubAppModuleManager manager,
                           FermatSession appSession, View.OnClickListener onClickListenerRedeem, View.OnClickListener onClickListenerTransfer,
                           View.OnClickListener onClickListenerAppropriate, View.OnClickListener onClickListenerSell,
                           View.OnClickListener onClickListenerTransactions) {
        super(context, digitalAssets);
        this.manager = manager;
        this.dataSet = digitalAssets;
        this.assetUserSession = (AssetUserSession) appSession;
        this.onClickListenerRedeem = onClickListenerRedeem;
        this.onClickListenerTransfer = onClickListenerTransfer;
        this.onClickListenerAppropriate = onClickListenerAppropriate;
        this.onClickListenerSell = onClickListenerSell;
        this.onClickListenerTransactions = onClickListenerTransactions;
    }

    @Override
    protected HomeCardViewHolder createHolder(View itemView, int type) {
        return new HomeCardViewHolder(itemView, manager, context);
    }

    @Override
    protected int getCardViewResource() {
        return R.layout.dap_v3_wallet_asset_user_card;
    }

    @Override
    protected void bindHolder(HomeCardViewHolder holder, Asset data, int position) {
        assetUserSession.setData("asset_data", data);
        holder.bind(data, onClickListenerRedeem, onClickListenerTransfer, onClickListenerAppropriate,
                onClickListenerSell, onClickListenerTransactions);
    }

    @Override
    public Filter getFilter() {
        return new HomeCardAdapterFilter(this.dataSet, this);
    }
}
