package com.bitdubai.reference_wallet.crypto_broker_wallet.common.holders;

import android.view.View;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.util.BitcoinConverter;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.setting.CryptoBrokerWalletAssociatedSetting;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.interfaces.CryptoBrokerWalletModuleManager;
import com.bitdubai.reference_wallet.crypto_broker_wallet.R;


/**
 * Created by memo on 11/01/16.
 */
public class StockDestockViewHolder extends FermatViewHolder {
    private FermatTextView title;
    private FermatTextView subTitle1;
    private FermatTextView subTitle2;
    CryptoBrokerWalletModuleManager moduleManager;

    public StockDestockViewHolder(View itemView, CryptoBrokerWalletModuleManager moduleManager) {
        super(itemView);
        title = (FermatTextView) itemView.findViewById(R.id.cbw_settings_title);
        subTitle1 = (FermatTextView) itemView.findViewById(R.id.cbw_settings_sub_title1);
        subTitle2 = (FermatTextView) itemView.findViewById(R.id.cbw_settings_sub_title2);
        this.moduleManager = moduleManager;
    }


    public void bind(CryptoBrokerWalletAssociatedSetting data) {
        System.out.println("data = " + data.getPlatform().getCode() + "    " + data.getWalletPublicKey() + "         " + data.getMerchandise().getCode());
        subTitle1.setText(getPlatformTitle(data.getPlatform()));
        title.setText(data.getMerchandise().getCode());
        try {
            if (data.getPlatform() == Platforms.BANKING_PLATFORM) {
                //subTitle2.setText("balance: "+moduleManager.getAvailableBalance(data.getMerchandise(),"walletPublicKeyTest"));
                subTitle2.setText("" + moduleManager.getBalanceBankWallet(data.getWalletPublicKey(), data.getBankAccount()));
            }
            if (data.getPlatform() == Platforms.CASH_PLATFORM) {
                subTitle2.setText("" + moduleManager.getBalanceCashWallet(data.getWalletPublicKey()));
            }
            if (data.getPlatform() == Platforms.CRYPTO_CURRENCY_PLATFORM) {
                long balanceBitcoinWallet = moduleManager.getBalanceBitcoinWallet(data.getWalletPublicKey());
                double availableBalance = BitcoinConverter.convert(balanceBitcoinWallet, BitcoinConverter.Currency.SATOSHI, BitcoinConverter.Currency.BITCOIN);
                subTitle2.setText("" + availableBalance);
            }

        } catch (Exception e) {
            subTitle2.setText("balance: --");
        }
    }

    private String getPlatformTitle(Platforms platform) {
        if (platform.equals(Platforms.BANKING_PLATFORM))
            return "Bank";
        if (platform.equals(Platforms.CASH_PLATFORM))
            return "Cash";

        return "Crypto";
    }
}
