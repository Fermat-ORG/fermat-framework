package com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.bitdubai.android_fermat_dmp_wallet_bitcoin.R;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.WalletSession;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.crypto_wallet.exceptions.CantGetBalanceException;
import com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.crypto_wallet.exceptions.CantGetCryptoWalletException;
import com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.crypto_wallet.interfaces.CryptoWallet;
import com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.crypto_wallet.interfaces.CryptoWalletManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedWalletExceptionSeverity;


import java.text.DecimalFormat;
import java.util.UUID;

import static com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.utils.WalletUtils.formatBalanceString;
import static com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.utils.WalletUtils.showMessage;

/**
 * Created by Natalia on 02/06/2015.
 */
public class BalanceFragment extends Fragment {
    
    final int TYPE_BALANCE_AVAILABLE=1;
    final int TYPE_BALANCE_BOOK=2;

    UUID wallet_id = UUID.fromString("25428311-deb3-4064-93b2-69093e859871");

    /**
     *  Screen members
     */

    private View rootView;
    private SwipeRefreshLayout swipeLayout;
    private TextView txtViewTypeBalance;
    private TextView txtViewBalance;

    /**
     *  Platform members
     */

    long balanceAvailable;
    long bookBalance;

    /**
     * DealsWithNicheWalletTypeCryptoWallet Interface member variables.
     */

    private CryptoWalletManager cryptoWalletManager;
    CryptoWallet cryptoWallet;

    /**
     *  Flag to show one balance or other
     */

    int showTypeBalance=TYPE_BALANCE_AVAILABLE;

    /**
     * TypeFace to apply in all fragment
     */
    Typeface tf;

    /**
     * Wallet Session
     */

    WalletSession walletSession;
    

    /**
     * Error manager Addon
     * Used to capture exceptions
     */

    private ErrorManager errorManager;


    /**
     *  Create a new instance of BalanceFragment and set walletSession and platforms plugin inside
     * @param position
     * @param walletSession   An object that contains all session data
     * @return BalanceFragment with Session and platform plugins inside
     */

    public static BalanceFragment newInstance(int position,WalletSession walletSession) {
        BalanceFragment balanceFragment = new BalanceFragment();
        balanceFragment.setWalletSession(walletSession);
        return balanceFragment;
    }

