package com.bitdubai.reference_wallet.crypto_broker_wallet.common.adapters;

import android.content.Context;
import android.view.View;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.setting.CryptoBrokerWalletAssociatedSetting;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.interfaces.CryptoBrokerWalletManager;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.interfaces.CryptoBrokerWalletModuleManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.InstalledWallet;
import com.bitdubai.reference_wallet.crypto_broker_wallet.R;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.holders.StockDestockViewHolder;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.holders.WalletViewHolder;

import java.util.List;

/**
 * Created by memo on 11/01/16.
 */
public class StockDestockAdapter extends FermatAdapter<CryptoBrokerWalletAssociatedSetting, StockDestockViewHolder> {

    CryptoBrokerWalletManager moduleManager;

    public StockDestockAdapter(Context context, List<CryptoBrokerWalletAssociatedSetting> dataSet,CryptoBrokerWalletManager moduleManager) {
        super(context, dataSet);
        this.moduleManager = moduleManager;
    }

    @Override
    protected StockDestockViewHolder createHolder(View itemView, int type) {
        return new StockDestockViewHolder(itemView,moduleManager);
    }

    @Override
    protected int getCardViewResource() {
        return R.layout.cbw_settings_recycler_view_item;
    }

    @Override
    protected void bindHolder(StockDestockViewHolder holder, CryptoBrokerWalletAssociatedSetting data, int position) {
        holder.bind(data);
    }
}
