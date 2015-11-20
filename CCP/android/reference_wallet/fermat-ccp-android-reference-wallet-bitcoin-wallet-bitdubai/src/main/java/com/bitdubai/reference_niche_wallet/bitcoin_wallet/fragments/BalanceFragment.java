package com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments;


import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatWalletFragment;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWalletTransaction;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantGetBalanceException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantGetCryptoWalletException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWallet;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWalletManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_resources.interfaces.WalletResourcesProviderManager;

import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums.TransactionType;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedUIExceptionSeverity;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.custom_view.ListComponent;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.enums.ShowMoneyType;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.preference_settings.ReferenceWalletPreferenceSettings;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.session.ReferenceWalletSession;

import java.util.ArrayList;
import java.util.List;
import com.bitdubai.android_fermat_ccp_wallet_bitcoin.R;

import static com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.utils.WalletUtils.formatBalanceString;

/**
 * Created by Matias Furszyfer on 02/06/2015.
 */
public class BalanceFragment extends FermatWalletFragment {

    String walletPublicKey = "reference_wallet";

    private final String CRYPTO_WALLET_PARAM = "cryptoWalletParam";

    /**
     * Screen members
     */

    private View rootView;
    private SwipeRefreshLayout swipeLayout;
    private TextView txtViewTypeBalance;
    private TextView txtViewBalance;

    /**
     * Platform members
     */

    long balanceAvailable;
    long bookBalance;

    /**
     * DealsWithWalletModuleCryptoWallet Interface member variables.
     */

    private CryptoWalletManager cryptoWalletManager;
    private CryptoWallet cryptoWallet;

    /**
     * Resources
     */
    WalletResourcesProviderManager walletResourcesProviderManager;

    /**
     * TypeFace to apply in all fragment
     */
    Typeface tf;

    /**
     * Wallet Session
     */

    private ReferenceWalletSession referenceWalletSession;

    ReferenceWalletPreferenceSettings referenceWalletPreferenceSettings;

    /**
     * list of last 5 transactions
     */
    List<CryptoWalletTransaction> lstCryptoWalletTransactions = new ArrayList<>();


    /**
     * Create a new instance of BalanceFragment and set referenceWalletSession and platforms plugin inside
     *

     * @return BalanceFragment with Session and platform plugins inside
     */

    public static BalanceFragment newInstance() {
        BalanceFragment balanceFragment = new BalanceFragment();
        return balanceFragment;
    }

    /**
     * @param savedInstanceState
     */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setRetainInstance(true);

