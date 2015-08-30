package com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragmentFactory;

import android.app.Fragment;

import com.bitdubai.fermat_android_api.layer.definition.wallet.exceptions.FragmentNotFoundException;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.WalletSession;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_settings.interfaces.WalletSettings;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_settings.interfaces.WalletSettingsManager;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_resources.WalletResourcesProviderManager;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments.BalanceFragment;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments.ContactDetailFragment;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments.ContactsFragment;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments.CreateContactFragment;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments.MoneyRequestFragment;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments.ReceiveFragment;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments.SendFragment;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments.TransactionsBookFragment;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments.TransactionsFragment;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.session.ReferenceWalletSession;

/**
 * Created by Matias Furszyfer on 2015.07.22..
 */

public class ReferenceWalletFragmentFactory implements com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.WalletFragmentFactory {


    /**
     *  Create a new Fragment based on the fragmentType
     *
     * @param code            getCode from a Enum of fragments
     * @param walletSession   reference of walletSession
     * @return
     * @throws FragmentNotFoundException
     */

    @Override
    public Fragment getFragment(String code,WalletSession walletSession,WalletResourcesProviderManager walletResourcesProviderManager) throws FragmentNotFoundException {

        Fragment currentFragment = null;
        try {
            ReferenceWalletSession refereceWalletSession = (ReferenceWalletSession) walletSession;

            ReferenceFragmentsEnumType fragment = ReferenceFragmentsEnumType.getValue(code);


            switch (fragment) {
                /**
                 * Executing fragments for BITCOIN REQUESTED.
                 */
                case CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_BALANCE:
                    currentFragment = BalanceFragment.newInstance(0, refereceWalletSession, walletResourcesProviderManager);
                    break;
                case CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_RECEIVE:
                    currentFragment = ReceiveFragment.newInstance(0, refereceWalletSession, walletResourcesProviderManager);
                    break;
                case CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_SEND:
                    currentFragment = SendFragment.newInstance(0, refereceWalletSession, walletResourcesProviderManager);
                    break;

                case CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_TRANSACTIONS:
                    currentFragment = TransactionsFragment.newInstance(0, refereceWalletSession, walletResourcesProviderManager);
                    break;
                case CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_CONTACTS:
                    currentFragment = ContactsFragment.newInstance(refereceWalletSession, walletResourcesProviderManager);
                    break;
                case CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_CREATE_CONTACTS:
                    currentFragment = CreateContactFragment.newInstance(0, refereceWalletSession, walletResourcesProviderManager);
                    break;
                case CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_DETAIL_CONTACTS:
                    currentFragment = ContactDetailFragment.newInstance(refereceWalletSession, walletResourcesProviderManager);
                    break;
                case CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_MONEY_REQUEST:
                    currentFragment = MoneyRequestFragment.newInstance(0, null, refereceWalletSession, walletResourcesProviderManager);
                    break;
                case CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_TRANSACTIONS_BOOK:
                    currentFragment = TransactionsBookFragment.newInstance(0, refereceWalletSession, walletResourcesProviderManager, 0);
                    break;
                case CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_TRANSACTIONS_AVAILABLE:
                    currentFragment = TransactionsBookFragment.newInstance(0, refereceWalletSession, walletResourcesProviderManager, 1);
                    break;
                default:
                    throw new FragmentNotFoundException("Fragment not found", new Exception(), code, "Swith failed");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return currentFragment;
    }


}
