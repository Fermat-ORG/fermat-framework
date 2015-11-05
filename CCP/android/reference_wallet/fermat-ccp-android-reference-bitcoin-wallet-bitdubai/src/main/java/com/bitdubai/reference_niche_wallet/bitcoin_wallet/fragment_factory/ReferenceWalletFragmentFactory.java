package com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragment_factory;

import com.bitdubai.fermat_android_api.engine.FermatWalletFragmentFactory;
import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatWalletFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.exceptions.FragmentNotFoundException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.interfaces.WalletSettings;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments.ContactDetailFragment;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments.ContactsFragment;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments.CreateContactFragment;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments.wallet_final_version.ReceiveTransactionFragment2;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments.wallet_final_version.ReceiveTransactionsFragment;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments.wallet_final_version.RequestHomePaymentFragment;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments.wallet_final_version.RequestSendHistoryFragment;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments.wallet_final_version.SendTransactionFragment2;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments.wallet_final_version.SendTransactionsFragment;
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
                    // currentFragment = /*BlankFragment.newInstance(null,null);*/BalanceFragment.newInstance(0, refereceWalletSession, walletResourcesProviderManager);
                   // currentFragment = HomeFragment.newInstance();
                    //currentFragment = com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments.wallet_v2.ReceiveFragment.newInstance(0);
                   //currentFragment = RequestPaymentFragment.newInstance();
                    //currentFragment = BalanceFragment.newInstance();
                    currentFragment =  RequestHomePaymentFragment.newInstance();
                    break;
                case CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_RECEIVE:
                    //currentFragment = com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments.wallet_v2.ReceiveFragment.newInstance(0);
                    //currentFragment = RequestPaymentFragment.newInstance();
                    currentFragment = ReceiveTransactionFragment2.newInstance();
                    break;
                case CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_SEND:
                    //currentFragment = com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments.wallet_v2.SendFragment.newInstance(0);
                    //currentFragment = SendFragment.newInstance();
                    currentFragment = SendTransactionFragment2.newInstance();
                    break;

                case CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_TRANSACTIONS:
                    //currentFragment = TransactionsFragment.newInstance(0, refereceWalletSession);
                    break;
                case CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_CONTACTS:
                    currentFragment = ContactsFragment.newInstance();
                    //currentFragment = com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments.wallet_v2.ContactsFragment.newInstance();
                    break;
                case CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_CREATE_CONTACTS:
                    currentFragment = CreateContactFragment.newInstance();
                    break;
                case CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_DETAIL_CONTACTS:
                    currentFragment = ContactDetailFragment.newInstance();
                    break;
                case CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_MONEY_REQUEST:
                    //currentFragment = MoneyRequestFragment.newInstance(0, null, refereceWalletSession);
                    break;
                case CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_TRANSACTIONS_BOOK:
                   // currentFragment = TransactionsBookFragment.newInstance(0, refereceWalletSession);
                    break;
                case CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_TRANSACTIONS_AVAILABLE:
                    //currentFragment = TransactionsBookFragment.newInstance(0, refereceWalletSession);
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
                    currentFragment = RequestSendHistoryFragment.newInstance();
                    break;
                case CCP_BITCOIN_WALLET_REQUEST_SENT_HISTORY:
                    currentFragment = RequestSendHistoryFragment.newInstance();
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