    /**
     *
     * @param savedInstanceState
     */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);


        tf=Typeface.createFromAsset(getActivity().getAssets(), "fonts/CaviarDreams.ttf");

        /**
         *
         */
        try {
            errorManager = walletSession.getErrorManager();
            balanceAvailable = 0;
            bookBalance=0;
            cryptoWalletManager = walletSession.getCryptoWalletManager();

            try {

                /**
                 * Get cryptoWalet that manage balance in wallet
                 */
                cryptoWallet = cryptoWalletManager.getCryptoWallet();

            }catch (CantGetCryptoWalletException e) {
                errorManager.reportUnexpectedWalletException(Wallets.CWP_WALLET_RUNTIME_WALLET_BITCOIN_WALLET_ALL_BITDUBAI, UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                showMessage(getActivity(),"CantGetCryptoWalletException- " + e.getMessage());

            }
            try {
                /**
                 * Get AvailableBalance
                 */
                balanceAvailable = cryptoWallet.getAvailableBalance(wallet_id);

                /**
                 * Get BookBalance
                 */
                bookBalance= cryptoWallet.getBookBalance(wallet_id);

            } catch (CantGetBalanceException e)
            {
                errorManager.reportUnexpectedWalletException(Wallets.CWP_WALLET_RUNTIME_WALLET_BITCOIN_WALLET_ALL_BITDUBAI, UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                showMessage(getActivity(),"CantGetBalanceException- " + e.getMessage());

            }
        } catch (Exception ex) {
            errorManager.reportUnexpectedWalletException(Wallets.CWP_WALLET_RUNTIME_WALLET_BITCOIN_WALLET_ALL_BITDUBAI, UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, ex);
            showMessage(getActivity(),"Unexpected error getting the balance - " + ex.getMessage());
        }
    }


    /**
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.wallets_bitcoin_fragment_balance, container, false);


        // Loading a setting textView Balance type
        txtViewTypeBalance =(TextView) rootView.findViewById(R.id.txtViewTypeBalance);
        txtViewTypeBalance.setTypeface(tf);
        txtViewTypeBalance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeBalanceType();
            }
        });

        // Setting type balance
        showTypeBalance=TYPE_BALANCE_AVAILABLE;


        // Loading a setting textView Balance amount
        txtViewBalance = ((TextView) rootView.findViewById(R.id.txtViewBalance));
        txtViewBalance.setTypeface(tf);
        txtViewBalance.setText(formatBalanceString(balanceAvailable));
        txtViewBalance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                changeBalance();
                //refreshBalance();
            }
        });


        // Loading a setting SwipeRefreshLayout
        swipeLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshBalanceContent();
            }
        });


        return rootView;
    }

    /*
        Method to change the balance type
     */
    private void changeBalanceType() {
        if(showTypeBalance==TYPE_BALANCE_AVAILABLE){
            txtViewBalance.setText(formatBalanceString(bookBalance));
            txtViewTypeBalance.setText(R.string.book_balance);
            showTypeBalance=TYPE_BALANCE_BOOK;
        }else if (showTypeBalance==TYPE_BALANCE_BOOK){
            txtViewBalance.setText(formatBalanceString(balanceAvailable));
            txtViewTypeBalance.setText(R.string.available_balance);
            showTypeBalance=TYPE_BALANCE_AVAILABLE;
        }
    }

    /*
        Method to change the balance amount
     */
    private void changeBalance(){
        if(showTypeBalance==TYPE_BALANCE_AVAILABLE){
            txtViewBalance.setText(formatBalanceString(balanceAvailable));
        }else if (showTypeBalance==TYPE_BALANCE_BOOK){
            txtViewBalance.setText(formatBalanceString(bookBalance));
        }
    }




    /**
     *  Method to run the swipeRefreshLayout
     */
    private void refreshBalanceContent(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshBalance();
                //transactionArrayAdapter = new TransactionArrayAdapter(getActivity(),lstTransactions);
                //listViewTransactions.setAdapter(transactionArrayAdapter);
                swipeLayout.setRefreshing(false);
            }
        }, 3000);

    }

    /**
     *  Method to refresh amount of BookBalance and AvailableBalance
     */
    private void refreshBalance() {
        try {
            try {
                balanceAvailable = cryptoWallet.getAvailableBalance(wallet_id);

                bookBalance= cryptoWallet.getBookBalance(wallet_id);

            } catch (CantGetBalanceException e)
            {
                errorManager.reportUnexpectedWalletException(Wallets.CWP_WALLET_RUNTIME_WALLET_BITCOIN_WALLET_ALL_BITDUBAI, UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                showMessage(getActivity(),"CantGetBalanceException- " + e.getMessage());

            }

            if(showTypeBalance==TYPE_BALANCE_AVAILABLE){
                txtViewBalance.setText(formatBalanceString(balanceAvailable));
            }else if(showTypeBalance==TYPE_BALANCE_BOOK){
                txtViewBalance.setText(formatBalanceString(bookBalance));
            }


        } catch (Exception ex) {

            errorManager.reportUnexpectedWalletException(Wallets.CWP_WALLET_RUNTIME_WALLET_BITCOIN_WALLET_ALL_BITDUBAI, UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, ex);
            showMessage(getActivity(),"Unexpected error getting the balance - " + ex.getMessage());

        }
    }


    /**
     *  Set Wallet Session object
     * @param walletSession
     */
    public void setWalletSession(WalletSession walletSession) {
        this.walletSession = walletSession;
    }
}

