package com.bitdubai.reference_niche_wallet.loss_protected_wallet.fragments.wallet_final_version;

import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bitdubai.android_fermat_ccp_loss_protected_wallet_bitcoin.R;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.ui.Views.DividerItemDecoration;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_android_api.ui.enums.FermatRefreshTypes;
import com.bitdubai.fermat_android_api.ui.fragments.FermatWalletListFragment;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatListItemListeners;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatWorkerCallBack;
import com.bitdubai.fermat_android_api.ui.util.FermatAnimationsUtils;
import com.bitdubai.fermat_android_api.ui.util.FermatWorker;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedSubAppExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedWalletExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.modules.exceptions.ActorIdentityNotSelectedException;
import com.bitdubai.fermat_api.layer.modules.exceptions.CantGetSelectedActorIdentityException;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums.TransactionType;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantListCryptoWalletIntraUserIdentityException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.LossProtectedWalletSettings;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions.CantGetCryptoLossProtectedWalletException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions.CantGetLossProtectedBalanceException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions.CantListLossProtectedTransactionsException;
import com.bitdubai.fermat_ccp_api.all_definition.ExchangeRateProvider;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.interfaces.LossProtectedWallet;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.interfaces.LossProtectedWalletTransaction;
import com.bitdubai.fermat_cer_api.all_definition.interfaces.ExchangeRate;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.adapters.ChunckValuesHistoryAdapter;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.enums.ShowMoneyType;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.popup.ErrorExchangeRateConnectionDialog;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.popup.PresentationBitcoinWalletDialog;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.utils.WalletUtils;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.utils.onRefreshList;

import com.bitdubai.reference_niche_wallet.loss_protected_wallet.session.SessionConstant;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;

import static android.widget.Toast.makeText;

/**
 * Created by mati on 2015.09.30..
 */
public class ChunckValuesHistoryFragment extends FermatWalletListFragment<LossProtectedWalletTransaction,ReferenceAppFermatSession,ResourceProviderManager>implements FermatListItemListeners<LossProtectedWalletTransaction>,onRefreshList {

    /**
     * Session
     */
    ReferenceAppFermatSession<LossProtectedWallet> lossProtectedWalletSession;
    String walletPublicKey = "loss_protected_wallet";
    /**
     * MANAGERS
     @Override
     protected void bindHolder(ChunckValuesHistoryItemViewHolder holder, LossProtectedWalletTransaction data, int position) {
     }
     */
    private LossProtectedWallet lossProtectedWalletManager;
    /**
     * DATA
     */
    private List<LossProtectedWalletTransaction> lstTransaction;
    private LossProtectedWalletTransaction selectedItem;

    /**
     * Executor Service
     */
    private ExecutorService executor;
    private int MAX_TRANSACTIONS = 20;
    private int offset = 0;
    private View rootView;
    private LinearLayout empty;
    private TextView chunk_balance_TextView;
    private TextView exchange_rate_view;
    private TextView txt_balance_amount;
    private TextView txt_balance_amount_type;
    private TextView txt_type_balance;
    private TextView txt_touch_to_change;
    private TextView txt_exchange_rate;

    private UUID exchangeProviderId = null;

    private long exchangeRate = 0;

    private ErrorManager errorManager;

    private long balanceAvailable;
    private long realBalance;

    private FermatWorker fermatWorker;

    LossProtectedWalletSettings lossProtectedWalletSettings;

    BlockchainNetworkType blockchainNetworkType;

    private BalanceType balanceType = BalanceType.REAL;

    private ShowMoneyType typeAmountSelected = ShowMoneyType.BITCOIN;

