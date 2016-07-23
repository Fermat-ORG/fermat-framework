package com.bitdubai.reference_wallet.crypto_broker_wallet.fragmentFactory;

import com.bitdubai.fermat_android_api.engine.FermatFragmentFactory;
import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.enums.FermatFragmentsEnumType;
import com.bitdubai.fermat_android_api.layer.definition.wallet.exceptions.FragmentNotFoundException;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.interfaces.CryptoBrokerWalletModuleManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_resources.interfaces.WalletResourcesProviderManager;
import com.bitdubai.reference_wallet.crypto_broker_wallet.fragments.closed_negotiation_details.ClosedNegotiationDetailsFragment;
import com.bitdubai.reference_wallet.crypto_broker_wallet.fragments.common.CloseContractDetailsFragment;
import com.bitdubai.reference_wallet.crypto_broker_wallet.fragments.common.CreateNewLocationFragment;
import com.bitdubai.reference_wallet.crypto_broker_wallet.fragments.contract_detail.ContractDetailActivityFragment;
import com.bitdubai.reference_wallet.crypto_broker_wallet.fragments.contracts_history.ContractsHistoryActivityFragment;
import com.bitdubai.reference_wallet.crypto_broker_wallet.fragments.earnings.EarningsActivityFragment;
import com.bitdubai.reference_wallet.crypto_broker_wallet.fragments.home.MarketRateStatisticsFragment;
import com.bitdubai.reference_wallet.crypto_broker_wallet.fragments.home.OpenContractsTabFragment;
import com.bitdubai.reference_wallet.crypto_broker_wallet.fragments.home.OpenNegotiationsTabFragment;
import com.bitdubai.reference_wallet.crypto_broker_wallet.fragments.home.StockStatisticsFragment;
import com.bitdubai.reference_wallet.crypto_broker_wallet.fragments.open_negotiation_details.OpenNegotiationDetailsFragment;
import com.bitdubai.reference_wallet.crypto_broker_wallet.fragments.settings.SettingsActivityFragment;
import com.bitdubai.reference_wallet.crypto_broker_wallet.fragments.settings.SettingsBankAccountsFragment;
import com.bitdubai.reference_wallet.crypto_broker_wallet.fragments.settings.SettingsFeeManagementFragment;
import com.bitdubai.reference_wallet.crypto_broker_wallet.fragments.settings.SettingsMylocationsFragment;
import com.bitdubai.reference_wallet.crypto_broker_wallet.fragments.settings.SetttingsStockManagementFragment;
import com.bitdubai.reference_wallet.crypto_broker_wallet.fragments.wizard_pages.WizardPageOtherSettingsFragment;
import com.bitdubai.reference_wallet.crypto_broker_wallet.fragments.wizard_pages.WizardPageSetEarningsFragment;
import com.bitdubai.reference_wallet.crypto_broker_wallet.fragments.wizard_pages.WizardPageSetIdentityFragment;
import com.bitdubai.reference_wallet.crypto_broker_wallet.fragments.wizard_pages.WizardPageSetMerchandisesFragment;
import com.bitdubai.reference_wallet.crypto_broker_wallet.fragments.wizard_pages.WizardPageSetProvidersFragment;

/**
 * Return new instances of the fragments for this wallet
 * <p/>
 * Created by Nelson Ramirez on 2015-09-30
 */
public class CryptoBrokerWalletFragmentFactory extends FermatFragmentFactory<ReferenceAppFermatSession<CryptoBrokerWalletModuleManager>, WalletResourcesProviderManager, CryptoBrokerWalletFragmentsEnumType> {

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
                return ClosedNegotiationDetailsFragment.newInstance();
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
                return WizardPageOtherSettingsFragment.newInstance();
            case CBP_CRYPTO_BROKER_WALLET_CREATE_NEW_LOCATION_IN_WIZARD:
                return CreateNewLocationFragment.newInstance();
            case CBP_CRYPTO_BROKER_WALLET_OTHER_SETTINGS:
                return WizardPageOtherSettingsFragment.newInstance();
            case CBP_CRYPTO_BROKER_WALLET_CREATE_NEW_LOCATION_IN_SETTINGS:
                return CreateNewLocationFragment.newInstance();
            case CBP_CRYPTO_BROKER_WALLET_SETTINGS_MY_LOCATIONS:
                return SettingsMylocationsFragment.newInstance();
            case CBP_CRYPTO_BROKER_WALLET_SETTINGS_BANK_ACCOUNT:
                return SettingsBankAccountsFragment.newInstance();
            case CBP_CRYPTO_BROKER_WALLET_SETTINGS_STOCK_MERCHANDISES:
                return SetttingsStockManagementFragment.newInstance();
            case CBP_CRYPTO_BROKER_WALLET_SETTINGS_FEE_MANAGEMENT:
                return SettingsFeeManagementFragment.newInstance();
            case CBP_CRYPTO_BROKER_WALLET_CONTRACT_DETAILS:
                return ContractDetailActivityFragment.newInstance();
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