package com.bitdubai.reference_wallet.crypto_broker_wallet.common.holders;

import android.view.View;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.util.BitcoinConverter;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.setting.CryptoBrokerWalletAssociatedSetting;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.interfaces.CryptoBrokerWalletModuleManager;
import com.bitdubai.reference_wallet.crypto_broker_wallet.R;

import java.math.BigDecimal;
import java.text.DecimalFormat;


/**
 * Created by memo on 11/01/16.
 */
public class StockDestockViewHolder extends FermatViewHolder {

    //Constants
    private static final DecimalFormat moneyFormat = new DecimalFormat("#,##0.00");

    //UI
    private FermatTextView walletNameTextView;
    private FermatTextView amountTextView;

    //Managers
    CryptoBrokerWalletModuleManager moduleManager;


    public StockDestockViewHolder(View itemView, CryptoBrokerWalletModuleManager moduleManager) {
        super(itemView);

        walletNameTextView = (FermatTextView) itemView.findViewById(R.id.cbw_settings_wallet_name);
        amountTextView = (FermatTextView) itemView.findViewById(R.id.cbw_settings_amount);
        this.moduleManager = moduleManager;
    }


    public void bind(CryptoBrokerWalletAssociatedSetting data) {
        StringBuilder stringBuilder = new StringBuilder();
        walletNameTextView.setText(getPlatformTitle(data.getPlatform()));

        try {
            if (data.getPlatform() == Platforms.BANKING_PLATFORM) {
                stringBuilder.append(moneyFormat.format(moduleManager.getBalanceBankWallet(data.getWalletPublicKey(), data.getBankAccount())))
                        .append(" ")
                        .append(data.getMerchandise().getCode());
                amountTextView.setText(stringBuilder.toString());
            }
            if (data.getPlatform() == Platforms.CASH_PLATFORM) {
                stringBuilder.append(moneyFormat.format((moduleManager.getBalanceCashWallet(data.getWalletPublicKey()))))
                        .append(" ")
                        .append(data.getMerchandise().getCode());
                amountTextView.setText(stringBuilder.toString());
            }
            if (data.getPlatform() == Platforms.CRYPTO_CURRENCY_PLATFORM) {
                long bitcoinWalletBalance = moduleManager.getBalanceBitcoinWallet(data.getWalletPublicKey());
                double availableBalance = BitcoinConverter.convert(bitcoinWalletBalance, BitcoinConverter.Currency.SATOSHI, BitcoinConverter.Currency.BITCOIN);
                stringBuilder.append(moneyFormat.format((new BigDecimal(availableBalance))))
                        .append(" ")
                        .append(data.getMerchandise().getCode());
                amountTextView.setText(stringBuilder.toString());
            }
        } catch (Exception e) {
            amountTextView.setText("Balance: --");
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
