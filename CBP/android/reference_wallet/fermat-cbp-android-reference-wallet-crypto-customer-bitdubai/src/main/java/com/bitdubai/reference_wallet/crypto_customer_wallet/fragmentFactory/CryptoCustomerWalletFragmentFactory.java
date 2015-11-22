package com.bitdubai.reference_wallet.crypto_customer_wallet.fragmentFactory;

import com.bitdubai.fermat_android_api.engine.FermatWalletFragmentFactory;
import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatWalletFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.enums.FermatFragmentsEnumType;
import com.bitdubai.fermat_android_api.layer.definition.wallet.exceptions.FragmentNotFoundException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.interfaces.WalletSettings;
import com.bitdubai.reference_wallet.crypto_customer_wallet.fragments.broker_list.BrokerListActivityFragment;
import com.bitdubai.reference_wallet.crypto_customer_wallet.fragments.common.CloseContractDetailsFragment;
import com.bitdubai.reference_wallet.crypto_customer_wallet.fragments.common.CloseNegotiationDetailsFragment;
import com.bitdubai.reference_wallet.crypto_customer_wallet.fragments.common.OpenContractDetailsFragment;
import com.bitdubai.reference_wallet.crypto_customer_wallet.fragments.common.OpenNegotiationDetailsFragment;
import com.bitdubai.reference_wallet.crypto_customer_wallet.fragments.common.StartNegotiationActivityFragment;
import com.bitdubai.reference_wallet.crypto_customer_wallet.fragments.contracts_history.ContractsHistoryActivityFragment;
import com.bitdubai.reference_wallet.crypto_customer_wallet.fragments.home.MarketRateStatisticsFragment;
import com.bitdubai.reference_wallet.crypto_customer_wallet.fragments.home.OpenContractsTabFragment;
import com.bitdubai.reference_wallet.crypto_customer_wallet.fragments.home.OpenNegotiationsTabFragment;
import com.bitdubai.reference_wallet.crypto_customer_wallet.fragments.settings.SettingsActivityFragment;
import com.bitdubai.reference_wallet.crypto_customer_wallet.session.CryptoCustomerWalletSession;

/**
 * Created by Matias Furszyfer on 2015.19.22..
 */
public class CryptoCustomerWalletFragmentFactory extends FermatWalletFragmentFactory<CryptoCustomerWalletSession, WalletSettings, CryptoCustomerWalletFragmentsEnumType> {

    @Override
    public FermatWalletFragment getFermatFragment(CryptoCustomerWalletFragmentsEnumType fragment) throws FragmentNotFoundException {
        if (fragment == null) {
            throw createFragmentNotFoundException(null);
        }

        switch (fragment) {
            case CBP_CRYPTO_CUSTOMER_WALLET_MARKET_RATE_STATISTICS:
                return MarketRateStatisticsFragment.newInstance();
            case CBP_CRYPTO_CUSTOMER_WALLET_OPEN_NEGOTIATIONS_TAB:
                return OpenNegotiationsTabFragment.newInstance();
            case CBP_CRYPTO_CUSTOMER_WALLET_OPEN_CONTRACTS_TAB:
                return OpenContractsTabFragment.newInstance();
            case CBP_CRYPTO_CUSTOMER_WALLET_CONTRACTS_HISTORY:
                return ContractsHistoryActivityFragment.newInstance();
            case CBP_CRYPTO_CUSTOMER_WALLET_BROKER_LIST:
                return BrokerListActivityFragment.newInstance();
            case CBP_CRYPTO_CUSTOMER_WALLET_SETTINGS:
                return SettingsActivityFragment.newInstance();

            case CBP_CRYPTO_CUSTOMER_WALLET_START_NEGOTIATION:
                return StartNegotiationActivityFragment.newInstance();
            case CBP_CRYPTO_CUSTOMER_WALLET_OPEN_NEGOTIATION_DETAILS:
                return OpenNegotiationDetailsFragment.newInstance();
            case CBP_CRYPTO_CUSTOMER_WALLET_CLOSE_NEGOTIATION_DETAILS:
                return CloseNegotiationDetailsFragment.newInstance();
            case CBP_CRYPTO_CUSTOMER_WALLET_OPEN_CONTRACT_DETAILS:
                return OpenContractDetailsFragment.newInstance();
            case CBP_CRYPTO_CUSTOMER_WALLET_CLOSE_CONTRACT_DETAILS:
                return CloseContractDetailsFragment.newInstance();
            default:
                throw createFragmentNotFoundException(fragment);
        }
    }

    @Override
    public CryptoCustomerWalletFragmentsEnumType getFermatFragmentEnumType(String key) {
        return CryptoCustomerWalletFragmentsEnumType.getValue(key);
    }

    private FragmentNotFoundException createFragmentNotFoundException(FermatFragmentsEnumType fragments) {
        String possibleReason = (fragments == null) ? "The parameter 'fragments' is NULL" : "Not found in switch block";
        String context = (fragments == null) ? "Null Value" : fragments.toString();

        return new FragmentNotFoundException("Fragment not found", new Exception(), context, possibleReason);
    }

}
