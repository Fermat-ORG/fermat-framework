package com.bitdubai.reference_niche_wallet.fermat_wallet.fragments.wallet_final_version;

import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bitdubai.android_fermat_ccp_wallet_fermat.R;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_android_api.ui.enums.FermatRefreshTypes;
import com.bitdubai.fermat_android_api.ui.fragments.FermatWalletListFragment;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatListItemListeners;
import com.bitdubai.fermat_android_api.ui.util.FermatDividerItemDecoration;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.modules.exceptions.ActorIdentityNotSelectedException;
import com.bitdubai.fermat_api.layer.modules.exceptions.CantGetSelectedActorIdentityException;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.fermat_ccp_api.all_definition.ExchangeRateProvider;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums.TransactionType;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.fermat_wallet.interfaces.FermatWalletTransaction;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.fermat_wallet.FermatWalletSettings;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.fermat_wallet.exceptions.CantGetBalanceException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.fermat_wallet.interfaces.FermatWallet;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.fermat_wallet.interfaces.FermatWalletModuleTransaction;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions.CantGetCurrencyExchangeProviderException;
import com.bitdubai.reference_niche_wallet.fermat_wallet.common.adapters.ReceivetransactionsAdapter;
import com.bitdubai.reference_niche_wallet.fermat_wallet.common.adapters.ViewPagerAdapter;
import com.bitdubai.reference_niche_wallet.fermat_wallet.common.animation.AnimationManager;
import com.bitdubai.reference_niche_wallet.fermat_wallet.common.enums.ShowMoneyType;
import com.bitdubai.reference_niche_wallet.fermat_wallet.common.popup.BlockchainDownloadInfoDialog;
import com.bitdubai.reference_niche_wallet.fermat_wallet.common.popup.PresentationBitcoinWalletDialog;
import com.bitdubai.reference_niche_wallet.fermat_wallet.common.utils.WalletUtils;
import com.bitdubai.reference_niche_wallet.fermat_wallet.session.SessionConstant;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static android.widget.Toast.makeText;


/**
 * Fragment the show the list of open negotiations waiting for the broker and the customer un the Home activity
 *
 * @author MAtias Furszyfer
 */

