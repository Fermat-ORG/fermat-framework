package com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragment_factory;

import com.bitdubai.fermat_android_api.engine.FermatWalletFragmentFactory;
import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatWalletFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.exceptions.FragmentNotFoundException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.interfaces.WalletSettings;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments.ContactDetailFragment;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments.ContactsFragment;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments.CreateContactFragment;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments.wallet_final_version.AddConnectionFragment;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments.wallet_final_version.ReceiveTransactionFragment2;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments.wallet_final_version.RequestFormFragment;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments.wallet_final_version.RequestHomePaymentFragment;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments.wallet_final_version.RequestReceiveHistoryFragment;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments.wallet_final_version.RequestSendHistoryFragment;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments.wallet_final_version.SendFormFragment;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments.wallet_final_version.SendTransactionFragment2;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments.wallet_final_version.SettingsFragment2;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments.wallet_final_version.TransactionsReceivedHistory;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments.wallet_final_version.TransactionsSendHistory;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments.wallet_v2.RequestsReceivedFragment;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.session.ReferenceWalletSession;

/**
 * Created by Matias Furszyfer on 2015.07.22..
 */

public class ReferenceWalletFragmentFactory extends FermatWalletFragmentFactory<ReferenceWalletSession, WalletSettings, ReferenceFragmentsEnumType> {//implements com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.WalletFragmentFactory {


    @Override
    public FermatWalletFragment getFermatFragment(ReferenceFragmentsEnumType fragments) throws FragmentNotFoundException {
        FermatWalletFragment currentFragment = null;
        try {

            switch (fragments) {
                /**
                 * Executing fragments for BITCOIN REQUESTED.
                 */
                case CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_BALANCE:
                    currentFragment =  RequestHomePaymentFragment.newInstance();
                    break;
                case CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_RECEIVE:
                    currentFragment = ReceiveTransactionFragment2.newInstance();
                    break;
                case CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_SEND:
                    currentFragment =SendTransactionFragment2.newInstance(); //RequestHomePaymentFragment.newInstance();
                    break;

                case CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_TRANSACTIONS:
                    break;
                case CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_CONTACTS:
                    currentFragment = ContactsFragment.newInstance();
                    break;
                case CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_CREATE_CONTACTS:
                    currentFragment = CreateContactFragment.newInstance();
                    break;
                case CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_DETAIL_CONTACTS:
                    currentFragment = ContactDetailFragment.newInstance();
                    break;
                case CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_MONEY_REQUEST:
                    break;
                case CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_TRANSACTIONS_BOOK:
                    break;
                case CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_TRANSACTIONS_AVAILABLE:
                    break;
                case CCP_BITCOIN_WALLET_TRANSACTIONS_SENT:
                    currentFragment = TransactionsSendHistory.newInstance();
                    break;
                case CCP_BITCOIN_WALLET_TRANSACTIONS_RECEIVED:
                    currentFragment = TransactionsReceivedHistory.newInstance();
                    break;
                case CCP_BITCOIN_WALLET_REQUEST_RECEIVED:
                    currentFragment = RequestSendHistoryFragment.newInstance();
                    break;
                case CCP_BITCOIN_WALLET_REQUEST_SEND:
                    currentFragment = RequestsReceivedFragment.newInstance();
                    break;
                case CCP_BITCOIN_WALLET_TRANSACTIONS_RECEIVED_HISTORY:
                    currentFragment = TransactionsReceivedHistory.newInstance();
                    break;
                case CCP_BITCOIN_WALLET_TRANSACTIONS_SENT_HISTORY:
                    currentFragment = TransactionsSendHistory.newInstance();
                    break;
                case CCP_BITCOIN_WALLET_REQUEST_RECEIVED_HISTORY:
                    currentFragment = RequestReceiveHistoryFragment.newInstance();
                    break;
                case CCP_BITCOIN_WALLET_REQUEST_SENT_HISTORY:
                    currentFragment = RequestSendHistoryFragment.newInstance();
                    break;
                case CCP_BITCOIN_WALLET_SEND_FORM_FRAGMENT:
                    currentFragment = SendFormFragment.newInstance();
                    break;
                case CCP_BITCOIN_WALLET_REQUEST_FORM_FRAGMENT:
                    currentFragment = RequestFormFragment.newInstance();
                    break;
                case CCP_BITCOIN_WALLET_SETTINGS_FRAGMENT:
                    currentFragment = SettingsFragment2.newInstance();
                    break;
                case CCP_BITCOIN_WALLET_ADD_CONNECTION_FRAGMENT:
                    currentFragment = AddConnectionFragment.newInstance();
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
