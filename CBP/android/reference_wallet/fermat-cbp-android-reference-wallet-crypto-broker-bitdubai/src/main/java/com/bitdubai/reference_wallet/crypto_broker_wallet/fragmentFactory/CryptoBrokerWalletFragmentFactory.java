package com.bitdubai.reference_wallet.crypto_broker_wallet.fragmentFactory;

import com.bitdubai.fermat_android_api.engine.FermatFragmentFactory;
import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.enums.FermatFragmentsEnumType;
import com.bitdubai.fermat_android_api.layer.definition.wallet.exceptions.FragmentNotFoundException;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_resources.interfaces.WalletResourcesProviderManager;
import com.bitdubai.reference_wallet.crypto_broker_wallet.fragments.common.CloseContractDetailsFragment;
import com.bitdubai.reference_wallet.crypto_broker_wallet.fragments.common.CloseNegotiationDetailsFragment;
import com.bitdubai.reference_wallet.crypto_broker_wallet.fragments.common.CreateNewLocationFragment;
import com.bitdubai.reference_wallet.crypto_broker_wallet.fragments.common.OpenContractDetailsFragment;
import com.bitdubai.reference_wallet.crypto_broker_wallet.fragments.open_negotiation_details.OpenNegotiationDetailsFragment;
import com.bitdubai.reference_wallet.crypto_broker_wallet.fragments.contracts_history.ContractsHistoryActivityFragment;
import com.bitdubai.reference_wallet.crypto_broker_wallet.fragments.earnings.EarningsActivityFragment;
import com.bitdubai.reference_wallet.crypto_broker_wallet.fragments.home.MarketRateStatisticsFragment;
import com.bitdubai.reference_wallet.crypto_broker_wallet.fragments.home.OpenContractsTabFragment;
import com.bitdubai.reference_wallet.crypto_broker_wallet.fragments.home.OpenNegotiationsTabFragment;
import com.bitdubai.reference_wallet.crypto_broker_wallet.fragments.home.StockStatisticsFragment;
import com.bitdubai.reference_wallet.crypto_broker_wallet.fragments.settings.SettingsMylocationsFragment;
import com.bitdubai.reference_wallet.crypto_broker_wallet.fragments.settings.SettingsActivityFragment;
import com.bitdubai.reference_wallet.crypto_broker_wallet.fragments.wizard_pages.WizardPageSetBankAccountsFragment;
import com.bitdubai.reference_wallet.crypto_broker_wallet.fragments.wizard_pages.WizardPageSetEarningsFragment;
import com.bitdubai.reference_wallet.crypto_broker_wallet.fragments.wizard_pages.WizardPageSetIdentityFragment;
import com.bitdubai.reference_wallet.crypto_broker_wallet.fragments.wizard_pages.WizardPageSetLocationsFragment;
import com.bitdubai.reference_wallet.crypto_broker_wallet.fragments.wizard_pages.WizardPageSetMerchandisesFragment;
import com.bitdubai.reference_wallet.crypto_broker_wallet.fragments.wizard_pages.WizardPageSetProvidersFragment;
import com.bitdubai.reference_wallet.crypto_broker_wallet.session.CryptoBrokerWalletSession;

/**
 * Return new instances of the fragments for this wallet
 * <p/>
 * Created by Nelson Ramirez on 2015-09-30
 */
public class CryptoBrokerWalletFragmentFactory extends FermatFragmentFactory<CryptoBrokerWalletSession,WalletResourcesProviderManager, CryptoBrokerWalletFragmentsEnumType> {

    @Override
    public AbstractFermatFragment getFermatFragment(CryptoBrokerWalletFragmentsEnumType fragment) throws FragmentNotFoundException {
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
            case CBP_CRYPTO_BROKER_WALLET_SET_IDENTITY:
                return WizardPageSetIdentityFragment.newInstance();
            case CBP_CRYPTO_BROKER_WALLET_SET_MERCHANDISES:
                return WizardPageSetMerchandisesFragment.newInstance();
            case CBP_CRYPTO_BROKER_WALLET_SET_EARNINGS:
                return WizardPageSetEarningsFragment.newInstance();
            case CBP_CRYPTO_BROKER_WALLET_SET_PROVIDERS:
                return WizardPageSetProvidersFragment.newInstance();
            case CBP_CRYPTO_BROKER_WALLET_SET_LOCATIONS:
                return WizardPageSetLocationsFragment.newInstance();
            case CBP_CRYPTO_BROKER_WALLET_SET_BANK_ACCOUNT:
                return WizardPageSetBankAccountsFragment.newInstance();
            case CBP_CRYPTO_BROKER_WALLET_CREATE_NEW_LOCATION_IN_WIZARD:
                return CreateNewLocationFragment.newInstance();
            case CBP_CRYPTO_BROKER_WALLET_SETTINGS_MY_LOCATIONS:
                return SettingsMylocationsFragment.newInstance();
            case CBP_CRYPTO_BROKER_WALLET_CREATE_NEW_LOCATION_IN_SETTINGS:
                return CreateNewLocationFragment.newInstance();
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