public class SendTransactionFragment2 extends FermatWalletListFragment<FermatWalletModuleTransaction,ReferenceAppFermatSession<FermatWallet>,ResourceProviderManager>
        implements FermatListItemListeners<FermatWalletTransaction> {




    private  BlockchainNetworkType blockchainNetworkType;
    private long before = 0;
    private long after = 0;
    private boolean pressed = false;

    private Thread background;

    // Fermat Managers
    private FermatWallet moduleManager;
    private List<FermatWalletModuleTransaction> lstFermatWalletTransactions;
    private TextView txt_type_balance;
    private TextView txt_balance_amount;
    private TextView txt_Date_time;
    private TextView txt_rate_amount;
    private long balanceAvailable;
    private View rootView;
    private List<FermatWalletModuleTransaction> lstCryptoWalletTransactionsAvailable;
    private long bookBalance;
    private LinearLayout emptyListViewsContainer;
    private AnimationManager animationManager;
    private TextView txt_type_balance_amount;
    private int progress1=1;
    private Map<Long, Long> runningDailyBalance;
    final Handler handler = new Handler();


    private UUID exchangeProviderId = null;

    private FermatWalletSettings fermatWalletSettings = null;

    private ExecutorService _executor;
    private BalanceType balanceType = BalanceType.AVAILABLE;
    private ShowMoneyType typeAmountSelected = ShowMoneyType.BITCOIN;

    public static SendTransactionFragment2 newInstance() {
        return new SendTransactionFragment2();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        _executor = Executors.newFixedThreadPool(5);
        setHasOptionsMenu(true);

        lstCryptoWalletTransactionsAvailable = new ArrayList<>();
        _executor.execute(new Runnable() {
            @Override
            public void run() {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    final Drawable drawable = getResources().getDrawable(R.drawable.background_white_gradient, null);
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

            moduleManager = appSession.getModuleManager();


            if(appSession.getData(SessionConstant.TYPE_BALANCE_SELECTED) != null)
                balanceType = (BalanceType)appSession.getData(SessionConstant.TYPE_BALANCE_SELECTED);
            else
                appSession.setData(SessionConstant.TYPE_BALANCE_SELECTED, balanceType);

            if(appSession.getData(SessionConstant.TYPE_AMOUNT_SELECTED) != null)
                typeAmountSelected = (ShowMoneyType)appSession.getData(SessionConstant.TYPE_AMOUNT_SELECTED);
            else
                appSession.setData(SessionConstant.TYPE_AMOUNT_SELECTED, typeAmountSelected);

            //get wallet settings
            try {
                fermatWalletSettings = moduleManager.loadAndGetSettings(appSession.getAppPublicKey());
            } catch (Exception e) {
                fermatWalletSettings = null;
            }

            if (fermatWalletSettings == null) {
                fermatWalletSettings = new FermatWalletSettings();
                fermatWalletSettings.setIsContactsHelpEnabled(true);
                fermatWalletSettings.setIsPresentationHelpEnabled(true);
                fermatWalletSettings.setNotificationEnabled(true);
                fermatWalletSettings.setIsBlockchainDownloadEnabled(true);
                blockchainNetworkType = BlockchainNetworkType.getDefaultBlockchainNetworkType();
                fermatWalletSettings.setBlockchainNetworkType(blockchainNetworkType);
                if(moduleManager!=null)
                    moduleManager.persistSettings(appSession.getAppPublicKey(), fermatWalletSettings);
            } else {
                if (fermatWalletSettings.getBlockchainNetworkType() == null)
                    fermatWalletSettings.setBlockchainNetworkType(BlockchainNetworkType.getDefaultBlockchainNetworkType());
                else
                    blockchainNetworkType = fermatWalletSettings.getBlockchainNetworkType();
            }

            try {
                if(moduleManager!=null) moduleManager.persistSettings(appSession.getAppPublicKey(), fermatWalletSettings);
            } catch (Exception e) {
                e.printStackTrace();
            }

            final FermatWalletSettings bitcoinWalletSettingsTemp = fermatWalletSettings;
            _executor.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        handler.postDelayed(new Runnable() {
                            public void run() {
                                if (bitcoinWalletSettingsTemp.isPresentationHelpEnabled()) {
                                    setUpPresentation(false);
                                }
                                setRunningDailyBalance();

                            }
                        }, 500);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });

            //list transaction on background

            onRefresh();

            //check blockchain progress

                       /*         try {
                                    int pendingBlocks = moduleManager.getBlockchainDownloadProgress(blockchainNetworkType).getPendingBlocks();
                                    final Toolbar toolBar = getToolbar();
                                    int toolbarColor = 0;
                                    if (pendingBlocks > 0) {
                                        //paint toolbar on red
                                        toolbarColor = Color.RED;
                                        if (fermatWalletSettings.isBlockchainDownloadEnabled())
                                            setUpBlockchainProgress(fermatWalletSettings.isBlockchainDownloadEnabled());
                                    } else {
                                        toolbarColor = Color.parseColor("#12aca1");
                                    }
                                    final int finalToolbarColor = toolbarColor;
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            toolBar.setBackgroundColor(finalToolbarColor);
                                                             }
                                    });
                                }catch (Exception e){
                                    e.printStackTrace();
                                }*/




        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    private void setUpPresentation(boolean checkButton) {
        PresentationBitcoinWalletDialog presentationBitcoinWalletDialog =
                new PresentationBitcoinWalletDialog(
                        getActivity(),
                        appSession,
                        null,
                        (moduleManager.getActiveIdentities().isEmpty()) ? PresentationBitcoinWalletDialog.TYPE_PRESENTATION : PresentationBitcoinWalletDialog.TYPE_PRESENTATION_WITHOUT_IDENTITIES,
                        checkButton);


        presentationBitcoinWalletDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                Object o = appSession.getData(SessionConstant.PRESENTATION_IDENTITY_CREATED);
                if (o != null) {
                    if ((Boolean) (o))
                        appSession.removeData(SessionConstant.PRESENTATION_IDENTITY_CREATED);
                }
                //noinspection TryWithIdenticalCatches
                try {
                    ActiveActorIdentityInformation cryptoWalletIntraUserIdentity = moduleManager.getSelectedActorIdentity();
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

    private void setUpBlockchainProgress(final boolean checkButton) {

        final int type = (moduleManager.getActiveIdentities().isEmpty()) ? PresentationBitcoinWalletDialog.TYPE_PRESENTATION : PresentationBitcoinWalletDialog.TYPE_PRESENTATION_WITHOUT_IDENTITIES;


        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                BlockchainDownloadInfoDialog blockchainDownloadInfoDialog =
                        new BlockchainDownloadInfoDialog(
                                getActivity(),
                                appSession,
                                null,
                                type,
                                checkButton);


                blockchainDownloadInfoDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                    }
                });
                blockchainDownloadInfoDialog.show();
            }
        });

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        try {
            super.onActivityCreated(savedInstanceState);
            animationManager = new AnimationManager(rootView,emptyListViewsContainer);
            //getPaintActivtyFeactures().addCollapseAnimation(animationManager);
        } catch (Exception e){
            makeText(getActivity(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();
            appSession.getErrorManager().reportUnexpectedUIException(UISource.VIEW, UnexpectedUIExceptionSeverity.CRASH, e);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = super.onCreateView(inflater, container, savedInstanceState);
        setUp(inflater);

        return rootView;
    }

    @Override
    public void onResume() {
        animationManager = new AnimationManager(rootView, emptyListViewsContainer);
        //getPaintActivtyFeactures().addCollapseAnimation(animationManager);
        super.onResume();
    }

    @Override
    public void onStop() {
        //getPaintActivtyFeactures().removeCollapseAnimation(animationManager);
        super.onStop();
    }

    private void setUp(LayoutInflater inflater){
        try {
           // setUpDonut(inflater);
            setUpHeader(inflater);
            setUpScreen();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setUpScreen(){
        int[] emptyOriginalPos = new int[2];
        if(emptyListViewsContainer!=null)
            emptyListViewsContainer.getLocationOnScreen(emptyOriginalPos);    }


    private void setUpHeader(LayoutInflater inflater)  throws CantGetBalanceException {
        final RelativeLayout container_header_balance = getToolbarHeader();
        try{
            container_header_balance.removeAllViews();
        }catch (Exception e){
            e.printStackTrace();
        }
        final int sdk = android.os.Build.VERSION.SDK_INT;
        if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            container_header_balance.setBackgroundDrawable( getResources().getDrawable(R.drawable.background_white_gradient) );
        } else {
            container_header_balance.setBackground( getResources().getDrawable(R.drawable.background_white_gradient));
        }

        final View header_layout = inflater.inflate(R.layout.fermat_wallet_home_header,container_header_balance,true);
        container_header_balance.setVisibility(View.VISIBLE);


        //Select all header Element
        txt_balance_amount      = (TextView) header_layout.findViewById(R.id.txt_balance_amount);
        txt_type_balance_amount = (TextView) header_layout.findViewById(R.id.txt_type_balance_amount);
        txt_type_balance        = (TextView) header_layout.findViewById(R.id.txt_type_balance);
        txt_Date_time           = (TextView) header_layout.findViewById(R.id.txt_date_time);
        txt_rate_amount         = (TextView) header_layout.findViewById(R.id.txt_rate_amount);
        ViewPager vpPager       = (ViewPager) header_layout.findViewById(R.id.vpPager);
        TabLayout tabLayout     = (TabLayout) header_layout.findViewById(R.id.sliding_tabs);

        final String date;
        final String time;
        SimpleDateFormat sdf1 = new SimpleDateFormat("MMM dd, yyyy");
        SimpleDateFormat sdf2 = new SimpleDateFormat("hh:ss a");

        date = sdf1.format(System.currentTimeMillis());
        time = sdf2.format(System.currentTimeMillis());

        txt_Date_time.setText(time + " | " + date);

        //Event Click For change the balance type
        txt_type_balance.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                changeBalanceType(txt_type_balance, txt_balance_amount);
            }
        });

        //Event Click For change the balance type
        txt_type_balance.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                changeBalanceType(txt_type_balance, txt_balance_amount);
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
        txt_type_balance_amount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                changeAmountType();
            }
        });

       // moduleManager.getExchangeRateProviders()
        List<ExchangeRateProvider> exchangeProviderList = new ArrayList<>();

        try {

            List<ExchangeRateProvider> ProviderList  = moduleManager.getExchangeRateProviders();

            for (ExchangeRateProvider lst : ProviderList) {

                String name = lst.getProviderName();
                UUID id = lst.getProviderId();

                if (id!=null || name!=null)
                    exchangeProviderList.add(lst);
                }



            FragmentStatePagerAdapter adapterViewPager;
            adapterViewPager = new ViewPagerAdapter(getFragmentManager(),exchangeProviderList,appSession);
            vpPager.setAdapter(adapterViewPager);


            vpPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {

                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });

            // Give the TabLayout the ViewPager
            tabLayout.setupWithViewPager(vpPager);
            tabLayout.setLeft(5);
            tabLayout.setTop(5);

        } catch (CantGetCurrencyExchangeProviderException e) {
            e.printStackTrace();
        }

        long balance = 0;


        if (balanceType.equals(BalanceType.AVAILABLE))
            balance =  moduleManager.getBalance(BalanceType.AVAILABLE, appSession.getAppPublicKey(), blockchainNetworkType);
        else
            //balance = moduleManager.getRealBalance(appSession.getAppPublicKey(), blockchainNetworkType);

        txt_balance_amount.setText(WalletUtils.formatBalanceString(balance, typeAmountSelected.getCode()));

        updateBalances();
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        super.onCreateOptionsMenu(menu, inflater);



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try {
            int id = item.getItemId();
            if(id == 1){
                changeActivity(Activities.CCP_BITCOIN_FERMAT_WALLET_SEND_FORM_ACTIVITY,appSession.getAppPublicKey());
                return true;
            }else{
                setUpPresentation(moduleManager.loadAndGetSettings(appSession.getAppPublicKey()).isPresentationHelpEnabled());
                return true;
            }
        } catch (Exception e) {
            // errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));
            makeText(getActivity(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void initViews(View layout) {
        super.initViews(layout);

        RecyclerView.ItemDecoration itemDecoration = new FermatDividerItemDecoration(getActivity(), R.drawable.cbw_divider_shape);
       recyclerView.addItemDecoration(itemDecoration);

        if(lstFermatWalletTransactions!=null) {
            if (lstFermatWalletTransactions.isEmpty()) {
               recyclerView.setVisibility(View.GONE);
                emptyListViewsContainer = (LinearLayout) layout.findViewById(R.id.empty);
                //FermatAnimationsUtils.showEmpty(getActivity(), true, emptyListViewsContainer);
                emptyListViewsContainer.setVisibility(View.VISIBLE);
            }
        }else{
            recyclerView.setVisibility(View.GONE);
            emptyListViewsContainer = (LinearLayout) layout.findViewById(R.id.empty);
            //FermatAnimationsUtils.showEmpty(getActivity(), true, emptyListViewsContainer);
            emptyListViewsContainer.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected boolean hasMenu() {
        return true;
    }


    @Override
    public FermatAdapter getAdapter() {
        if (adapter == null) {
            adapter = new ReceivetransactionsAdapter(getActivity(), lstFermatWalletTransactions,getResources());
            // setting up event listeners
        }
        return adapter;
    }

    @Override
    public RecyclerView.LayoutManager getLayoutManager() {
        if (layoutManager == null)
            layoutManager = new LinearLayoutManager(getActivity());
        return layoutManager;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fermat_wallet_home_send_main;
    }

    @Override
    protected int getRecyclerLayoutId() {
        return R.id.open_contracts_recycler_view;
    }

    @Override
    protected int getSwipeRefreshLayoutId() {
        return R.id.swipe_refresh;
    }

    @Override
    public List<FermatWalletModuleTransaction> getMoreDataAsync(FermatRefreshTypes refreshType, int pos) {
        List<FermatWalletModuleTransaction> data = new ArrayList<>();
        lstCryptoWalletTransactionsAvailable = new ArrayList<>();

        //noinspection TryWithIdenticalCatches
        try {
            ActiveActorIdentityInformation intraUserLoginIdentity = moduleManager.getSelectedActorIdentity();
            if(intraUserLoginIdentity!=null) {
                String intraUserPk = intraUserLoginIdentity.getPublicKey();


                int MAX_TRANSACTIONS = 20;
                List<FermatWalletModuleTransaction> list = moduleManager.listLastActorTransactionsByTransactionType(
                        BalanceType.AVAILABLE, TransactionType.DEBIT, appSession.getAppPublicKey(),
                        intraUserPk, blockchainNetworkType, MAX_TRANSACTIONS, 0);

                if(list!=null) {
                    lstCryptoWalletTransactionsAvailable.addAll(list);
                }

                if(!data.isEmpty())
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            //FermatAnimationsUtils.showEmpty(getActivity(), true, emptyListViewsContainer);
                            emptyListViewsContainer.setVisibility(View.VISIBLE);
                        }
                    });
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return lstCryptoWalletTransactionsAvailable;
    }

    @Override
    protected boolean recyclerHasFixedSize() {
        return true;
    }


    @Override
    public void onPostExecute(Object... result) {
        isRefreshing = false;
        if (isAttached) {
            swipeRefreshLayout.setRefreshing(false);
            if (result != null && result.length > 0) {
                //noinspection unchecked
                lstFermatWalletTransactions = (ArrayList) result[0];
                if (adapter != null)
                    adapter.changeDataSet(lstFermatWalletTransactions);

                if(lstFermatWalletTransactions.size() > 0)
                {
                    recyclerView.setVisibility(View.VISIBLE);
                   // FermatAnimationsUtils.showEmpty(getActivity(), false, emptyListViewsContainer);
                    emptyListViewsContainer.setVisibility(View.GONE);
                }
            }
            else {
                recyclerView.setVisibility(View.GONE);
                //FermatAnimationsUtils.showEmpty(getActivity(), true, emptyListViewsContainer);
                emptyListViewsContainer.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onErrorOccurred(Exception ex) {
        isRefreshing = false;
        if (isAttached) {
            swipeRefreshLayout.setRefreshing(false);
            //errorManager.reportUnexpectedPluginException(Plugins.CRYPTO_WALLET, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, ex);
            ex.printStackTrace();
        }
    }

    private void changeAmountType() {
        ShowMoneyType showMoneyType = (typeAmountSelected.getCode()== ShowMoneyType.BITCOIN.getCode()) ? ShowMoneyType.BITS : ShowMoneyType.BITCOIN;
        appSession.setData(SessionConstant.TYPE_AMOUNT_SELECTED,showMoneyType);
        typeAmountSelected = showMoneyType;
        String moneyTpe = "";
        switch (showMoneyType){
            case BITCOIN:
                moneyTpe = "btc";
                txt_balance_amount.setTextSize(28);
                break;
            case BITS:
                moneyTpe = "bits";
                txt_balance_amount.setTextSize(20);
                break;
        }

        txt_type_balance_amount.setText(moneyTpe);
        updateBalances();
    }

    /**
     * Method to change the balance type
     */
    private void changeBalanceType(TextView txt_type_balance,TextView txt_balance_amount) {
        updateBalances();
        setRunningDailyBalance();
        try {


            if (balanceType.getCode().equals(BalanceType.AVAILABLE.getCode())) {

                balanceAvailable = loadBalance(BalanceType.AVAILABLE);
                txt_balance_amount.setText(WalletUtils.formatBalanceString(bookBalance, typeAmountSelected.getCode()));
                txt_type_balance.setText(R.string.book_balance);
                appSession.setData(SessionConstant.TYPE_BALANCE_SELECTED, BalanceType.BOOK);
                balanceType = BalanceType.BOOK;
            } else if (balanceType.getCode().equals(BalanceType.BOOK.getCode())) {
                bookBalance = loadBalance(BalanceType.BOOK);
               txt_balance_amount.setText(WalletUtils.formatBalanceString(balanceAvailable, typeAmountSelected.getCode()));
                txt_type_balance.setText(R.string.available_balance);
                balanceType = BalanceType.AVAILABLE;
                appSession.setData(SessionConstant.TYPE_BALANCE_SELECTED,BalanceType.AVAILABLE);
            }
        } catch (Exception e) {
            appSession.getErrorManager().reportUnexpectedUIException(
                    UISource.ACTIVITY, UnexpectedUIExceptionSeverity.CRASH, FermatException.wrapException(e));
            Toast.makeText(getActivity().getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();
        }

    }

    private long loadBalance(BalanceType balanceType){
        long balance = 0;
        //noinspection TryWithIdenticalCatches
        try {

            balance = moduleManager.getBalance(balanceType, appSession.getAppPublicKey(), blockchainNetworkType);

        } catch (CantGetBalanceException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return balance;
    }


    private void updateBalances() {
        bookBalance = loadBalance(BalanceType.BOOK);
        balanceAvailable = loadBalance(BalanceType.AVAILABLE);
        txt_balance_amount.setText(
                WalletUtils.formatBalanceString(
                        (balanceType.getCode().equals(BalanceType.AVAILABLE.getCode()))
                        ? balanceAvailable : bookBalance, typeAmountSelected.getCode()));

    }


    private int getBalanceAverage() {
        long balanceSum = 0;
        int average = 0;
        try {
            if(runningDailyBalance!=null) {
                for (Map.Entry<Long, Long> entry : runningDailyBalance.entrySet())
                    balanceSum += Integer.valueOf(WalletUtils.formatBalanceStringNotDecimal(entry.getValue(), ShowMoneyType.BITCOIN.getCode()));

                if (balanceSum > 0)
                    average = (int) ((Integer.valueOf(WalletUtils.formatBalanceStringNotDecimal(getBalanceValue(runningDailyBalance.size() - 1), ShowMoneyType.BITCOIN.getCode())) * 100) / balanceSum);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return average;
    }

    private void setRunningDailyBalance() {
        try {

            long currentTime = System.currentTimeMillis();
            runningDailyBalance = new HashMap<>();

            if(fermatWalletSettings != null){

                blockchainNetworkType = fermatWalletSettings.getBlockchainNetworkType();
                if (fermatWalletSettings.getRunningDailyBalance() == null) {
                    try {
                        long balance = moduleManager.getBalance(BalanceType.AVAILABLE, appSession.getAppPublicKey(), blockchainNetworkType);
                        runningDailyBalance.put(currentTime, balance);
                    }catch (Exception e) {
                        Log.e(TAG,"Balance null, please check this, line:"+new Throwable().getStackTrace()[0].getLineNumber());
                    }
                } else {
                    runningDailyBalance = fermatWalletSettings.getRunningDailyBalance();

                    //verify that I have this day added
                    long lastDate = getKeyDate(runningDailyBalance.size()-1);
                    long dif = currentTime - lastDate;
                    double dias = Math.floor(dif / (1000 * 60 * 60 * 24));

                    if(dias > 1) {
                        //if I have 30 days I start counting again
                        if(runningDailyBalance.size() == 30)
                            runningDailyBalance = new HashMap<>();

                        runningDailyBalance.put(currentTime, moduleManager.getBalance(
                                BalanceType.AVAILABLE, appSession.getAppPublicKey(),blockchainNetworkType));
                    } else {
                        //update balance
                        this.updateDailyBalance(runningDailyBalance.size()-1,moduleManager.getBalance(BalanceType.AVAILABLE, appSession.getAppPublicKey(),blockchainNetworkType));
                    }
                }

                fermatWalletSettings.setRunningDailyBalance(runningDailyBalance);
                if(moduleManager!=null) {
                    moduleManager.persistSettings(appSession.getAppPublicKey(), fermatWalletSettings);
                }else {
                    Log.e(TAG,"Settings manager null, please check this line:"+new Throwable().getStackTrace()[0].getLineNumber());
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private long getKeyDate(int pos){
        int i = 0;
        long date = 0;

        try {
            for (Map.Entry<Long, Long> entry :  runningDailyBalance.entrySet()) {
                if(i == pos)
                    date += entry.getKey();
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    private long getBalanceValue(int pos){
        int i = 0;
        long date = 0;

        try {
            for (Map.Entry<Long, Long> entry :  runningDailyBalance.entrySet()) {
                if(i == pos)
                    date += entry.getValue();
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    private long updateDailyBalance(int pos, long balance) {
        int i = 0;
        long date = 0;

        try {
            for (Map.Entry<Long, Long> entry :  runningDailyBalance.entrySet()) {
                if(i == pos) {
                    entry.setValue(balance);
                    break;
                }
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }


    @Override
    public void onUpdateViewOnUIThread(String code){
        try {
            if(code.equals("BlockchainDownloadComplete")) {
                //update toolbar color
                final Toolbar toolBar = getToolbar();

                //toolBar.setBackgroundColor(Color.parseColor("#12aca1"));
                toolBar.setBackground(getResources().getDrawable(R.drawable.background_navigation_drawer));

               // makeText(getActivity(), "Blockchain Download Complete", Toast.LENGTH_SHORT).show();
            } else {
                if(code.equals("Btc_arrive"))
                {
                    //update balance amount
                    final String runningBalance = WalletUtils.formatBalanceStringNotDecimal(
                            moduleManager.getBalance(BalanceType.AVAILABLE, appSession.getAppPublicKey(),
                                    blockchainNetworkType),ShowMoneyType.BITCOIN.getCode());

                    changeBalanceType(txt_type_balance, txt_balance_amount);

                   // circularProgressBar.setProgressValue(Integer.valueOf(runningBalance));
                   // circularProgressBar.setProgressValue2(getBalanceAverage());
                }

            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Override
    public void onItemClickListener(FermatWalletTransaction data, int position) {

    }

    @Override
    public void onLongItemClickListener(FermatWalletTransaction data, int position) {

    }


}

