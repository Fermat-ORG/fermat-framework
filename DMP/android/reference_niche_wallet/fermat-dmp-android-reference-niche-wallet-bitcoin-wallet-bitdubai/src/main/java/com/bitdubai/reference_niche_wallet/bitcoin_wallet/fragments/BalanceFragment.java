package com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments;


import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import android.widget.Toast;

import com.bitdubai.android_fermat_dmp_wallet_bitcoin.R;


import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.enums.BalanceType;
import com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.crypto_wallet.exceptions.CantGetBalanceException;
import com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.crypto_wallet.exceptions.CantGetCryptoWalletException;
import com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.crypto_wallet.interfaces.CryptoWallet;
import com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.crypto_wallet.interfaces.CryptoWalletManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedWalletExceptionSeverity;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.enums.ShowMoneyType;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.session.ReferenceWalletSession;

import java.util.UUID;

import static com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.utils.WalletUtils.formatBalanceString;
import static com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.utils.WalletUtils.showMessage;

/**
 * Created by Natalia on 02/06/2015.
 */
public class BalanceFragment extends Fragment {

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
     * TypeFace to apply in all fragment
     */
    Typeface tf;

    /**
     * Wallet Session
     */

    ReferenceWalletSession referenceWalletSession;
    


    /**
     *  Create a new instance of BalanceFragment and set referenceWalletSession and platforms plugin inside
     * @param position
     * @param walletSession   An object that contains all session data
     * @return BalanceFragment with Session and platform plugins inside
     */

    public static BalanceFragment newInstance(int position,com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.WalletSession walletSession) {
        BalanceFragment balanceFragment = new BalanceFragment();
        balanceFragment.setReferenceWalletSession((ReferenceWalletSession) walletSession);
        return balanceFragment;
    }

    /**
     *
     * @param savedInstanceState
     */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        /**
         *
         */
        try {

            tf = Typeface.createFromAsset(getActivity().getAssets(), "fonts/CaviarDreams.ttf");

            balanceAvailable = 0;
            bookBalance = 0;
            cryptoWalletManager = referenceWalletSession.getCryptoWalletManager();


            /**
             * Get cryptoWalet that manage balance in wallet
             */
            cryptoWallet = cryptoWalletManager.getCryptoWallet();


            /**
             * Get AvailableBalance
             */
            balanceAvailable = cryptoWallet.getAvailableBalance(wallet_id);

            /**
             * Get BookBalance
             */
            bookBalance = cryptoWallet.getBookBalance(wallet_id);
        }
         catch (CantGetCryptoWalletException e) {
             referenceWalletSession.getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.CRASH, FermatException.wrapException(e));
                Toast.makeText(getActivity().getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();
         }
        catch (CantGetBalanceException e){
            referenceWalletSession.getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.CRASH, FermatException.wrapException(e));
                Toast.makeText(getActivity().getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();

        } catch (Exception ex) {
            referenceWalletSession.getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.CRASH, FermatException.wrapException(ex));
            Toast.makeText(getActivity().getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();

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


        // Loading a setting textView Balance amount
        txtViewBalance = ((TextView) rootView.findViewById(R.id.txtViewBalance));
        txtViewBalance.setTypeface(tf);
        txtViewBalance.setText(formatBalanceString(balanceAvailable, ShowMoneyType.BITCOIN.getCode()));
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

        ReferenceWalletSession referenceWalletSession =(ReferenceWalletSession) this.referenceWalletSession;
        try
        {
            if(referenceWalletSession.getBalanceTypeSelected()==BalanceType.AVAILABLE.getCode()) {
                txtViewBalance.setText(formatBalanceString(bookBalance, referenceWalletSession.getTypeAmount()));
                txtViewTypeBalance.setText(R.string.book_balance);
                referenceWalletSession.setBalanceTypeSelected(BalanceType.BOOK);
            }else if (referenceWalletSession.getBalanceTypeSelected()==BalanceType.BOOK.getCode()){
                txtViewBalance.setText(formatBalanceString(balanceAvailable, referenceWalletSession.getTypeAmount()));
                txtViewTypeBalance.setText(R.string.available_balance);
                referenceWalletSession.setBalanceTypeSelected(BalanceType.AVAILABLE);
            }
        }catch (Exception e){
            referenceWalletSession.getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.CRASH, FermatException.wrapException(e));
            Toast.makeText(getActivity().getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();


        }

    }

    /*
        Method to change the balance amount
     */
    private void changeBalance() {
        try {
            if (referenceWalletSession.getBalanceTypeSelected()==BalanceType.AVAILABLE.getCode()){
                txtViewBalance.setText(formatBalanceString(balanceAvailable, referenceWalletSession.getTypeAmount()));
            }else if (referenceWalletSession.getBalanceTypeSelected()==BalanceType.BOOK.getCode()){
                txtViewBalance.setText(formatBalanceString(bookBalance, referenceWalletSession.getTypeAmount()));
            }
        }catch (Exception e){
            referenceWalletSession.getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.CRASH, FermatException.wrapException(e));
            Toast.makeText(getActivity().getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();

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

            balanceAvailable = cryptoWallet.getAvailableBalance(wallet_id);

            bookBalance = cryptoWallet.getBookBalance(wallet_id);


            if(referenceWalletSession.getBalanceTypeSelected()==BalanceType.AVAILABLE.getCode()){
                txtViewBalance.setText(formatBalanceString(balanceAvailable, referenceWalletSession.getTypeAmount()));
            }else if(referenceWalletSession.getBalanceTypeSelected()==BalanceType.BOOK.getCode()){
                txtViewBalance.setText(formatBalanceString(bookBalance, referenceWalletSession.getTypeAmount()));
            }

        }catch (CantGetBalanceException e)
        {
            referenceWalletSession.getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.CRASH, FermatException.wrapException(e));
                Toast.makeText(getActivity().getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();
        } catch (Exception ex){
            referenceWalletSession.getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.CRASH, FermatException.wrapException(ex));
            Toast.makeText(getActivity().getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();
        }
    }


    /**
     *  Set Wallet Session object
     * @param referenceWalletSession
     */
    public void setReferenceWalletSession(ReferenceWalletSession referenceWalletSession) {
        this.referenceWalletSession = referenceWalletSession;
    }
}

