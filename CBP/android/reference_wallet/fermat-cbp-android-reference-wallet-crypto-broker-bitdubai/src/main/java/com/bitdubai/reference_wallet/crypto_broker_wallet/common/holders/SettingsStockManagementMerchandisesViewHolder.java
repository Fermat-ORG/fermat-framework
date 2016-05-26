package com.bitdubai.reference_wallet.crypto_broker_wallet.common.holders;

import android.view.View;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatButton;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.CurrencyTypes;
import com.bitdubai.fermat_api.layer.all_definition.util.BitcoinConverter;
import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.setting.CryptoBrokerWalletAssociatedSetting;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.interfaces.CryptoBrokerWalletModuleManager;
import com.bitdubai.reference_wallet.crypto_broker_wallet.R;


/**
 * Created by guillermo on 16/02/16.
 */
public class SettingsStockManagementMerchandisesViewHolder extends FermatViewHolder {
    private FermatButton merchandiseButton;
    CryptoBrokerWalletModuleManager walletManager;

    public SettingsStockManagementMerchandisesViewHolder(View itemView, CryptoBrokerWalletModuleManager walletManager) {
        super(itemView, 0);

        this.walletManager = walletManager;
        merchandiseButton = (FermatButton) itemView.findViewById(R.id.cbw_earning_currency_pair_button);
        merchandiseButton.setEnabled(false);
        merchandiseButton.setBackgroundColor(itemView.getResources().getColor(R.color.cbw_wizard_color));
    }

    public void bind(CryptoBrokerWalletAssociatedSetting data) {
        Currency merchandise = data.getMerchandise();
        String merchandiseCode = merchandise.getCode();

        try {
            double availableBalance = walletManager.getAvailableBalance(merchandise, "walletPublicKeyTest");
            if (merchandise.getType() == CurrencyTypes.CRYPTO && CryptoCurrency.BITCOIN.getCode().equals(merchandiseCode))
                availableBalance = BitcoinConverter.convert(availableBalance, BitcoinConverter.Currency.SATOSHI, BitcoinConverter.Currency.BITCOIN);

            merchandiseButton.setText(String.format("%1$s %2$s", merchandiseCode, availableBalance));
        } catch (Exception e) {
            merchandiseButton.setText(String.format("%1$s --", merchandiseCode));
        }
    }
}
