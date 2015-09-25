package com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragmentFactory;

import com.bitdubai.fermat_android_api.engine.FermatWalletFragmentFactory;
import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatWalletFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.exceptions.FragmentNotFoundException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_settings.interfaces.WalletSettings;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments.wallet_final_version.RequestPaymentFragment;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments.wallet_v2.HomeFragment;
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
                   currentFragment = RequestPaymentFragment.newInstance();
                    break;
                case CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_RECEIVE:
                    currentFragment = com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments.wallet_v2.ReceiveFragment.newInstance(0);
                    break;
                case CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_SEND:
                    currentFragment = com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments.wallet_v2.SendFragment.newInstance(0);
                    break;

                case CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_TRANSACTIONS:
                    //currentFragment = TransactionsFragment.newInstance(0, refereceWalletSession);
                    break;
                case CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_CONTACTS:
                    //currentFragment = ContactsFragment.newInstance(refereceWalletSession, walletResourcesProviderManager);
                    currentFragment = com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments.wallet_v2.ContactsFragment.newInstance();
                    break;
                case CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_CREATE_CONTACTS:
                    //currentFragment = CreateContactFragment.newInstance(0, refereceWalletSession);
                    break;
                case CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_DETAIL_CONTACTS:
                    //currentFragment = ContactDetailFragment.newInstance(refereceWalletSession);
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
