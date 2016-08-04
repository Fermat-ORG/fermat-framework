package com.bitdubai.reference_wallet.crypto_broker_wallet.common.holders;

import android.view.View;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatButton;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.CurrencyTypes;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.util.BitcoinConverter;
import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.setting.CryptoBrokerWalletAssociatedSetting;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.interfaces.CryptoBrokerWalletModuleManager;
import com.bitdubai.reference_wallet.crypto_broker_wallet.R;

import java.text.DecimalFormat;
import java.util.List;


/**
 * Created by guillermo on 16/02/16.
 */
public class SettingsStockManagementMerchandisesViewHolder extends FermatViewHolder {
//    private FermatButton merchandiseButton;
    private FermatTextView walletNameTextView;
    private FermatTextView amountTextView;
    CryptoBrokerWalletModuleManager walletManager;
    private static final DecimalFormat moneyFormat = new DecimalFormat("#,##0.00");


    public SettingsStockManagementMerchandisesViewHolder(View itemView, CryptoBrokerWalletModuleManager walletManager) {
        super(itemView, 0);

//        this.walletManager = walletManager;
//        merchandiseButton = (FermatButton) itemView.findViewById(R.id.cbw_stock_management_button);
//        merchandiseButton.setEnabled(false);
        walletNameTextView = (FermatTextView) itemView.findViewById(R.id.cbw_settings_wallet_stock_name);
        amountTextView = (FermatTextView) itemView.findViewById(R.id.cbw_settings_stock_amount);
        this.walletManager = walletManager;
    }

    public void bind(CryptoBrokerWalletAssociatedSetting data) {
        String merchandiseCode = data.getMerchandise().getCode();
        walletNameTextView.setText(getPlatformTitle(data.getPlatform()));

        try {
            double availableBalance = walletManager.getAvailableBalance(data.getMerchandise(), "walletPublicKeyTest");
            if (data.getMerchandise().getType() == CurrencyTypes.CRYPTO && CryptoCurrency.BITCOIN.getCode().equals(merchandiseCode))
                availableBalance = BitcoinConverter.convert(availableBalance, BitcoinConverter.Currency.SATOSHI, BitcoinConverter.Currency.BITCOIN);

            amountTextView.setText(String.format("%1$s %2$s", moneyFormat.format(availableBalance), merchandiseCode));
            amountTextView.invalidate();
        } catch (Exception e) {
            amountTextView.setText(String.format("%1$s --", merchandiseCode));
        }
    }

    private String getPlatformTitle(Platforms platform) {
        if (platform.equals(Platforms.BANKING_PLATFORM))
            return "Bank Wallet";
        if (platform.equals(Platforms.CASH_PLATFORM))
            return "Cash Wallet";

        return "Crypto Wallet";
    }
}
