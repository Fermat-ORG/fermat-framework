package com.bitdubai.reference_wallet.crypto_customer_wallet.fragmentFactory;

import com.bitdubai.fermat_android_api.engine.FermatFragmentFactory;
import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.enums.FermatFragmentsEnumType;
import com.bitdubai.fermat_android_api.layer.definition.wallet.exceptions.FragmentNotFoundException;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.interfaces.CryptoCustomerWalletModuleManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_resources.interfaces.WalletResourcesProviderManager;
import com.bitdubai.reference_wallet.crypto_customer_wallet.fragments.broker_list.BrokerListActivityFragment;
import com.bitdubai.reference_wallet.crypto_customer_wallet.fragments.common.CreateNewBankAccountFragment;
import com.bitdubai.reference_wallet.crypto_customer_wallet.fragments.common.CreateNewLocationFragment;
import com.bitdubai.reference_wallet.crypto_customer_wallet.fragments.common.SettingsCreateNewBankAccountFragment;
import com.bitdubai.reference_wallet.crypto_customer_wallet.fragments.contract_detail.CloseContractDetailsFragment;
import com.bitdubai.reference_wallet.crypto_customer_wallet.fragments.contract_detail.ClosedNegotiationDetailsFragment;
import com.bitdubai.reference_wallet.crypto_customer_wallet.fragments.contract_detail.ContractDetailActivityFragment;
import com.bitdubai.reference_wallet.crypto_customer_wallet.fragments.contracts_history.ContractsHistoryActivityFragment;
import com.bitdubai.reference_wallet.crypto_customer_wallet.fragments.home.MarketRateStatisticsFragment;
import com.bitdubai.reference_wallet.crypto_customer_wallet.fragments.home.OpenContractsTabFragment;
import com.bitdubai.reference_wallet.crypto_customer_wallet.fragments.home.OpenNegotiationsTabFragment;
import com.bitdubai.reference_wallet.crypto_customer_wallet.fragments.open_negotiation_details.OpenNegotiationAddNoteFragment;
import com.bitdubai.reference_wallet.crypto_customer_wallet.fragments.open_negotiation_details.OpenNegotiationDetailsFragment;
import com.bitdubai.reference_wallet.crypto_customer_wallet.fragments.settings.SettingsActivityFragment;
import com.bitdubai.reference_wallet.crypto_customer_wallet.fragments.settings.SettingsBankAccountsFragment;
import com.bitdubai.reference_wallet.crypto_customer_wallet.fragments.settings.SettingsFeeManagementFragment;
import com.bitdubai.reference_wallet.crypto_customer_wallet.fragments.settings.SettingsMylocationsFragment;
import com.bitdubai.reference_wallet.crypto_customer_wallet.fragments.settings.SettingsProvidersFragment;
import com.bitdubai.reference_wallet.crypto_customer_wallet.fragments.start_negotiation.StartNegotiationActivityFragment;
import com.bitdubai.reference_wallet.crypto_customer_wallet.fragments.wizard_pages.WizardPageSetBankAccountsFragment;
import com.bitdubai.reference_wallet.crypto_customer_wallet.fragments.wizard_pages.WizardPageSetBitcoinWalletAndProvidersFragment;
import com.bitdubai.reference_wallet.crypto_customer_wallet.fragments.wizard_pages.WizardPageSetIdentityFragment;
import com.bitdubai.reference_wallet.crypto_customer_wallet.fragments.wizard_pages.WizardPageSetLocationsFragment;

/**
 * Created by Matias Furszyfer on 2015.19.22..
 */
public class CryptoCustomerWalletFragmentFactory extends FermatFragmentFactory<ReferenceAppFermatSession<CryptoCustomerWalletModuleManager>, WalletResourcesProviderManager, CryptoCustomerWalletFragmentsEnumType> {

    @Override
    public AbstractFermatFragment getFermatFragment(CryptoCustomerWalletFragmentsEnumType fragment) throws FragmentNotFoundException {
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
            case CBP_CRYPTO_CUSTOMER_WALLET_OPEN_NEGOTIATION_ADD_NOTE:
                return OpenNegotiationAddNoteFragment.newInstance();
            case CBP_CRYPTO_CUSTOMER_WALLET_CLOSE_NEGOTIATION_DETAILS:
                return ClosedNegotiationDetailsFragment.newInstance();
            case CBP_CRYPTO_CUSTOMER_WALLET_CLOSE_CONTRACT_DETAILS:
                return CloseContractDetailsFragment.newInstance();
            case CBP_CRYPTO_CUSTOMER_WALLET_SET_IDENTITY:
                return WizardPageSetIdentityFragment.newInstance();
            case CBP_CRYPTO_CUSTOMER_WALLET_SET_BITCOIN_WALLET_AND_PROVIDERS:
                return WizardPageSetBitcoinWalletAndProvidersFragment.newInstance();
            case CBP_CRYPTO_CUSTOMER_WALLET_SET_LOCATIONS:
                return WizardPageSetLocationsFragment.newInstance();
            case CBP_CRYPTO_CUSTOMER_WALLET_SET_BANK_ACCOUNT:
                return WizardPageSetBankAccountsFragment.newInstance();
            case CBP_CRYPTO_CUSTOMER_WALLET_CREATE_NEW_LOCATION_IN_WIZARD:
                return CreateNewLocationFragment.newInstance();
            case CBP_CRYPTO_CUSTOMER_WALLET_CREATE_NEW_BANK_ACCOUNT_IN_WIZARD:
                return CreateNewBankAccountFragment.newInstance();
            case CBP_CRYPTO_CUSTOMER_WALLET_SETTINGS_MY_LOCATIONS:
                return SettingsMylocationsFragment.newInstance();
            case CBP_CRYPTO_CUSTOMER_WALLET_CREATE_NEW_LOCATION_IN_SETTINGS:
                return CreateNewLocationFragment.newInstance();
            case CBP_CRYPTO_CUSTOMER_WALLET_SETTINGS_BANK_ACCOUNTS:
                return SettingsBankAccountsFragment.newInstance();
            case CBP_CRYPTO_CUSTOMER_WALLET_SETTINGS_PROVIDERS:
                return SettingsProvidersFragment.newInstance();
            case CBP_CRYPTO_CUSTOMER_WALLET_SETTINGS_MANAGEMENT_FEE:
                return SettingsFeeManagementFragment.newInstance();
            case CBP_CRYPTO_CUSTOMER_WALLET_CONTRACT_DETAILS:
                return ContractDetailActivityFragment.newInstance();
            case CBP_CRYPTO_CUSTOMER_WALLET_CREATE_NEW_BANK_ACCOUNT_IN_SETTINGS:
                return SettingsCreateNewBankAccountFragment.newInstance();
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
