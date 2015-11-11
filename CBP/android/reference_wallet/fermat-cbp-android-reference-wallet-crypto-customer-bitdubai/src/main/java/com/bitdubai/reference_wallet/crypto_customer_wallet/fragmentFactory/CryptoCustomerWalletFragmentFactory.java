package com.bitdubai.reference_wallet.crypto_customer_wallet.fragmentFactory;

import android.app.Fragment;

import com.bitdubai.fermat_android_api.engine.FermatWalletFragmentFactory;
import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatWalletFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.enums.FermatFragmentsEnumType;
import com.bitdubai.fermat_android_api.layer.definition.wallet.exceptions.FragmentNotFoundException;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.WalletFragmentFactory;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.WalletSession;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.interfaces.WalletSettings;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_resources.interfaces.WalletResourcesProviderManager;
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

import static com.bitdubai.reference_wallet.crypto_customer_wallet.fragmentFactory.CryptoCustomerWalletFragmentsEnumType.*;

/**
 * Created by Matias Furszyfer on 2015.19.22..
 */
public class CryptoCustomerWalletFragmentFactory extends FermatWalletFragmentFactory<CryptoCustomerWalletSession,WalletSettings,CryptoCustomerWalletFragmentsEnumType> {



    @Override
    public FermatWalletFragment getFermatFragment(CryptoCustomerWalletFragmentsEnumType fragment) throws FragmentNotFoundException {
        if (fragment == CBP_CRYPTO_CUSTOMER_WALLET_MARKET_RATE_STATISTICS)
            return MarketRateStatisticsFragment.newInstance();
        if (fragment == CBP_CRYPTO_CUSTOMER_WALLET_OPEN_NEGOTIATIONS_TAB)
            return OpenNegotiationsTabFragment.newInstance();
        if (fragment == CBP_CRYPTO_CUSTOMER_WALLET_OPEN_CONTRACTS_TAB)
            return OpenContractsTabFragment.newInstance();
        if (fragment == CBP_CRYPTO_CUSTOMER_WALLET_CONTRACTS_HISTORY)
            return ContractsHistoryActivityFragment.newInstance();
        if (fragment == CBP_CRYPTO_CUSTOMER_WALLET_BROKER_LIST)
            return BrokerListActivityFragment.newInstance();
        if (fragment == CBP_CRYPTO_CUSTOMER_WALLET_SETTINGS)
            return SettingsActivityFragment.newInstance();

        if (fragment == CBP_CRYPTO_CUSTOMER_WALLET_START_NEGOTIATION)
            return StartNegotiationActivityFragment.newInstance();
        if (fragment == CBP_CRYPTO_CUSTOMER_WALLET_OPEN_NEGOTIATION_DETAILS)
            return OpenNegotiationDetailsFragment.newInstance();
        if (fragment == CBP_CRYPTO_CUSTOMER_WALLET_CLOSE_NEGOTIATION_DETAILS)
            return CloseNegotiationDetailsFragment.newInstance();
        if (fragment == CBP_CRYPTO_CUSTOMER_WALLET_OPEN_CONTRACT_DETAILS)
            return OpenContractDetailsFragment.newInstance();
        if (fragment == CBP_CRYPTO_CUSTOMER_WALLET_CLOSE_CONTRACT_DETAILS)
            return CloseContractDetailsFragment.newInstance();

        throw createFragmentNotFoundException(fragment);
    }

    @Override
    public CryptoCustomerWalletFragmentsEnumType getFermatFragmentEnumType(String key) {
        return CryptoCustomerWalletFragmentsEnumType.getValue(key);
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
