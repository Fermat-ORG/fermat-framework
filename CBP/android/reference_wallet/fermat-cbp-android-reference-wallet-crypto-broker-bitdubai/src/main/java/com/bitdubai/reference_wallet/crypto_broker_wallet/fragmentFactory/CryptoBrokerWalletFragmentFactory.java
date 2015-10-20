package com.bitdubai.reference_wallet.crypto_broker_wallet.fragmentFactory;

import android.app.Fragment;

import com.bitdubai.fermat_android_api.layer.definition.wallet.enums.FermatFragmentsEnumType;
import com.bitdubai.fermat_android_api.layer.definition.wallet.exceptions.FragmentNotFoundException;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.WalletFragmentFactory;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.WalletSession;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.interfaces.WalletSettings;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_resources.interfaces.WalletResourcesProviderManager;
import com.bitdubai.reference_wallet.crypto_broker_wallet.fragments.common.ContractDetailsFragment;
import com.bitdubai.reference_wallet.crypto_broker_wallet.fragments.common.DealDetailsFragment;
import com.bitdubai.reference_wallet.crypto_broker_wallet.fragments.contracts.ContractsActivityFragment;
import com.bitdubai.reference_wallet.crypto_broker_wallet.fragments.deals.DealsActivityFragment;
import com.bitdubai.reference_wallet.crypto_broker_wallet.fragments.home.OpenContractsTabFragment;
import com.bitdubai.reference_wallet.crypto_broker_wallet.fragments.home.OpenDealsTabFragment;
import com.bitdubai.reference_wallet.crypto_broker_wallet.fragments.home.MarketRateStatisticsFragment;
import com.bitdubai.reference_wallet.crypto_broker_wallet.fragments.home.StockStatisticsFragment;
import com.bitdubai.reference_wallet.crypto_broker_wallet.fragments.settings.SettingsActivityFragment;
import com.bitdubai.reference_wallet.crypto_broker_wallet.fragments.stock.StockPreferencesActivityFragment;

import static com.bitdubai.reference_wallet.crypto_broker_wallet.fragmentFactory.CryptoBrokerWalletFragmentsEnumType.CBP_CRYPTO_BROKER_WALLET_CONTRACTS;
import static com.bitdubai.reference_wallet.crypto_broker_wallet.fragmentFactory.CryptoBrokerWalletFragmentsEnumType.CBP_CRYPTO_BROKER_WALLET_CONTRACT_DETAILS;
import static com.bitdubai.reference_wallet.crypto_broker_wallet.fragmentFactory.CryptoBrokerWalletFragmentsEnumType.CBP_CRYPTO_BROKER_WALLET_DEALS;
import static com.bitdubai.reference_wallet.crypto_broker_wallet.fragmentFactory.CryptoBrokerWalletFragmentsEnumType.CBP_CRYPTO_BROKER_WALLET_DEAL_DETAILS;
import static com.bitdubai.reference_wallet.crypto_broker_wallet.fragmentFactory.CryptoBrokerWalletFragmentsEnumType.CBP_CRYPTO_BROKER_WALLET_OPEN_CONTRACTS_TAB;
import static com.bitdubai.reference_wallet.crypto_broker_wallet.fragmentFactory.CryptoBrokerWalletFragmentsEnumType.CBP_CRYPTO_BROKER_WALLET_OPEN_DEALS_TAB;
import static com.bitdubai.reference_wallet.crypto_broker_wallet.fragmentFactory.CryptoBrokerWalletFragmentsEnumType.CBP_CRYPTO_BROKER_WALLET_MARKET_RATE_STATISTICS;
import static com.bitdubai.reference_wallet.crypto_broker_wallet.fragmentFactory.CryptoBrokerWalletFragmentsEnumType.CBP_CRYPTO_BROKER_WALLET_SETTINGS;
import static com.bitdubai.reference_wallet.crypto_broker_wallet.fragmentFactory.CryptoBrokerWalletFragmentsEnumType.CBP_CRYPTO_BROKER_WALLET_STOCK_PREFERENCE;
import static com.bitdubai.reference_wallet.crypto_broker_wallet.fragmentFactory.CryptoBrokerWalletFragmentsEnumType.CBP_CRYPTO_BROKER_WALLET_STOCK_STATISTICS;

/**
 * Return new instances of the fragments for this wallet
 *
 * Created by Nelson Ramirez on 2015-09-30
 */
public class CryptoBrokerWalletFragmentFactory implements WalletFragmentFactory {

    @Override
    public Fragment getFragment(String code, WalletSession walletSession, WalletSettings WalletSettings, WalletResourcesProviderManager walletResourcesProviderManager) throws FragmentNotFoundException {
        CryptoBrokerWalletFragmentsEnumType fragment = CryptoBrokerWalletFragmentsEnumType.getValue(code);

        if (fragment == CBP_CRYPTO_BROKER_WALLET_MARKET_RATE_STATISTICS)
            return MarketRateStatisticsFragment.newInstance();
        if (fragment == CBP_CRYPTO_BROKER_WALLET_OPEN_DEALS_TAB)
            return OpenDealsTabFragment.newInstance();
        if (fragment == CBP_CRYPTO_BROKER_WALLET_OPEN_CONTRACTS_TAB)
            return OpenContractsTabFragment.newInstance();
        if (fragment == CBP_CRYPTO_BROKER_WALLET_STOCK_STATISTICS)
            return StockStatisticsFragment.newInstance();
        if (fragment == CBP_CRYPTO_BROKER_WALLET_DEAL_DETAILS)
            return DealDetailsFragment.newInstance();
        if (fragment == CBP_CRYPTO_BROKER_WALLET_CONTRACT_DETAILS)
            return ContractDetailsFragment.newInstance();
        if (fragment == CBP_CRYPTO_BROKER_WALLET_DEALS)
            return DealsActivityFragment.newInstance();
        if (fragment == CBP_CRYPTO_BROKER_WALLET_CONTRACTS)
            return ContractsActivityFragment.newInstance();
        if (fragment == CBP_CRYPTO_BROKER_WALLET_STOCK_PREFERENCE)
            return StockPreferencesActivityFragment.newInstance();
        if (fragment == CBP_CRYPTO_BROKER_WALLET_SETTINGS)
            return SettingsActivityFragment.newInstance();

        throw createFragmentNotFoundException(fragment);
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