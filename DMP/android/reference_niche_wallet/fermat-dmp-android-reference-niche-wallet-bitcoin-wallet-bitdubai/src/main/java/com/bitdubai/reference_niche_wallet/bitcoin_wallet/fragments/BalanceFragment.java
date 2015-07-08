package com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.SystemClock;

import android.widget.Button;
import android.widget.TextView;

import com.bitdubai.android_fermat_dmp_wallet_bitcoin.R;
import com.bitdubai.fermat_api.layer.all_definition.enums.PlatformComponents;
import com.bitdubai.fermat_api.layer.dmp_middleware.app_runtime.enums.Wallets;
import com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.crypto_wallet.exceptions.CantGetAllWalletContactsException;
import com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.crypto_wallet.exceptions.CantGetBalanceException;
import com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.crypto_wallet.exceptions.CantGetCryptoWalletException;
import com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.crypto_wallet.interfaces.CryptoWallet;
import com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.crypto_wallet.interfaces.CryptoWalletManager;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.UnexpectedPlatformExceptionSeverity;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.UnexpectedWalletExceptionSeverity;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.Platform;
import com.melnykov.fab.FloatingActionButton;

import java.text.DecimalFormat;
import java.util.UUID;

/**
 * Created by Natalia on 02/06/2015.
 */
public class BalanceFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    
    final int TYPE_BALANCE_AVAILABLE=1;
    final int TYPE_BALANCE_BOOK=2;
    
    View rootView;
    SwipeRefreshLayout swipeLayout;

    long balanceAvailable;

    long bookBalance;

    boolean showBalanceBTC = false;
    
    int showTypeBalance=TYPE_BALANCE_AVAILABLE;

    private static final String ARG_POSITION = "position";

    // Check if a click was executed.
    private long mLastClickTime = 0;



    UUID wallet_id = UUID.fromString("25428311-deb3-4064-93b2-69093e859871");

    Typeface tf ;

    /**
     * DealsWithNicheWalletTypeCryptoWallet Interface member variables.
     */
    private static CryptoWalletManager cryptoWalletManager;
    private static Platform platform = new Platform();
    CryptoWallet cryptoWallet;
    private ErrorManager errorManager;
    
    private TextView labelBalance;
    private TextView textViewBalance;

    public static BalanceFragment newInstance(int position) {
        BalanceFragment f = new BalanceFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

        labelBalance =(TextView) rootView.findViewById(R.id.labelbalance);
        labelBalance.setTypeface(tf);
        labelBalance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refreshBalance();
                if (showTypeBalance==TYPE_BALANCE_AVAILABLE) {
                    labelBalance.setText("available balance");
                    textViewBalance.setText(String.valueOf(balanceAvailable));
                    showTypeBalance=TYPE_BALANCE_BOOK;
                    //fab_change_balance.setImageResource(R.drawable.wallet);
                    //labelBalance.setText();
                }else if (showTypeBalance==TYPE_BALANCE_BOOK){
                    labelBalance.setText("book Balance");
                    textViewBalance.setText(String.valueOf(bookBalance));
                    showTypeBalance=TYPE_BALANCE_AVAILABLE;
                    //fab_change_balance.setImageResource(R.drawable.ic_action_about);
                    //labelBalance.setText();
                }
            }
        });
        showTypeBalance=TYPE_BALANCE_AVAILABLE;
        
        if (showTypeBalance==TYPE_BALANCE_AVAILABLE) {
            //labelBalance.setText();
        }

        textViewBalance = ((TextView) rootView.findViewById(R.id.balance));
        textViewBalance.setTypeface(tf);
        textViewBalance.setText(formatBalanceString(balanceAvailable));


        textViewBalance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // mis-clicking prevention, using threshold of 6000 ms
                if (SystemClock.elapsedRealtime() - mLastClickTime < 6000){
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();

                showBalanceBTC = !showBalanceBTC;
                refreshBalance();
            }
        });

        swipeLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_container);
        swipeLayout.setOnRefreshListener(this);

        //final Button b = (Button) rootView.findViewById(R.id.changeFormatBtn);
        //b.setTypeface(tf);

        /*b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (showBalanceBTC)
                    b.setText("Show in BTC");
                else
                    b.setText("Show in bits");

                showBalanceBTC = !showBalanceBTC;
                refreshBalance();
            }
        });
        */


        return rootView;
    }

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

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeLayout.setRefreshing(false);
                refreshBalance();
            }
        }, 5000);
    }

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
                textViewBalance.setText(formatBalanceString(balanceAvailable));
            }else if(showTypeBalance==TYPE_BALANCE_BOOK){
                textViewBalance.setText(formatBalanceString(bookBalance));
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

