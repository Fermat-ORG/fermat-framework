package com.bitdubai.reference_niche_wallet.loss_protected_wallet.fragment_factory;

import com.bitdubai.fermat_android_api.engine.FermatFragmentFactory;
import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.exceptions.FragmentNotFoundException;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.interfaces.LossProtectedWallet;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_resources.interfaces.WalletResourcesProviderManager;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.fragments.ContactDetailFragment;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.fragments.ContactsFragment;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.fragments.LossProtectedSettingsFragment;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.fragments.wallet_final_version.AddConnectionFragment;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.fragments.wallet_final_version.ChunckValuesDetailFragment;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.fragments.wallet_final_version.ChunckValuesHistoryFragment;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.fragments.wallet_final_version.HomeFragment;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.fragments.wallet_final_version.NoIdentityFragment;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.fragments.wallet_final_version.ReceiveTransactionHistoryFragment;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.fragments.wallet_final_version.RequestFormFragment;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.fragments.wallet_final_version.RequestReceiveHistoryFragment2;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.fragments.wallet_final_version.RequestSendHistoryFragment2;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.fragments.wallet_final_version.SendFormFragment;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.fragments.wallet_final_version.SendFormWalletFragment;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.fragments.wallet_final_version.SendTransactionHistoryFragment;


/**
 * Created by Matias Furszyfer on 2015.07.22..
 */

public class LossProtectedWalletFragmentFactory extends FermatFragmentFactory<ReferenceAppFermatSession<LossProtectedWallet>,WalletResourcesProviderManager,LossProtectedFragmentsEnumType>  {


    @Override
    public AbstractFermatFragment getFermatFragment(LossProtectedFragmentsEnumType fragments) throws FragmentNotFoundException {
        AbstractFermatFragment currentFragment = null;
        try {

            switch (fragments) {
                /**
                 * Executing fragments for BITCOIN REQUESTED.
                 */
                case CCP_BITCOIN_LOSS_PROTECTED_WALLET_HOME:
                    currentFragment = HomeFragment.newInstance();
                    break;
                case CWP_WALLET_RUNTIME_WALLET_BITCOIN_LOSS_PROTECTED_ALL_BITDUBAI_RECEIVE:
                    //currentFragment = ReceiveTransactionFragment2.newInstance();
                    break;
                case CWP_WALLET_RUNTIME_WALLET_BITCOIN_LOSS_PROTECTED_ALL_BITDUBAI_SEND:
                    //currentFragment = SendTransactionFragment2.newInstance(); //RequestHomePaymentFragment.newInstance();
                    break;
                case CWP_WALLET_RUNTIME_WALLET_BITCOIN_LOSS_PROTECTED_ALL_BITDUBAI_TRANSACTIONS:
                    break;
                case CWP_WALLET_RUNTIME_WALLET_BITCOIN_LOSS_PROTECTED_ALL_BITDUBAI_CONTACTS:
                    currentFragment = ContactsFragment.newInstance();
                    break;
                case CWP_WALLET_RUNTIME_WALLET_BITCOIN_LOSS_PROTECTED_ALL_BITDUBAI_CREATE_CONTACTS:
                    currentFragment = null;//CreateContactFragment.newInstance();
                    break;
                case CWP_WALLET_RUNTIME_WALLET_BITCOIN_LOSS_PROTECTED_ALL_BITDUBAI_DETAIL_CONTACTS:
                    currentFragment = ContactDetailFragment.newInstance();
                    break;
                case CWP_WALLET_RUNTIME_WALLET_BITCOIN_LOSS_PROTECTED_ALL_BITDUBAI_MONEY_REQUEST:
                    break;
                case CWP_WALLET_RUNTIME_WALLET_BITCOIN_LOSS_PROTECTED_ALL_BITDUBAI_TRANSACTIONS_BOOK:
                    break;
                case CWP_WALLET_RUNTIME_WALLET_BITCOIN_LOSS_PROTECTED_ALL_BITDUBAI_TRANSACTIONS_AVAILABLE:
                    break;
                case CCP_BITCOIN_LOSS_PROTECTED_WALLET_TRANSACTIONS_SENT:
                    break;
                case CCP_BITCOIN_LOSS_PROTECTED_WALLET_TRANSACTIONS_RECEIVED:
                    break;
                case CCP_BITCOIN_LOSS_PROTECTED_WALLET_REQUEST_RECEIVED:
                   // currentFragment = RequestHistorySendFragment.newInstance();
                    break;
                case CCP_BITCOIN_LOSS_PROTECTED_WALLET_TRANSACTIONS_RECEIVED_HISTORY:
                    currentFragment = ReceiveTransactionHistoryFragment.newInstance();
                    break;
                case CCP_BITCOIN_LOSS_PROTECTED_WALLET_TRANSACTIONS_SENT_HISTORY:
                    currentFragment = SendTransactionHistoryFragment.newInstance();
                    break;
                case CCP_BITCOIN_LOSS_PROTECTED_WALLET_REQUEST_RECEIVED_HISTORY:
                    currentFragment = RequestReceiveHistoryFragment2.newInstance();
                    break;
                case CCP_BITCOIN_LOSS_PROTECTED_WALLET_REQUEST_SENT_HISTORY:
                    currentFragment = RequestSendHistoryFragment2.newInstance();
                    break;
                case CCP_BITCOIN_LOSS_PROTECTED_WALLET_SEND_FORM_FRAGMENT:
                    currentFragment = SendFormFragment.newInstance();
                    break;
                case CCP_BITCOIN_LOSS_PROTECTED_WALLET_REQUEST_FORM_FRAGMENT:
                    currentFragment = RequestFormFragment.newInstance();
                    break;
                case CCP_BITCOIN_LOSS_PROTECTED_WALLET_SETTINGS_FRAGMENT:
                    currentFragment = LossProtectedSettingsFragment.newInstance();
                    break;
                case CCP_BITCOIN_LOSS_PROTECTED_WALLET_SETTINGS_FRAGMENT_NOTIFICATIONS:
                    //currentFragment = SettingsNotificationsFragment.newInstance();
                    break;
                case CCP_BITCOIN_LOSS_PROTECTED_WALLET_SETTINGS_FRAGMENT_MAIN_NETWORK:
                    //currentFragment = SettingsMainNetworkFragment.newInstance();
                    break;
                case CCP_BITCOIN_LOSS_PROTECTED_WALLET_ADD_CONNECTION_FRAGMENT:
                    currentFragment = AddConnectionFragment.newInstance();
                    break;
                case CCP_BITCOIN_LOSS_PROTECTED_WALLET_NO_IDENTITY_FRAGMENT:
                    currentFragment = NoIdentityFragment.newInstance();
                    break;
                case CCP_BITCOIN_LOSS_PROTECTED_WALLET_SEND_WALLET_FORM_FRAGMENT:
                    currentFragment = SendFormWalletFragment.newInstance();
                    break;
                case CWP_WALLET_RUNTIME_WALLET_BITCOIN_LOSS_PROTECTED_ALL_BITDUBAI_CHUNCK_VALUES:
                    currentFragment = ChunckValuesHistoryFragment.newInstance();
                    break;

                case CCP_BITCOIN_LOSS_PROTECTED_WALLET_CHUNCK_VALUES_DETAIL_FRAGMENT:
                    currentFragment = ChunckValuesDetailFragment.newInstance();
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
    public LossProtectedFragmentsEnumType getFermatFragmentEnumType(String key) {
        return LossProtectedFragmentsEnumType.getValue(key);
    }
}
