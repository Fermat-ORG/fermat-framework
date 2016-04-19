package com.bitdubai.fermat_cbp_plugin.layer.middleware.matching_engine.developer.bitdubai.version_1.structure.earning_transfer_apliers;

import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletsPublicKeys;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.exceptions.CantTransferEarningsToWalletException;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.interfaces.EarningToWalletTransaction;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.interfaces.EarningToWalletTransferApplier;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.interfaces.EarningTransaction;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.interfaces.EarningsPair;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantGetCryptoBrokerWalletSettingException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CryptoBrokerWalletNotFoundException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.CryptoBrokerWallet;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.CryptoBrokerWalletManager;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.setting.CryptoBrokerWalletAssociatedSetting;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.setting.CryptoBrokerWalletSetting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by nelsonalfo on 18/04/16.
 */
public class EarningToWalletTransactionImpl implements EarningToWalletTransaction {
    final private String brokerWalletPublicKey = WalletsPublicKeys.CBP_CRYPTO_BROKER_WALLET.getCode();

    final private CryptoBrokerWalletManager cryptoBrokerWalletManager;
    private HashMap<Platforms, EarningToWalletTransferApplier> transferAppliers;
    private List<CryptoBrokerWalletAssociatedSetting> associatedWallets;

    public EarningToWalletTransactionImpl(CryptoBrokerWalletManager walletManager) {

        this.cryptoBrokerWalletManager = walletManager;
        transferAppliers = new HashMap<>();
        associatedWallets = new ArrayList<>();
    }

    @Override
    public void addTransferApplier(EarningToWalletTransferApplier transferApplier) {
        transferApplier.setAssociatedWallets(getAssociatedWallets(brokerWalletPublicKey));
        transferAppliers.put(transferApplier.getPlatform(), transferApplier);
    }

    @Override
    public void transferEarningsToEarningWallet(EarningsPair earningsPair, List<EarningTransaction> earningTransactions)
            throws CantTransferEarningsToWalletException {

        if (earningsPair == null)
            throw new CantTransferEarningsToWalletException("Cant Transfer the earnings to the Earning Wallet", null,
                    "N/A", "The earningsPair parameter cannot be null");

        if (earningTransactions == null)
            throw new CantTransferEarningsToWalletException("Cant Transfer the earnings to the Earning Wallet", null,
                    "N/A", "The list of earning cannot be null");


        final String earningWalletPublicKey = earningsPair.getEarningsWallet().getPublicKey();

        Platforms earningWalletPlatform = getEarningWalletPlatform(earningWalletPublicKey, brokerWalletPublicKey);

        if (earningWalletPlatform != null && !earningTransactions.isEmpty()) {

            for (EarningTransaction earningTransaction : earningTransactions) {
                final EarningToWalletTransferApplier transferApplier = transferAppliers.get(earningWalletPlatform);

                transferApplier.applyTransference(earningsPair, earningTransaction, earningWalletPublicKey, brokerWalletPublicKey);
            }
        }
    }

    private Platforms getEarningWalletPlatform(String earningWalletPublicKey, String brokerWalletPublicKey)
            throws CantTransferEarningsToWalletException {

        List<CryptoBrokerWalletAssociatedSetting> associatedWallets = getAssociatedWallets(brokerWalletPublicKey);

        for (CryptoBrokerWalletAssociatedSetting associatedWallet : associatedWallets) {
            if (associatedWallet.getWalletPublicKey().equals(earningWalletPublicKey))
                return associatedWallet.getPlatform();
        }

        return null;
    }

    private List<CryptoBrokerWalletAssociatedSetting> getAssociatedWallets(String brokerWalletPublicKey) {

        if(associatedWallets.isEmpty()){
            try {
                CryptoBrokerWallet cryptoBrokerWallet = cryptoBrokerWalletManager.loadCryptoBrokerWallet(brokerWalletPublicKey);
                CryptoBrokerWalletSetting walletSettings = cryptoBrokerWallet.getCryptoWalletSetting();
                associatedWallets.addAll(walletSettings.getCryptoBrokerWalletAssociatedSettings());

            } catch (CantGetCryptoBrokerWalletSettingException | CryptoBrokerWalletNotFoundException ignore) {
            }
        }

        return associatedWallets;
    }
}
