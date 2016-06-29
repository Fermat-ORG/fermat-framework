package com.bitdubai.reference_niche_wallet.fermat_wallet.fragment_factory;

import com.bitdubai.fermat_android_api.engine.FermatFragmentFactory;
import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.exceptions.FragmentNotFoundException;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_resources.interfaces.WalletResourcesProviderManager;
import com.bitdubai.reference_niche_wallet.fermat_wallet.common.utils.mnemonic.MnemonicFragment;
import com.bitdubai.reference_niche_wallet.fermat_wallet.fragments.ContactDetailFragment;
import com.bitdubai.reference_niche_wallet.fermat_wallet.fragments.ContactsFragment;
import com.bitdubai.reference_niche_wallet.fermat_wallet.fragments.FermatWalletSettings;
import com.bitdubai.reference_niche_wallet.fermat_wallet.fragments.wallet_final_version.AddConnectionFragment;
import com.bitdubai.reference_niche_wallet.fermat_wallet.fragments.wallet_final_version.NoIdentityFragment;
import com.bitdubai.reference_niche_wallet.fermat_wallet.fragments.wallet_final_version.ReceiveTransactionFragment2;
import com.bitdubai.reference_niche_wallet.fermat_wallet.fragments.wallet_final_version.RequestFormFragment;
import com.bitdubai.reference_niche_wallet.fermat_wallet.fragments.wallet_final_version.RequestReceiveHistoryFragment;
import com.bitdubai.reference_niche_wallet.fermat_wallet.fragments.wallet_final_version.RequestSendHistoryFragment;
import com.bitdubai.reference_niche_wallet.fermat_wallet.fragments.wallet_final_version.SendFormFragment;
import com.bitdubai.reference_niche_wallet.fermat_wallet.fragments.wallet_final_version.SendTransactionFragment2;
import com.bitdubai.reference_niche_wallet.fermat_wallet.fragments.wallet_final_version.SettingsNotificationsFragment;
import com.bitdubai.reference_niche_wallet.fermat_wallet.fragments.wallet_final_version.WalletErrorReportFragment;
import com.bitdubai.reference_niche_wallet.fermat_wallet.session.FermatWalletSessionReferenceApp;




/**
 * Created by Matias Furszyfer on 2015.07.22..
 */

public class ReferenceWalletFragmentFactory extends FermatFragmentFactory<FermatWalletSessionReferenceApp,WalletResourcesProviderManager,ReferenceFragmentsEnumType>  {



    @Override
    public AbstractFermatFragment getFermatFragment(ReferenceFragmentsEnumType fragments) throws FragmentNotFoundException {
        AbstractFermatFragment currentFragment = null;
        try {

            switch (fragments) {
                /**
                 * Executing fragments for BITCOIN REQUESTED.
                 */
                case CWP_WALLET_RUNTIME_WALLET_BITCOIN_FERMAT_ALL_BITDUBAI_RECEIVE:
                    currentFragment = ReceiveTransactionFragment2.newInstance();
                    break;
                case CWP_WALLET_RUNTIME_WALLET_BITCOIN_FERMAT_ALL_BITDUBAI_SEND:
                    currentFragment =SendTransactionFragment2.newInstance(); //RequestHomePaymentFragment.newInstance();
                    break;

                case CWP_WALLET_RUNTIME_WALLET_BITCOIN_FERMAT_ALL_BITDUBAI_TRANSACTIONS:
                    break;
                case CWP_WALLET_RUNTIME_WALLET_BITCOIN_FERMAT_ALL_BITDUBAI_CONTACTS:
                    currentFragment = ContactsFragment.newInstance();
                    break;
                case CWP_WALLET_RUNTIME_WALLET_BITCOIN_FERMAT_ALL_BITDUBAI_CREATE_CONTACTS:
                    currentFragment = null;//CreateContactFragment.newInstance();
                    break;
                case CWP_WALLET_RUNTIME_WALLET_BITCOIN_FERMAT_ALL_BITDUBAI_DETAIL_CONTACTS:
                    currentFragment = ContactDetailFragment.newInstance();
                    break;
                case CWP_WALLET_RUNTIME_WALLET_BITCOIN_FERMAT_ALL_BITDUBAI_MONEY_REQUEST:
                    break;
                case CWP_WALLET_RUNTIME_WALLET_BITCOIN_FERMAT_ALL_BITDUBAI_TRANSACTIONS_BOOK:
                    break;
                case CWP_WALLET_RUNTIME_WALLET_BITCOIN_FERMAT_ALL_BITDUBAI_TRANSACTIONS_AVAILABLE:
                    break;
                case CCP_BITCOIN_FERMAT_WALLET_TRANSACTIONS_SENT:
                    break;
                case CCP_BITCOIN_FERMAT_WALLET_TRANSACTIONS_RECEIVED:
                    break;
                case CCP_BITCOIN_FERMAT_WALLET_REQUEST_RECEIVED:
                    currentFragment = RequestSendHistoryFragment.newInstance();
                    break;
                case CCP_BITCOIN_FERMAT_WALLET_TRANSACTIONS_RECEIVED_HISTORY:
                    break;
                case CCP_BITCOIN_FERMAT_WALLET_TRANSACTIONS_SENT_HISTORY:
                    break;
                case CCP_BITCOIN_FERMAT_WALLET_REQUEST_RECEIVED_HISTORY:
                    currentFragment = RequestReceiveHistoryFragment.newInstance();
                    break;
                case CCP_BITCOIN_FERMAT_WALLET_REQUEST_SENT_HISTORY:
                    currentFragment = RequestSendHistoryFragment.newInstance();
                    break;
                case CCP_BITCOIN_FERMAT_WALLET_SEND_FORM_FRAGMENT:
                    currentFragment = SendFormFragment.newInstance();
                    break;
                case CCP_BITCOIN_FERMAT_WALLET_REQUEST_FORM_FRAGMENT:
                    currentFragment = RequestFormFragment.newInstance();
                    break;
                case CCP_BITCOIN_FERMAT_WALLET_SETTINGS_FRAGMENT:
                    currentFragment = FermatWalletSettings.newInstance();
                    break;
                case CCP_BITCOIN_FERMAT_WALLET_SETTINGS_FRAGMENT_NOTIFICATIONS:
                    currentFragment = SettingsNotificationsFragment.newInstance();
                    break;
                case CCP_BITCOIN_FERMAT_WALLET_SETTINGS_FRAGMENT_MAIN_NETWORK:
                    //currentFragment = SettingsMainNetworkFragment.newInstance();
                    break;
                case CCP_BITCOIN_FERMAT_WALLET_ADD_CONNECTION_FRAGMENT:
                    currentFragment = AddConnectionFragment.newInstance();
                    break;
                case CCP_BITCOIN_FERMAT_WALLET_NO_IDENTITY_FRAGMENT:
                    currentFragment = NoIdentityFragment.newInstance();
                    break;

                case CCP_BITCOIN_FERMAT_WALLET_MNEMONIC_FRAGMENT:
                    currentFragment = MnemonicFragment.newInstance();
                    break;
                case CCP_BITCOIN_FERMAT_WALLET_SEND_ERROR_REPORT_FRAGMENT:
                    currentFragment = WalletErrorReportFragment.newInstance();
                    break;
                default:
                    throw new FragmentNotFoundException("Fragment not found", new Exception(), fragments.getKey(), "Swith failed");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return currentFragment;
    }

    @Override
    public ReferenceFragmentsEnumType getFermatFragmentEnumType(String key) {
        return ReferenceFragmentsEnumType.getValue(key);
    }
}
