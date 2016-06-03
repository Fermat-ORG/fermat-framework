package com.bitdubai.fermat_cbp_plugin.layer.middleware.matching_engine.developer.bitdubai.version_1.structure.earning_extraction;

import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletsPublicKeys;
import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.enums.EarningTransactionState;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.exceptions.CantExtractEarningsException;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.exceptions.CantMarkEarningTransactionAsExtractedException;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.exceptions.EarningTransactionNotFoundException;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.interfaces.EarningExtractorManager;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.interfaces.EarningExtractor;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.interfaces.EarningTransaction;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.interfaces.EarningsPair;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantGetCryptoBrokerWalletSettingException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CryptoBrokerWalletNotFoundException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.CryptoBrokerWallet;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.CryptoBrokerWalletManager;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.setting.CryptoBrokerWalletAssociatedSetting;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.setting.CryptoBrokerWalletSetting;
import com.bitdubai.fermat_cbp_plugin.layer.middleware.matching_engine.developer.bitdubai.version_1.database.MatchingEngineMiddlewareDao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by nelsonalfo on 18/04/16.
 */
public class EarningExtractorManagerImpl implements EarningExtractorManager {
    private static final String BROKER_WALLET_PUBLIC_KEY = WalletsPublicKeys.CBP_CRYPTO_BROKER_WALLET.getCode();

    final private CryptoBrokerWalletManager cryptoBrokerWalletManager;
    private MatchingEngineMiddlewareDao dao;
    private HashMap<Platforms, EarningExtractor> earningExtractors;
    private List<CryptoBrokerWalletAssociatedSetting> associatedWallets;

    public EarningExtractorManagerImpl(CryptoBrokerWalletManager walletManager, MatchingEngineMiddlewareDao dao) {
        this.cryptoBrokerWalletManager = walletManager;
        this.dao = dao;
        earningExtractors = new HashMap<>();
        associatedWallets = new ArrayList<>();
    }

    @Override
    public void addEarningExtractor(EarningExtractor transferApplier) {
        transferApplier.setAssociatedWallets(getAssociatedWallets(BROKER_WALLET_PUBLIC_KEY));
        earningExtractors.put(transferApplier.getPlatform(), transferApplier);
    }

    @Override
    public boolean extractEarnings(EarningsPair earningsPair, List<EarningTransaction> earningTransactions) throws CantExtractEarningsException {

        if (earningsPair == null)
            throw new CantExtractEarningsException("Verifying parameters", "The earningsPair parameter cannot be null");

        if (earningTransactions == null)
            throw new CantExtractEarningsException("Verifying parameters", "The list of earning cannot be null");

        if (earningExtractors.isEmpty())
            throw new CantExtractEarningsException("Verifying the Earning Extractors", "No Earning Extractors added");

        if (earningTransactions.isEmpty())
            return false;

        final String earningWalletPublicKey = earningsPair.getEarningsWallet().getPublicKey();
        final Platforms earningWalletPlatform = getEarningWalletPlatform(earningWalletPublicKey, BROKER_WALLET_PUBLIC_KEY);

        if (earningWalletPlatform != null) {
            final Currency earningCurrency = earningsPair.getEarningCurrency();

            float earningsAmount = 0;
            for (EarningTransaction earningTransaction : earningTransactions) {
                final EarningTransactionState state = earningTransaction.getState();
                final Currency currency = earningTransaction.getEarningCurrency();

                if (currency == earningCurrency && state != EarningTransactionState.EXTRACTED)
                    earningsAmount += earningTransaction.getAmount();
            }

            if (earningsAmount > 0) {
                markEarningTransactionsAsExtracted(earningTransactions, earningCurrency);

                final EarningExtractor earningExtractor = earningExtractors.get(earningWalletPlatform);
                earningExtractor.applyEarningExtraction(earningsPair, earningsAmount, earningWalletPublicKey, BROKER_WALLET_PUBLIC_KEY);

                return true;
            }
        }

        return false;
    }

    private Platforms getEarningWalletPlatform(String earningWalletPublicKey, String brokerWalletPublicKey) {

        List<CryptoBrokerWalletAssociatedSetting> associatedWallets = getAssociatedWallets(brokerWalletPublicKey);

        for (CryptoBrokerWalletAssociatedSetting associatedWallet : associatedWallets) {
            if (associatedWallet.getWalletPublicKey().equals(earningWalletPublicKey))
                return associatedWallet.getPlatform();
        }

        return null;
    }

    private List<CryptoBrokerWalletAssociatedSetting> getAssociatedWallets(String brokerWalletPublicKey) {

        if (associatedWallets.isEmpty()) {
            try {
                CryptoBrokerWallet cryptoBrokerWallet = cryptoBrokerWalletManager.loadCryptoBrokerWallet(brokerWalletPublicKey);
                CryptoBrokerWalletSetting walletSettings = cryptoBrokerWallet.getCryptoWalletSetting();
                associatedWallets.addAll(walletSettings.getCryptoBrokerWalletAssociatedSettings());

            } catch (CantGetCryptoBrokerWalletSettingException | CryptoBrokerWalletNotFoundException ignore) {
            }
        }

        return associatedWallets;
    }

    private void markEarningTransactionsAsExtracted(List<EarningTransaction> earningTransactions, Currency earningCurrency) throws CantExtractEarningsException {
        try {
            for (EarningTransaction earningTransaction : earningTransactions)
                if (earningTransaction.getEarningCurrency() == earningCurrency){
                    dao.markEarningTransactionAsExtracted(earningTransaction.getId());
                    earningTransaction.markAsExtracted();
                }


        } catch (EarningTransactionNotFoundException e) {
            throw new CantExtractEarningsException(e, "Trying to get the earning transaction in database to marked as EXTRACTED",
                    "Verify the earning transaction ID is in database");

        } catch (CantMarkEarningTransactionAsExtractedException e) {
            throw new CantExtractEarningsException(e, "Trying to mark the earning transaction has EXTRACTED",
                    "Verify the stack trace for more info");
        }
    }
}