        referenceWalletSession = (ReferenceWalletSession) walletSession;
        /**
         *
         */
        try {

            tf = Typeface.createFromAsset(getActivity().getAssets(), "fonts/roboto.ttf");
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
            balanceAvailable = cryptoWallet.getBalance(BalanceType.AVAILABLE, referenceWalletSession.getWalletSessionType().getWalletPublicKey());

            /**
             * Get BookBalance
             */
            bookBalance = cryptoWallet.getBalance(BalanceType.BOOK, walletPublicKey);
        } catch (CantGetCryptoWalletException e) {
            referenceWalletSession.getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.CRASH, FermatException.wrapException(e));
            Toast.makeText(getActivity().getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();
        } catch (CantGetBalanceException e) {
            referenceWalletSession.getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.CRASH, FermatException.wrapException(e));
            Toast.makeText(getActivity().getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();

        } catch (Exception ex) {
            referenceWalletSession.getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.CRASH, FermatException.wrapException(ex));
            Toast.makeText(getActivity().getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);


    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    /**
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        try {


            rootView = inflater.inflate(R.layout.wallets_bitcoin_fragment_balance, container, false);


            // Loading a setting textView Balance type
            txtViewTypeBalance = (TextView) rootView.findViewById(R.id.txtViewTypeBalance);
            txtViewTypeBalance.setTypeface(tf);
            txtViewTypeBalance.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    changeBalanceType();
                }
            });


            // Loading a setting textView Balance amount
            txtViewBalance = ((TextView) rootView.findViewById(R.id.txtViewBalance));
            //txtViewBalance.setTypeface(tf);
            txtViewBalance.setText(formatBalanceString(balanceAvailable, ShowMoneyType.BITCOIN.getCode()));
            txtViewBalance.setTypeface(tf);
            ViewCompat.setElevation(txtViewBalance, 30);

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

            List<ListComponent> lstData = new ArrayList<>();

            ReferenceWalletPreferenceSettings referenceWalletPreferenceSettings = new ReferenceWalletPreferenceSettings();
            this.referenceWalletPreferenceSettings = new ReferenceWalletPreferenceSettings();

            BalanceType balanceType = referenceWalletPreferenceSettings.getBalanceType() != null ? referenceWalletPreferenceSettings.getBalanceType() : BalanceType.AVAILABLE;
            lstCryptoWalletTransactions.addAll(
                    cryptoWallet.getTransactions(
                            referenceWalletSession.getIntraUserModuleManager().getActiveIntraUserIdentity().getPublicKey(),
                            balanceType,
                            TransactionType.DEBIT,
                            walletPublicKey,
                            referenceWalletPreferenceSettings.getTransactionsToShow(),
                            0
                    )
            );

            lstCryptoWalletTransactions.addAll(
                    cryptoWallet.getTransactions(
                            referenceWalletSession.getIntraUserModuleManager().getActiveIntraUserIdentity().getPublicKey(),
                            balanceType,
                            TransactionType.CREDIT,
                            walletPublicKey,
                            referenceWalletPreferenceSettings.getTransactionsToShow(),
                            0
                )
            );

           for (CryptoWalletTransaction cryptoWalletTransaction : lstCryptoWalletTransactions) {
               if (cryptoWalletTransaction.getBalanceType().getCode().equals(referenceWalletSession.getBalanceTypeSelected())) {
                   //ListComponent listComponent = new ListComponent(cryptoWalletTransaction);
                   //lstData.add(listComponent);
                }
            }

            Resources res = getResources();

//            CustomComponentMati custonMati = (CustomComponentMati) rootView.findViewById(R.id.custonMati);
//
//            custonMati.setResources(res);
//
//            custonMati.setWalletResources(referenceWalletSession.getWalletResourcesProviderManager());
//            custonMati.setWalletSettings(referenceWalletPreferenceSettings);
//            custonMati.setDataList(lstData);
//
//            custonMati.setLastTransactionsEvent(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    //((FermatNotifications) getActivity()).launchNotification("Mati notification", "Reference wallet", "Vendiste una lata de café por 100 btc");
//
//                }
//            });
//            custonMati.setSeeAlltransactionsEvent(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    ((FermatScreenSwapper) getActivity()).changeActivity("CWRWBWBV1T");
//                }
//            });
        } catch (Exception e) {
            e.printStackTrace();
        }


        return rootView;
    }


    /**
     * Method to change the balance type
     */
    private void changeBalanceType() {

        try {
            if (referenceWalletSession.getBalanceTypeSelected().equals(BalanceType.AVAILABLE.getCode())) {
                txtViewBalance.setText(formatBalanceString(bookBalance, referenceWalletSession.getTypeAmount()));
                txtViewTypeBalance.setText(R.string.book_balance);

                referenceWalletSession.setBalanceTypeSelected(BalanceType.BOOK);
            } else if (referenceWalletSession.getBalanceTypeSelected().equals(BalanceType.BOOK.getCode())) {
                txtViewBalance.setText(formatBalanceString(balanceAvailable, referenceWalletSession.getTypeAmount()));
                txtViewTypeBalance.setText(R.string.available_balance);
                referenceWalletSession.setBalanceTypeSelected(BalanceType.AVAILABLE);
            }
        } catch (Exception e) {
            referenceWalletSession.getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.CRASH, FermatException.wrapException(e));
            Toast.makeText(getActivity().getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * Method to change the balance amount
     */
    private void changeBalance() {
        try {
            if (referenceWalletSession.getBalanceTypeSelected().equals(BalanceType.AVAILABLE.getCode())) {
                txtViewBalance.setText(formatBalanceString(balanceAvailable, referenceWalletSession.getTypeAmount()));
            } else if (referenceWalletSession.getBalanceTypeSelected().equals(BalanceType.BOOK.getCode())) {
                txtViewBalance.setText(formatBalanceString(bookBalance, referenceWalletSession.getTypeAmount()));
            }
        } catch (Exception e) {
            referenceWalletSession.getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.CRASH, FermatException.wrapException(e));
            Toast.makeText(getActivity().getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Method to run the swipeRefreshLayout
     */
    private void refreshBalanceContent() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshBalance();
                swipeLayout.setRefreshing(false);
            }
        }, 3000);
    }

    /**
     * Method to refresh amount of BookBalance and AvailableBalance
     */
    private void refreshBalance() {
        try {

            balanceAvailable = cryptoWallet.getBalance(BalanceType.AVAILABLE, walletPublicKey);

            bookBalance = cryptoWallet.getBalance(BalanceType.BOOK, walletPublicKey);

            if (referenceWalletSession.getBalanceTypeSelected().equals(BalanceType.AVAILABLE.getCode())) {
                txtViewBalance.setText(formatBalanceString(balanceAvailable, referenceWalletSession.getTypeAmount()));
            } else if (referenceWalletSession.getBalanceTypeSelected().equals(BalanceType.BOOK.getCode())) {
                txtViewBalance.setText(formatBalanceString(bookBalance, referenceWalletSession.getTypeAmount()));
            }
        } catch (CantGetBalanceException e) {
            referenceWalletSession.getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.CRASH, FermatException.wrapException(e));
            Toast.makeText(getActivity().getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();
        } catch (Exception ex) {
            referenceWalletSession.getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.CRASH, FermatException.wrapException(ex));
            Toast.makeText(getActivity().getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Set Wallet Session object
     *
     * @param referenceWalletSession
     */
    public void setReferenceWalletSession(ReferenceWalletSession referenceWalletSession) {
        this.referenceWalletSession = referenceWalletSession;
    }

    public void setWalletResourcesProviderManager(WalletResourcesProviderManager walletResourcesProviderManager) {
        this.walletResourcesProviderManager = walletResourcesProviderManager;
    }
}

