package com.bitdubai.reference_wallet.crypto_broker_wallet.common.adapters;

import android.content.Context;
import android.view.View;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.interfaces.CryptoBrokerWalletModuleManager;
import com.bitdubai.reference_wallet.crypto_broker_wallet.R;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.holders.SettingsStockManagementMerchandisesViewHolder;

import java.util.List;

/**
 * Created by guillermo on 16/02/16.
 */
public class SettingsStockManagementMerchandisesAdapter extends FermatAdapter<Currency, SettingsStockManagementMerchandisesViewHolder> {

    CryptoBrokerWalletModuleManager walletManager;
    SettingsStockManagementMerchandisesViewHolder viewHolder;

    public SettingsStockManagementMerchandisesAdapter(Context context, List<Currency> dataSet, CryptoBrokerWalletModuleManager walletManager) {
        super(context, dataSet);
        this.walletManager = walletManager;
    }

    @Override
    protected SettingsStockManagementMerchandisesViewHolder createHolder(View itemView, int type) {
        viewHolder = new SettingsStockManagementMerchandisesViewHolder(itemView, walletManager);
        return viewHolder;
    }

    @Override
    protected int getCardViewResource() {
        return R.layout.cbw_stock_management_item;
    }

    @Override
    protected void bindHolder(SettingsStockManagementMerchandisesViewHolder holder, Currency data, int position) {
        viewHolder.bind(data);
    }
}
