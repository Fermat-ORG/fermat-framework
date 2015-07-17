package com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments;


import android.app.AlertDialog;
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
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.Wallets;
import com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.crypto_wallet.exceptions.CantGetBalanceException;
import com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.crypto_wallet.exceptions.CantGetCryptoWalletException;
import com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.crypto_wallet.interfaces.CryptoWallet;
import com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.crypto_wallet.interfaces.CryptoWalletManager;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.UnexpectedWalletExceptionSeverity;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.Platform;

import java.text.DecimalFormat;
import java.util.UUID;

/**
 * Created by Natalia on 02/06/2015.
 */
public class BalanceFragment extends Fragment {
    
    final int TYPE_BALANCE_AVAILABLE=1;
    final int TYPE_BALANCE_BOOK=2;

    View rootView;
    SwipeRefreshLayout swipeLayout;

    long balanceAvailable;

    long bookBalance;

    boolean showBalanceBTC = false;

    int showTypeBalance=TYPE_BALANCE_AVAILABLE;

    private static final String ARG_POSITION = "position";

    UUID wallet_id = UUID.fromString("25428311-deb3-4064-93b2-69093e859871");

    Typeface tf ;

    /**
     * DealsWithNicheWalletTypeCryptoWallet Interface member variables.
     */
    private static CryptoWalletManager cryptoWalletManager;
    private static Platform platform = new Platform();
    CryptoWallet cryptoWallet;
    private ErrorManager errorManager;

    private TextView txtViewTypeBalance;
    private TextView txtViewBalance;

    public static BalanceFragment newInstance(int position) {
        BalanceFragment f = new BalanceFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        tf=Typeface.createFromAsset(getActivity().getAssets(), "fonts/CaviarDreams.ttf");

        errorManager = platform.getErrorManager();
        try {
            balanceAvailable = 0;
            bookBalance=0;
            cryptoWalletManager = platform.getCryptoWalletManager();

            try {
                cryptoWallet = cryptoWalletManager.getCryptoWallet();
            } catch (CantGetCryptoWalletException e)
            {
                errorManager.reportUnexpectedWalletException(Wallets.CWP_WALLET_RUNTIME_WALLET_BITCOIN_WALLET_ALL_BITDUBAI, UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                showMessage("CantGetCryptoWalletException- " + e.getMessage());

            }
            try {
                balanceAvailable = cryptoWallet.getAvailableBalance(wallet_id);

                bookBalance= cryptoWallet.getBookBalance(wallet_id);
            } catch (CantGetBalanceException e)
            {
                errorManager.reportUnexpectedWalletException(Wallets.CWP_WALLET_RUNTIME_WALLET_BITCOIN_WALLET_ALL_BITDUBAI, UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                showMessage("CantGetBalanceException- " + e.getMessage());

            }
        } catch (Exception ex) {
            errorManager.reportUnexpectedWalletException(Wallets.CWP_WALLET_RUNTIME_WALLET_BITCOIN_WALLET_ALL_BITDUBAI, UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, ex);
            showMessage("Unexpected error getting the balance - " + ex.getMessage());
        }
    }

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
        showBalanceBTC = !showBalanceBTC;
        if(showTypeBalance==TYPE_BALANCE_AVAILABLE){
            txtViewBalance.setText(formatBalanceString(balanceAvailable));
        }else if (showTypeBalance==TYPE_BALANCE_BOOK){
            txtViewBalance.setText(formatBalanceString(bookBalance));
        }
    }


    /**
     *  Formationg balance amount
     * @param balance
     * @return
     */
    private String formatBalanceString(long balance) {
        String stringBalance = "";
        if (showBalanceBTC) {
            DecimalFormat df = new DecimalFormat();
            df.setMaximumFractionDigits(6);
            df.setMinimumFractionDigits(6);
            String BTCFormat = df.format(balance / 100000000.0);
            stringBalance = BTCFormat + " BTC";
        } else {
            stringBalance = (int) (balance / 100) + " bits";
        }
        return stringBalance;
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
                showMessage("CantGetBalanceException- " + e.getMessage());

            }

            if(showTypeBalance==TYPE_BALANCE_AVAILABLE){
                txtViewBalance.setText(formatBalanceString(balanceAvailable));
            }else if(showTypeBalance==TYPE_BALANCE_BOOK){
                txtViewBalance.setText(formatBalanceString(bookBalance));
            }


        } catch (Exception ex) {

            errorManager.reportUnexpectedWalletException(Wallets.CWP_WALLET_RUNTIME_WALLET_BITCOIN_WALLET_ALL_BITDUBAI, UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, ex);
            showMessage("Unexpected error getting the balance - " + ex.getMessage());

        }
    }

    private void showMessage(String text) {
        AlertDialog alertDialog = new AlertDialog.Builder(this.getActivity()).create();
        alertDialog.setTitle("Warning");
        alertDialog.setMessage(text);
        alertDialog.setButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // aquí puedes añadir funciones
            }
        });
        // alertDialog.setIcon(R.drawable.icon);
        alertDialog.show();
    }
}

