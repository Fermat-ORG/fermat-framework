package com.bitdubai.reference_wallet.crypto_broker_wallet.fragmentFactory;

import com.bitdubai.fermat_android_api.engine.FermatWalletFragmentFactory;
import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatWalletFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.enums.FermatFragmentsEnumType;
import com.bitdubai.fermat_android_api.layer.definition.wallet.exceptions.FragmentNotFoundException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.interfaces.WalletSettings;
import com.bitdubai.reference_wallet.crypto_broker_wallet.fragments.common.CloseContractDetailsFragment;
import com.bitdubai.reference_wallet.crypto_broker_wallet.fragments.common.CloseNegotiationDetailsFragment;
import com.bitdubai.reference_wallet.crypto_broker_wallet.fragments.common.OpenContractDetailsFragment;
import com.bitdubai.reference_wallet.crypto_broker_wallet.fragments.common.OpenNegotiationDetailsFragment;
import com.bitdubai.reference_wallet.crypto_broker_wallet.fragments.contracts_history.ContractsHistoryActivityFragment;
import com.bitdubai.reference_wallet.crypto_broker_wallet.fragments.earnings.EarningsActivityFragment;
import com.bitdubai.reference_wallet.crypto_broker_wallet.fragments.home.MarketRateStatisticsFragment;
import com.bitdubai.reference_wallet.crypto_broker_wallet.fragments.home.OpenContractsTabFragment;
import com.bitdubai.reference_wallet.crypto_broker_wallet.fragments.home.OpenNegotiationsTabFragment;
import com.bitdubai.reference_wallet.crypto_broker_wallet.fragments.home.StockStatisticsFragment;
import com.bitdubai.reference_wallet.crypto_broker_wallet.fragments.settings.SettingsActivityFragment;
import com.bitdubai.reference_wallet.crypto_broker_wallet.session.CryptoBrokerWalletSession;

/**
 * Return new instances of the fragments for this wallet
 * <p/>
 * Created by Nelson Ramirez on 2015-09-30
 */
public class CryptoBrokerWalletFragmentFactory extends FermatWalletFragmentFactory<CryptoBrokerWalletSession, WalletSettings, CryptoBrokerWalletFragmentsEnumType> {

    @Override
    public FermatWalletFragment getFermatFragment(CryptoBrokerWalletFragmentsEnumType fragment) throws FragmentNotFoundException {
        if (fragment == null) {
            throw createFragmentNotFoundException(null);
        }

        switch (fragment) {
            case CBP_CRYPTO_BROKER_WALLET_OPEN_NEGOTIATIONS_TAB:
                return OpenNegotiationsTabFragment.newInstance();
            case CBP_CRYPTO_BROKER_WALLET_OPEN_CONTRACTS_TAB:
                return OpenContractsTabFragment.newInstance();
            case CBP_CRYPTO_BROKER_WALLET_MARKET_RATE_STATISTICS:
                return MarketRateStatisticsFragment.newInstance();
            case CBP_CRYPTO_BROKER_WALLET_STOCK_STATISTICS:
                return StockStatisticsFragment.newInstance();
            case CBP_CRYPTO_BROKER_WALLET_OPEN_NEGOTIATION_DETAILS:
                return OpenNegotiationDetailsFragment.newInstance();
            case CBP_CRYPTO_BROKER_WALLET_CLOSE_NEGOTIATION_DETAILS:
                return CloseNegotiationDetailsFragment.newInstance();
            case CBP_CRYPTO_BROKER_WALLET_OPEN_CONTRACT_DETAILS:
                return OpenContractDetailsFragment.newInstance();
            case CBP_CRYPTO_BROKER_WALLET_CLOSE_CONTRACT_DETAILS:
                return CloseContractDetailsFragment.newInstance();
            case CBP_CRYPTO_BROKER_WALLET_CONTRACTS_HISTORY:
                return ContractsHistoryActivityFragment.newInstance();
            case CBP_CRYPTO_BROKER_WALLET_EARNINGS:
                return EarningsActivityFragment.newInstance();
            case CBP_CRYPTO_BROKER_WALLET_SETTINGS:
                return SettingsActivityFragment.newInstance();
            default:
                throw createFragmentNotFoundException(fragment);
        }
    }

    @Override
    public CryptoBrokerWalletFragmentsEnumType getFermatFragmentEnumType(String key) {
        return CryptoBrokerWalletFragmentsEnumType.getValue(key);
    }

    private FragmentNotFoundException createFragmentNotFoundException(FermatFragmentsEnumType fragments) {
        String possibleReason, context;

        if (fragments == null) {
            possibleReason = "The parameter 'fragments' is NULL";
            context = "Null Value";
        } else {
            possibleReason = "Not found in switch block";
            context = fragments.toString();
        }

        return new FragmentNotFoundException("Fragment not found", new Exception(), context, possibleReason);
    }
}