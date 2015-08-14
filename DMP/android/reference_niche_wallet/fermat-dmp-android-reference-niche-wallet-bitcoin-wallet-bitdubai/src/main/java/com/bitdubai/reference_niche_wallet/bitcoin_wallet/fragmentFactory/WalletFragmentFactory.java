package com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragmentFactory;

import android.app.Fragment;

import com.bitdubai.fermat_android_api.layer.definition.wallet.exceptions.FragmentNotFoundException;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.FragmentFactory;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.WalletSession;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_settings.interfaces.WalletSettingsManager;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments.BalanceFragment;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments.ContactsFragment;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments.CreateContactFragment;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments.MoneyRequestFragment;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments.ReceiveFragment;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments.SendFragment;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments.TransactionsFragment;

/**
 * Created by Matias Furszyfer on 2015.07.22..
 */

public class WalletFragmentFactory implements FragmentFactory{


    /**
     *  Create a new Fragment based on the fragmentType
     *
     * @param code            getCode from a Enum of fragments
     * @param walletSession   reference of walletSession
     * @return
     * @throws FragmentNotFoundException
     */

    @Override
    public Fragment getFragment(String code,WalletSession walletSession,WalletSettingsManager walletSettingsManager) throws FragmentNotFoundException {

        Fragment currentFragment = null;

        Fragments fragment = Fragments.getValueFromString(code);
        switch (fragment){
            /**
             * Executing fragments for BITCOIN REQUESTED.
             */
            case CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_BALANCE:
                currentFragment =  BalanceFragment.newInstance(0, walletSession,walletSettingsManager);
                break;
            case CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_RECEIVE:
                currentFragment = ReceiveFragment.newInstance(0, walletSession);
                break;
            case CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_SEND:
                currentFragment =  SendFragment.newInstance(0, walletSession);
                break;

            case CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_TRANSACTIONS:
                currentFragment =  TransactionsFragment.newInstance(0, walletSession);
                break;
            case CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_CONTACTS:
                currentFragment =  ContactsFragment.newInstance(0, walletSession);
                break;
            case CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_CREATE_CONTACTS:
                currentFragment =  CreateContactFragment.newInstance(0, walletSession);
                break;
            case CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_MONEY_REQUEST:
                currentFragment = MoneyRequestFragment.newInstance(0,walletSession,walletSettingsManager);
                break;
            default:
                throw new FragmentNotFoundException("Fragment not found",new Exception(),code,"Swith failed");
        }
        return currentFragment;
    }


}