    /**
     * Create a new instance of this fragment
     *
     * @return InstalledFragment instance object
     */
    public static ChunckValuesHistoryFragment newInstance() {
        return new ChunckValuesHistoryFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        lossProtectedWalletSession = (ReferenceAppFermatSession<LossProtectedWallet>)appSession;

        lstTransaction = new ArrayList<>();
        try {
            lossProtectedWalletManager = lossProtectedWalletSession.getModuleManager();

            if(appSession.getData(SessionConstant.TYPE_BALANCE_SELECTED) != null)
                balanceType = (BalanceType)appSession.getData(SessionConstant.TYPE_BALANCE_SELECTED);
            else
                appSession.setData(SessionConstant.TYPE_BALANCE_SELECTED, balanceType);

            if(appSession.getData(SessionConstant.TYPE_AMOUNT_SELECTED) != null)
                typeAmountSelected = (ShowMoneyType)appSession.getData(SessionConstant.TYPE_AMOUNT_SELECTED);
            else
                appSession.setData(SessionConstant.TYPE_AMOUNT_SELECTED, typeAmountSelected);


            getExecutor().execute(new Runnable() {
                @Override
                public void run() {
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                        final Drawable drawable = getResources().getDrawable(R.drawable.background_gradient, null);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    getPaintActivtyFeactures().setActivityBackgroundColor(drawable);
                                } catch (OutOfMemoryError o) {
                                    o.printStackTrace();
                                }
                            }
                        });
                    }
                }
            });



            try {
                lossProtectedWalletSettings = lossProtectedWalletManager.loadAndGetSettings(lossProtectedWalletSession.getAppPublicKey());
                this.blockchainNetworkType = lossProtectedWalletSettings.getBlockchainNetworkType();
            }catch (Exception e){

            }

            onRefresh();
        } catch (Exception ex) {
            ex.printStackTrace();
            //CommonLogger.exception(TAG, ex.getMessage(), ex);
            Toast.makeText(getActivity().getApplicationContext(), "Recovering from system error - OnCreate " + ex.getMessage(), Toast.LENGTH_SHORT).show();

        }


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        try {
            rootView = super.onCreateView(inflater, container, savedInstanceState);
            RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), R.drawable.divider_shape);
            recyclerView.addItemDecoration(itemDecoration);
            empty = (LinearLayout) rootView.findViewById(R.id.empty);

            setUpHeader(inflater);

        }catch (Exception e){
            Toast.makeText(getActivity().getApplicationContext(), "Recovering from system error " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return rootView;
    }



    private void  setUpHeader(LayoutInflater inflater) {
        try {

            //Select all header Element
            txt_balance_amount      = (TextView) rootView.findViewById(R.id.txt_balance_amount);
            txt_balance_amount_type = (TextView) rootView.findViewById(R.id.txt_balance_amount_type);
            txt_type_balance        = (TextView) rootView.findViewById(R.id.txt_type_balance);
            txt_touch_to_change     = (TextView) rootView.findViewById(R.id.txt_touch_to_change);
            txt_exchange_rate       = (TextView) rootView.findViewById(R.id.txt_exchange_rate);

            //show Exchange Market Rate
            getAndShowMarketExchangeRateData(rootView);

            //Event Click For change the balance type
            txt_touch_to_change.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    changeBalanceType(txt_type_balance, txt_balance_amount);
                }
            });

            //Event Click For change the balance type
            txt_type_balance.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    changeBalanceType(txt_type_balance,txt_balance_amount);
                }
            });

            //Event for change the balance amount
            txt_balance_amount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    changeAmountType();
                }
            });
            //Event for change the balance amount type
            txt_balance_amount_type.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    changeAmountType();
                }
            });

            long balance = 0;
            if (balanceType.getCode().equals(BalanceType.AVAILABLE))
                balance = lossProtectedWalletManager.getBalance(BalanceType.AVAILABLE, lossProtectedWalletSession.getAppPublicKey(), blockchainNetworkType, "0");
            else
                balance = lossProtectedWalletManager.getRealBalance(lossProtectedWalletSession.getAppPublicKey(), blockchainNetworkType);

            txt_balance_amount.setText(WalletUtils.formatBalanceString(balance, typeAmountSelected.getCode()));


        }catch (Exception e){
            errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));
            makeText(getActivity(), "recovering from system error: setUpHeader Exception - " + e.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }

    }



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        try {
            super.onActivityCreated(savedInstanceState);
            lstTransaction = new ArrayList<>();
        } catch (Exception e){
            makeText(getActivity(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();
            lossProtectedWalletSession.getErrorManager().reportUnexpectedUIException(UISource.VIEW, UnexpectedUIExceptionSeverity.CRASH, e);
        }
    }


    @Override
    protected boolean hasMenu() {
        return true;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.chunck_values_fragment_transaction_main;
    }

    @Override
    protected int getSwipeRefreshLayoutId() {
        return R.id.swipe_refresh;
    }

    @Override
    protected int getRecyclerLayoutId() {
        return R.id.transactions_recycler_view;
    }

    @Override
    protected boolean recyclerHasFixedSize() {
        return true;
    }


    @Override
    @SuppressWarnings("unchecked")
    public FermatAdapter getAdapter() {
        if (adapter == null) {
            adapter = new ChunckValuesHistoryAdapter(getActivity(), lstTransaction,lossProtectedWalletManager,lossProtectedWalletSession,this);
            adapter.setFermatListEventListener(this); // setting up event listeners
        }
        return adapter;
    }

    @Override
    public RecyclerView.LayoutManager getLayoutManager() {
        if (layoutManager == null) {
            layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        }
        return layoutManager;
    }

    @Override
    public List<LossProtectedWalletTransaction> getMoreDataAsync(FermatRefreshTypes refreshType, int pos) throws CantListCryptoWalletIntraUserIdentityException, CantGetCryptoLossProtectedWalletException, CantListLossProtectedTransactionsException {
        try {


            ActiveActorIdentityInformation intraUserLoginIdentity = null;
            intraUserLoginIdentity = lossProtectedWalletManager.getSelectedActorIdentity();
            String intraUserPk = null;
            if (intraUserLoginIdentity != null) {
                intraUserPk = intraUserLoginIdentity.getPublicKey();
            }

            //when refresh offset set 0
            if (refreshType.equals(FermatRefreshTypes.NEW))
                offset = 0;

            lstTransaction = lossProtectedWalletManager.listAllActorTransactionsByTransactionType(
                    BalanceType.AVAILABLE,
                    TransactionType.CREDIT,
                    lossProtectedWalletSession.getAppPublicKey(),
                    intraUserPk,
                    blockchainNetworkType,
                    20, offset);

        } catch (Exception e) {
            lossProtectedWalletSession.getErrorManager().reportUnexpectedSubAppException(SubApps.CWP_WALLET_STORE,
                    UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
            e.printStackTrace();
        }

        return lstTransaction;
    }

    @Override
    public void onItemClickListener(LossProtectedWalletTransaction item, int position) {
        try {
        selectedItem = item;
                //set selected transaction id
            lossProtectedWalletSession.setData(SessionConstant.TRANSACTION_DETAIL_ID,selectedItem.getTransactionId());

            //go to spending details
            changeActivity(Activities.CCP_BITCOIN_LOSS_PROTECTED_WALLET_CHUNCK_VALUE_DETAIL_ACTIVITY,lossProtectedWalletSession.getAppPublicKey());
        } catch (Exception e) {
           e.printStackTrace();
        }
     }

    @Override
    public void onLongItemClickListener(LossProtectedWalletTransaction data, int position) {

    }


    @Override
    public void onPostExecute(Object... result) {
        isRefreshing = false;
        if (isAttached) {
            swipeRefreshLayout.setRefreshing(false);
            if (result != null && result.length > 0) {
                lstTransaction = (ArrayList) result[0];
                if (adapter != null)
                    adapter.changeDataSet(lstTransaction);
                if(lstTransaction.isEmpty()) FermatAnimationsUtils.showEmpty(getActivity(),true,empty);
                else FermatAnimationsUtils.showEmpty(getActivity(),false,empty);

            }
        }
    }

    @Override
    public void onErrorOccurred(Exception ex) {
        isRefreshing = false;
        if (isAttached) {
            swipeRefreshLayout.setRefreshing(false);
            //CommonLogger.exception(TAG, ex.getMessage(), ex);
        }
    }



    public void setReferenceWalletSession(ReferenceAppFermatSession<LossProtectedWallet> referenceWalletSession) {
        this.lossProtectedWalletSession = referenceWalletSession;
    }


    //tool bar menu

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try {

            int id = item.getItemId();

            if(id == 1){
                changeActivity(Activities.CCP_BITCOIN_LOSS_PROTECTED_WALLET_SEND_FORM_ACTIVITY,lossProtectedWalletSession.getAppPublicKey());
                return true;
            }else {

                setUpPresentation(lossProtectedWalletSettings.isPresentationHelpEnabled());


                setUpPresentation(lossProtectedWalletManager.loadAndGetSettings(lossProtectedWalletSession.getAppPublicKey()).isPresentationHelpEnabled());

                return true;
            }


        } catch (Exception e) {
            errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));
            makeText(getActivity(), "Oooops! recovering from system error",
                    Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    private void setUpPresentation(boolean checkButton) {
        PresentationBitcoinWalletDialog presentationBitcoinWalletDialog =
                new PresentationBitcoinWalletDialog(
                        getActivity(),
                        lossProtectedWalletSession,
                        null,
                        (lossProtectedWalletManager.getActiveIdentities().isEmpty()) ? PresentationBitcoinWalletDialog.TYPE_PRESENTATION : PresentationBitcoinWalletDialog.TYPE_PRESENTATION_WITHOUT_IDENTITIES,
                        checkButton);

        presentationBitcoinWalletDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                Object o = lossProtectedWalletSession.getData(SessionConstant.PRESENTATION_IDENTITY_CREATED);
                if (o != null) {
                    if ((Boolean) (o)) {
                        //invalidate();
                        lossProtectedWalletSession.removeData(SessionConstant.PRESENTATION_IDENTITY_CREATED);
                    }
                }
                try {
                    ActiveActorIdentityInformation cryptoWalletIntraUserIdentity = lossProtectedWalletManager.getSelectedActorIdentity();
                    if (cryptoWalletIntraUserIdentity == null) {
                        getActivity().onBackPressed();
                    } else {
                        invalidate();
                    }

                } catch (CantGetSelectedActorIdentityException e) {
                    e.printStackTrace();
                } catch (ActorIdentityNotSelectedException e) {
                    e.printStackTrace();
                }

            }
        });
        presentationBitcoinWalletDialog.show();
    }
    private long loadBalance(BalanceType balanceType){
        long balance = 0;
        try {

            if(balanceType.equals(BalanceType.REAL))
                balance =  lossProtectedWalletManager.getRealBalance(lossProtectedWalletSession.getAppPublicKey(), blockchainNetworkType);

            if(balanceType.equals(BalanceType.AVAILABLE))
                balance =  lossProtectedWalletManager.getBalance(balanceType, lossProtectedWalletSession.getAppPublicKey(), blockchainNetworkType, String.valueOf(lossProtectedWalletSession.getData(SessionConstant.ACTUAL_EXCHANGE_RATE)));


        } catch (CantGetLossProtectedBalanceException e) {
            e.printStackTrace();
        }
        return balance;
    }

    private void updateBalances(){
         realBalance = loadBalance(BalanceType.REAL);
        balanceAvailable = loadBalance(BalanceType.AVAILABLE);
        txt_balance_amount.setText(
                WalletUtils.formatBalanceString(
                        (balanceType.getCode() == BalanceType.AVAILABLE.getCode())
                                ? balanceAvailable : realBalance,
                        typeAmountSelected.getCode())
        );
    }

    private void changeAmountType(){

        ShowMoneyType showMoneyType = (typeAmountSelected.getCode()== ShowMoneyType.BITCOIN.getCode()) ? ShowMoneyType.BITS : ShowMoneyType.BITCOIN;
        lossProtectedWalletSession.setData(SessionConstant.TYPE_AMOUNT_SELECTED,showMoneyType);
        typeAmountSelected = showMoneyType;
        String moneyTpe = "";
        switch (showMoneyType){
            case BITCOIN:
                moneyTpe = "BTC";
                txt_balance_amount.setTextSize(24);
                break;
            case BITS:
                moneyTpe = "BITS";
                txt_balance_amount.setTextSize(24);
                break;
        }

        txt_balance_amount_type.setText(moneyTpe);
        updateBalances();
    }

    /**
     * Method to change the balance type
     */
    private void changeBalanceType(TextView txt_type_balance,TextView txt_balance_amount) {
        updateBalances();
        try {
            if (balanceType.getCode().equals(BalanceType.AVAILABLE.getCode())) {
                realBalance = loadBalance(BalanceType.REAL);
                txt_balance_amount.setText(WalletUtils.formatBalanceString(realBalance, typeAmountSelected.getCode()));
                txt_type_balance.setText(R.string.real_balance_text);
                lossProtectedWalletSession.setData(SessionConstant.TYPE_BALANCE_SELECTED, BalanceType.REAL);
                balanceType = BalanceType.REAL;
            } else if (balanceType.getCode().equals(BalanceType.REAL.getCode())) {
                balanceAvailable = loadBalance(BalanceType.AVAILABLE);
                txt_balance_amount.setText(WalletUtils.formatBalanceString(balanceAvailable,typeAmountSelected.getCode()));
                txt_type_balance.setText(R.string.available_balance_text);
                balanceType = BalanceType.AVAILABLE;
                lossProtectedWalletSession.setData(SessionConstant.TYPE_BALANCE_SELECTED,BalanceType.AVAILABLE);
            }
        } catch (Exception e) {
            lossProtectedWalletSession.getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.CRASH, FermatException.wrapException(e));
            Toast.makeText(getActivity().getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();
        }

    }
    private void getAndShowMarketExchangeRateData(final View container) {

        final int MAX_DECIMAL_FOR_RATE = 2;
        final int MIN_DECIMAL_FOR_RATE = 2;

         fermatWorker = new FermatWorker(getActivity()) {
            @Override
            protected Object doInBackground() throws Exception {

                //default Exchange rate Provider

                if(lossProtectedWalletManager.getExchangeProvider()==null) {
                    List<ExchangeRateProvider> providers = new ArrayList(lossProtectedWalletManager.getExchangeRateProviderManagers());

                    exchangeProviderId = providers.get(0).getProviderId();
                    lossProtectedWalletManager.setExchangeProvider(exchangeProviderId);

                }
                else
                {
                    exchangeProviderId =lossProtectedWalletManager.getExchangeProvider();
                }
                ExchangeRate rate =  lossProtectedWalletManager.getCurrencyExchange(exchangeProviderId);
                return rate;
            }
        };

        fermatWorker.setCallBack(new FermatWorkerCallBack() {
            @Override
            public void onPostExecute(Object... result) {
                if (result != null && result.length > 0) {

                    ExchangeRate rate = (ExchangeRate) result[0];
                    // progressBar.setVisibility(View.GONE);
                    txt_exchange_rate.setText("1 BTC = " + String.valueOf(
                            WalletUtils.formatAmountStringWithDecimalEntry(
                                    rate.getPurchasePrice(),
                                    MAX_DECIMAL_FOR_RATE,
                                    MIN_DECIMAL_FOR_RATE)) + " USD");

                    //get available balance to actual exchange rate

                    lossProtectedWalletSession.setData(SessionConstant.ACTUAL_EXCHANGE_RATE,Double.parseDouble(
                            WalletUtils.formatAmountStringWithDecimalEntry(
                                    rate.getPurchasePrice(),
                                    MAX_DECIMAL_FOR_RATE,
                                    MIN_DECIMAL_FOR_RATE)));
                    updateBalances();
                    onRefresh();

                }else{
                    ErrorExchangeRateConnectionDialog dialog_error = new ErrorExchangeRateConnectionDialog(getActivity());
                    dialog_error.show();

                }
            }

            @Override
            public void onErrorOccurred(Exception ex) {
                //  progressBar.setVisibility(View.GONE);

                txt_exchange_rate.setVisibility(View.GONE);

                ErrorManager errorManager = lossProtectedWalletSession.getErrorManager();
                if (errorManager != null)
                    errorManager.reportUnexpectedWalletException(Wallets.CBP_CRYPTO_BROKER_WALLET,
                            UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, ex);
                else
                    Log.e("Exchange Rate", ex.getMessage(), ex);

                ErrorExchangeRateConnectionDialog dialog_error = new ErrorExchangeRateConnectionDialog(getActivity());
                dialog_error.show();
            }
        });

        fermatWorker.execute();
    }


    @Override
    public void onStop() {


        if(fermatWorker != null)
            fermatWorker.shutdownNow();
        super.onStop();
    }

}
