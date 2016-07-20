package com.bitdubai.reference_niche_wallet.loss_protected_wallet.fragments.wallet_final_version;


import android.app.Activity;
import  android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bitdubai.android_fermat_ccp_loss_protected_wallet_bitcoin.R;
import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatWorkerCallBack;
import com.bitdubai.fermat_android_api.ui.util.FermatWorker;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedWalletExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.modules.exceptions.ActorIdentityNotSelectedException;
import com.bitdubai.fermat_api.layer.modules.exceptions.CantGetSelectedActorIdentityException;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.loss_protected_wallet.interfaces.BitcoinLossProtectedWalletSpend;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.LossProtectedWalletSettings;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions.CantGetLossProtectedBalanceException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions.CantListLossProtectedTransactionsException;
import com.bitdubai.fermat_ccp_api.all_definition.ExchangeRateProvider;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.interfaces.LossProtectedWallet;

import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.interfaces.LossProtectedWalletTransaction;
import com.bitdubai.fermat_cer_api.all_definition.interfaces.ExchangeRate;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.animation.AnimationManager;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.custom_view.CustomChartMarkerdView;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.enums.ShowMoneyType;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.popup.ChartDetailDialog;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.popup.ErrorExchangeRateConnectionDialog;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.popup.PresentationBitcoinWalletDialog;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.utils.WalletUtils;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.session.SessionConstant;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import static android.widget.Toast.makeText;

/**
 * Created by Gian Barboza on 18/04/16.
 */
public class HomeFragment extends AbstractFermatFragment<ReferenceAppFermatSession<LossProtectedWallet>,ResourceProviderManager> implements OnChartValueSelectedListener {


    LossProtectedWalletSettings lossProtectedWalletSettings;
    BlockchainNetworkType blockchainNetworkType;


    /**
     * Manager
     * */
    private LossProtectedWallet lossProtectedWalletmanager;

    private ErrorManager errorManager;

    /**
     * DATA
     * */

    private List<BitcoinLossProtectedWalletSpend> allWalletSpendingList;

    private double totalEarnedAndLostForToday = 0;

    // Fermat Managers
    private TextView txt_type_balance;
    private TextView txt_touch_to_change;
    private TextView txt_balance_amount;
    private TextView txt_balance_amount_type;
    private TextView txt_exchange_rate;
    private TextView txt_earnOrLost;
    private TextView txt_date_home;
    private ImageView earnOrLostImage;
    private LineChart chart;
    private TextView noDataInChart;
    private long balanceAvailable;
    private long realBalance;
    private View rootView;
    private FermatWorker fermatWorker;

    private LinearLayout emptyListViewsContainer;
    private AnimationManager animationManager;

    private UUID exchangeProviderId = null;

    private long exchangeRate = 0;

    private LayoutInflater inflater;

    /**
     * Constants
     * */
    private int EARN_AND_LOST_MAX_DECIMAL_FORMAT = 2;
    private int EARN_AND_LOST_MIN_DECIMAL_FORMAT = 0;
    private BalanceType balanceType = BalanceType.REAL;
    private ShowMoneyType typeAmountSelected = ShowMoneyType.BITCOIN;
    private  double actuaExchangeRate = 0;

    public static HomeFragment newInstance(){ return new HomeFragment(); }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        try {


            getActivity().getWindow().setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
            );

            lossProtectedWalletmanager = appSession.getModuleManager();
            errorManager = appSession.getErrorManager();

            if(appSession.getData(SessionConstant.TYPE_BALANCE_SELECTED) != null)
                balanceType = (BalanceType)appSession.getData(SessionConstant.TYPE_BALANCE_SELECTED);
            else
                appSession.setData(SessionConstant.TYPE_BALANCE_SELECTED, balanceType);

            if(appSession.getData(SessionConstant.TYPE_AMOUNT_SELECTED) != null)
                typeAmountSelected = ((ShowMoneyType)appSession.getData(SessionConstant.TYPE_AMOUNT_SELECTED));
            else
                appSession.setData(SessionConstant.TYPE_AMOUNT_SELECTED, typeAmountSelected);

            if(appSession.getData(SessionConstant.ACTUAL_EXCHANGE_RATE) != null)
                actuaExchangeRate = Double.parseDouble(String.valueOf(appSession.getData(SessionConstant.ACTUAL_EXCHANGE_RATE)));
            else
                appSession.setData(SessionConstant.ACTUAL_EXCHANGE_RATE, 0);

            try {
                lossProtectedWalletSettings = lossProtectedWalletmanager.loadAndGetSettings(appSession.getAppPublicKey());
            } catch (Exception e) {
                lossProtectedWalletSettings = null;
            }


            if(lossProtectedWalletSettings == null){
                lossProtectedWalletSettings = new LossProtectedWalletSettings();
                lossProtectedWalletSettings.setIsContactsHelpEnabled(true);
                lossProtectedWalletSettings.setIsPresentationHelpEnabled(true);
                lossProtectedWalletSettings.setNotificationEnabled(true);
                lossProtectedWalletSettings.setLossProtectedEnabled(true);

            }

            //default btc network
            if(lossProtectedWalletSettings.getBlockchainNetworkType()==null){
                lossProtectedWalletSettings.setBlockchainNetworkType(BlockchainNetworkType.getDefaultBlockchainNetworkType());
            }


            try {
                if(lossProtectedWalletmanager!=null) lossProtectedWalletmanager.persistSettings(appSession.getAppPublicKey(), lossProtectedWalletSettings);
            } catch (Exception e) {
                e.printStackTrace();
            }
            blockchainNetworkType = lossProtectedWalletSettings.getBlockchainNetworkType();

            final LossProtectedWalletSettings lossProtectedWalletSettingstemp = lossProtectedWalletSettings;


            Handler handlerTimer = new Handler();
            handlerTimer.postDelayed(new Runnable() {
                public void run() {
                    if (lossProtectedWalletSettingstemp.isPresentationHelpEnabled()) {
                        setUpPresentation(false);
                    }

                }
            }, 500);



        } catch (Exception ex) {

            ex.printStackTrace();
            if (errorManager != null)
                errorManager.reportUnexpectedWalletException(Wallets.CWP_WALLET_RUNTIME_WALLET_BITCOIN_WALLET_ALL_BITDUBAI,
                        UnexpectedWalletExceptionSeverity.DISABLES_THIS_FRAGMENT, ex);
        }


    }

    private void setUpPresentation(boolean checkButton) {
         try{
                PresentationBitcoinWalletDialog presentationBitcoinWalletDialog =
                        new PresentationBitcoinWalletDialog(
                                getActivity(),
                                appSession,
                                null,
                                (lossProtectedWalletmanager.getActiveIdentities().isEmpty()) ? PresentationBitcoinWalletDialog.TYPE_PRESENTATION : PresentationBitcoinWalletDialog.TYPE_PRESENTATION_WITHOUT_IDENTITIES,
                                checkButton);

                presentationBitcoinWalletDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        Object o = appSession.getData(SessionConstant.PRESENTATION_IDENTITY_CREATED);
                        if (o != null) {
                            if ((Boolean) (o)) {
                                //invalidate();
                                appSession.removeData(SessionConstant.PRESENTATION_IDENTITY_CREATED);
                            }
                        }
                        try {
                            ActiveActorIdentityInformation cryptoWalletIntraUserIdentity = lossProtectedWalletmanager.getSelectedActorIdentity();
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

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        try {
            super.onActivityCreated(savedInstanceState);
        } catch (Exception e){
            makeText(getActivity(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();
            appSession.getErrorManager().reportUnexpectedUIException(UISource.VIEW, UnexpectedUIExceptionSeverity.CRASH, e);
        }
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);
        try {
            this.inflater = inflater;
            rootView = inflater.inflate(R.layout.lossprotected_home, container, false);

            setUp(inflater);


      /*      final InputMethodManager imm;
        imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(this.getView().getWindowToken(), 0);*/

            return rootView;

        }
        catch (Exception e) {
            makeText(getActivity(), "Recovering from system error. " + e.getMessage(), Toast.LENGTH_SHORT).show();
            appSession.getErrorManager().reportUnexpectedUIException(UISource.VIEW, UnexpectedUIExceptionSeverity.CRASH, e);
        }

        return null;

    }


    private void setUp(LayoutInflater inflater){
        try {
            setUpHeader(inflater);
            setUpChart(inflater);

            try {
                //show Exchange Market Rate
                getAndShowMarketExchangeRateData(rootView);

            }catch (Exception e){
                e.printStackTrace();
            }

        }catch (Exception e){
            errorManager.reportUnexpectedWalletException(Wallets.CWP_WALLET_RUNTIME_WALLET_BITCOIN_WALLET_ALL_BITDUBAI,
                    UnexpectedWalletExceptionSeverity.DISABLES_THIS_FRAGMENT, e);
        }
    }

    private void setUpHeader(LayoutInflater inflater){

        try {

            //Select all header Element
            txt_balance_amount      = (TextView) rootView.findViewById(R.id.txt_balance_amount);
            txt_balance_amount_type = (TextView) rootView.findViewById(R.id.txt_balance_amount_type);
            txt_type_balance        = (TextView) rootView.findViewById(R.id.txt_type_balance);
            txt_touch_to_change     = (TextView) rootView.findViewById(R.id.txt_touch_to_change);
            txt_exchange_rate       = (TextView) rootView.findViewById(R.id.txt_exchange_rate);

            //Event Click For change the balance type
            txt_touch_to_change.setOnClickListener(new View.OnClickListener() {

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
            txt_balance_amount_type.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    changeAmountType();
                }
            });

            long balance = 0;


            if (balanceType.equals(BalanceType.AVAILABLE))
                balance =  lossProtectedWalletmanager.getBalance(BalanceType.AVAILABLE, appSession.getAppPublicKey(), blockchainNetworkType, "0");
            else
                balance = lossProtectedWalletmanager.getRealBalance(appSession.getAppPublicKey(), blockchainNetworkType);

            txt_balance_amount.setText(WalletUtils.formatBalanceString(balance, typeAmountSelected.getCode()));


        }catch (Exception e){
            errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));
            makeText(getActivity(), "Recovering from system error - setUpHeader - " + e.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }
    }


    private void setUpChart(LayoutInflater inflater){

        try {
            //configChart(rootView);
            //Select all header Element
            txt_earnOrLost          = (TextView) rootView.findViewById(R.id.txt_amount_lost_or_earn);
            earnOrLostImage         = (ImageView) rootView.findViewById(R.id.earnOrLostImage);
            txt_date_home           = (TextView) rootView.findViewById(R.id.txt_date_home);
            chart                   = (LineChart) rootView.findViewById(R.id.chart);
            noDataInChart           = (TextView) rootView.findViewById(R.id.noDataInChart);


            //set Actual Date

            SimpleDateFormat sdf = new SimpleDateFormat("MMM d, yyyy", Locale.US);
            Date actualDate = new Date();
            txt_date_home.setText(sdf.format(actualDate));



            chart.setDrawGridBackground(false);
            chart.setDescription("");
            chart.animateY(2000);
            chart.setTouchEnabled(true);
            chart.setDragEnabled(false);
            chart.setScaleEnabled(false);
            chart.setPinchZoom(false);
            chart.setDoubleTapToZoomEnabled(false);
            chart.setHighlightPerDragEnabled(true);
            chart.setHighlightPerTapEnabled(true);
            chart.setOnChartValueSelectedListener(this);
            chart.fitScreen();


            //Get all wallet spending from the manager
            //for especific network

           fermatWorker = new FermatWorker(getActivity()) {
                @Override
                protected Object doInBackground()  {


                    try{
                        allWalletSpendingList = lossProtectedWalletmanager.listAllWalletSpendingValue(appSession.getAppPublicKey(), blockchainNetworkType);

                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }

                    return allWalletSpendingList;
                }
            };

            fermatWorker.setCallBack(new FermatWorkerCallBack() {
                @Override
                public void onPostExecute(Object... result) {
                    if (result != null && result.length > 0) {

                        allWalletSpendingList = (List<BitcoinLossProtectedWalletSpend>) result[0];
                        LineData data = getData(allWalletSpendingList);
                        //LineData data = new LineData(labels, dataset);
                        chart.setData(data);
                        //Set Earned and Lost For To day en UI
                        if (totalEarnedAndLostForToday > 0) {

                            txt_earnOrLost.setText("USD " +
                                    WalletUtils.formatAmountStringWithDecimalEntry(
                                            totalEarnedAndLostForToday,
                                            EARN_AND_LOST_MAX_DECIMAL_FORMAT,
                                            EARN_AND_LOST_MIN_DECIMAL_FORMAT) + " earned");

                            earnOrLostImage.setBackgroundResource(R.drawable.earning_icon);

                        } else if (totalEarnedAndLostForToday == 0) {

                            txt_earnOrLost.setText("USD 0.00");
                            earnOrLostImage.setVisibility(View.INVISIBLE);

                        } else if (totalEarnedAndLostForToday < 0) {

                            txt_earnOrLost.setText("USD " + WalletUtils.formatAmountStringWithDecimalEntry(
                                    totalEarnedAndLostForToday * -1,
                                    EARN_AND_LOST_MAX_DECIMAL_FORMAT,
                                    EARN_AND_LOST_MIN_DECIMAL_FORMAT) + " lost");


                            earnOrLostImage.setBackgroundResource(R.drawable.lost_icon);

                        }

                        data.setValueTextSize(12f);
                        data.setValueTextColor(Color.WHITE);

                        CustomChartMarkerdView mv = new CustomChartMarkerdView(getActivity(),
                                R.layout.loss_custom_marker_view,
                                allWalletSpendingList,
                                appSession,
                                errorManager,
                                lossProtectedWalletmanager);
                        //chart.setMarkerView(mv);

                        YAxis yAxis = chart.getAxisLeft();
                        yAxis.setEnabled(true);
                        yAxis.setTextColor(Color.WHITE);
                        yAxis.setGridColor(Color.WHITE);
                        //yAxis.setStartAtZero(false);
                        //yAxis.setAxisMaxValue(30);
                        //yAxis.setAxisMinValue(-30);

                        YAxis yAxis1R = chart.getAxisRight();
                        yAxis1R.setEnabled(false);
                        //yAxis1R.setAxisMaxValue(30);
                        //yAxis1R.setAxisMinValue(-30);

                        XAxis xAxis = chart.getXAxis();
                        xAxis.setEnabled(false);

                        Legend legend = chart.getLegend();
                        legend.setEnabled(false);

                    }
                }

                @Override
                public void onErrorOccurred(Exception ex) {
                    //  progressBar.setVisibility(View.GONE);

                    makeText(getActivity(), "Error Get SpendingList for Chart ",
                            Toast.LENGTH_SHORT).show();
                    ErrorManager errorManager = appSession.getErrorManager();
                    if (errorManager != null)
                        errorManager.reportUnexpectedWalletException(Wallets.CWP_WALLET_RUNTIME_WALLET_BITCOIN_WALLET_ALL_BITDUBAI,
                                UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, ex);
                    else
                        Log.e("Get Spendings", ex.getMessage(), ex);

                }
            });

            fermatWorker.execute();


        }catch (Exception e) {
            e.printStackTrace();
            errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));
            makeText(getActivity(), "Oooops! recovering from system error In Graphic",
                    Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * Get the LineData for the chart based on the all wallet Spending List
     *
     * @return the ListData object
     */
    private LineData getData(List<BitcoinLossProtectedWalletSpend> ListSpendigs) {

        ArrayList<Entry> entryList = new ArrayList<>();
        ArrayList<String> xValues = new ArrayList<>();

        ArrayList<Integer> colors = new ArrayList<Integer>();

        //Date format for earned and lost for today
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        Date actualDate = new Date();

        //look for the size number of the spendings list
        int size = 0;
        if(ListSpendigs != null)
           size = ListSpendigs.size();

            //if statement for validate if the list has values
            if (size > 0) {
                //loop into the spending transaction
                for (int i = 0; i < size; i++) {

                    BitcoinLossProtectedWalletSpend listSpendig = ListSpendigs.get(i);

                    final String dateActual = sdf.format(actualDate);
                    final String dateSpend = sdf.format(listSpendig.getTimestamp());
                    //get the total earned and lost
                    // if(dateActual.equals(dateSpend)){
                    totalEarnedAndLostForToday += getEarnOrLostOfSpending(
                            listSpendig.getAmount(),
                            listSpendig.getExchangeRate(),
                            listSpendig.getTransactionId());
                    // }

                    //get the entry value for chart with getEarnOrLostOfSpending method
                    final double valueEntry = getEarnOrLostOfSpending(
                            listSpendig.getAmount(),
                            listSpendig.getExchangeRate(),
                            listSpendig.getTransactionId());

                    //Set Array Colors
                    if (valueEntry==0)
                        colors.add(Color.parseColor("#E58617"));
                    else if (valueEntry>0)
                        colors.add(Color.GREEN);
                    else if (valueEntry<0)
                        colors.add(Color.RED);

                    //Set entries values for the chart
                    entryList.add(new Entry((float)valueEntry, i));
                    xValues.add("$ "+String.valueOf(valueEntry));
                }
                chart.setVisibility(View.VISIBLE);
            }else{
                txt_earnOrLost.setText("$0.00");
                earnOrLostImage.setImageResource(R.drawable.earning_icon);
                chart.setVisibility(View.GONE);
                noDataInChart.setVisibility(View.VISIBLE);
            }

            LineDataSet dataset = new LineDataSet(entryList, "");
            dataset.setColor(Color.WHITE); //
            dataset.setDrawCubic(false);
            dataset.setDrawValues(false);
            dataset.setDrawCircles(true);
            dataset.setCircleSize(3);
            dataset.setCircleColors(colors);
            dataset.setDrawCircleHole(false);
            dataset.setValueFormatter(new LargeValueFormatter());
            dataset.setDrawHighlightIndicators(false);

            return new LineData(xValues, dataset);



    }

    private double getEarnOrLostOfSpending(long spendingAmount,double spendingExchangeRate, UUID transactionId){

        double totalInDollars = 0;

        try{

            //get the intra user login identity
            ActiveActorIdentityInformation intraUserLoginIdentity = appSession.getModuleManager().getSelectedActorIdentity();
            String intraUserPk = null;
            if (intraUserLoginIdentity != null) {
                intraUserPk = intraUserLoginIdentity.getPublicKey();
            }

            //Get the transaction of this Spending
            LossProtectedWalletTransaction transaction = lossProtectedWalletmanager.getTransaction(
                    transactionId,
                    appSession.getAppPublicKey(),
                    intraUserPk);

            DecimalFormatSymbols separator = new DecimalFormatSymbols();
            separator.setDecimalSeparator('.');
            DecimalFormat form = new DecimalFormat("##########.######",separator);

            //convert satoshis to bitcoin
            String monto = WalletUtils.formatBalanceString(Long.parseLong(form.format(spendingAmount)), ShowMoneyType.BITCOIN.getCode());


            final double amount = Double.parseDouble(monto.replace(',','.'));

            //calculate the Earned/Lost in dollars of the spending value
            totalInDollars = (amount * spendingExchangeRate)-(amount * transaction.getExchangeRate());

            //return the total


            return totalInDollars;

        } catch (CantListLossProtectedTransactionsException e) {
            errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));
            makeText(getActivity(), "Oooops! Error Exception : CantListLossProtectedTransactionsException",
                    Toast.LENGTH_SHORT).show();

        } catch (CantGetSelectedActorIdentityException e) {
            e.printStackTrace();
        } catch (ActorIdentityNotSelectedException e) {
            e.printStackTrace();
        }
        return 0;
    }


    @Override
    public void onUpdateViewOnUIThread(String code){
        try {
            if(code.equals("BlockchainDownloadComplete")) {
                //update toolbar color
                final Toolbar toolBar = getToolbar();

                //toolBar.setBackgroundColor(Color.parseColor("#12aca1"));

               // makeText(getActivity(), "Blockchain Download Complete", Toast.LENGTH_SHORT).show();
            } else {
                if(code.equals("BalanceChange"))
                {
                    //update balance amount
                    changeBalanceType(txt_type_balance, txt_balance_amount);

                    //update chart
                    setUp(inflater);

                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void changeAmountType(){

        ShowMoneyType showMoneyType = (typeAmountSelected.getCode()== ShowMoneyType.BITCOIN.getCode()) ? ShowMoneyType.BITS : ShowMoneyType.BITCOIN;
        appSession.setData(SessionConstant.TYPE_AMOUNT_SELECTED,showMoneyType);
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        super.onCreateOptionsMenu(menu, inflater);


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try {

            int id = item.getItemId();

            if(id ==1){
                changeActivity(Activities.CCP_BITCOIN_LOSS_PROTECTED_WALLET_SEND_FORM_ACTIVITY,appSession.getAppPublicKey());
                return true;
            }else {
                setUpPresentation(lossProtectedWalletSettings.isPresentationHelpEnabled());
                return true;
            }


        } catch (Exception e) {
            errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));
            makeText(getActivity(), "Oooops! recovering from system error",
                    Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
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
                appSession.setData(SessionConstant.TYPE_BALANCE_SELECTED, BalanceType.REAL);
                balanceType = BalanceType.REAL;
            } else if (balanceType.getCode().equals(BalanceType.REAL.getCode())) {
                balanceAvailable = loadBalance(BalanceType.AVAILABLE);
                txt_balance_amount.setText(WalletUtils.formatBalanceString(balanceAvailable, typeAmountSelected.getCode()));
                txt_type_balance.setText(R.string.available_balance_text);
                balanceType = BalanceType.AVAILABLE;
                appSession.setData(SessionConstant.TYPE_BALANCE_SELECTED, BalanceType.AVAILABLE);
            }
        } catch (Exception e) {
            appSession.getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.CRASH, FermatException.wrapException(e));
            Toast.makeText(getActivity().getApplicationContext(), "Recovering from system error - changeBalanceType", Toast.LENGTH_SHORT).show();
        }

    }

    private long loadBalance(BalanceType balanceType){
        long balance = 0;
        try {

            if(balanceType.equals(BalanceType.REAL))
                balance =  lossProtectedWalletmanager.getRealBalance(appSession.getAppPublicKey(), blockchainNetworkType);

            if(balanceType.equals(BalanceType.AVAILABLE))
                balance =  lossProtectedWalletmanager.getBalance(balanceType, appSession.getAppPublicKey(), blockchainNetworkType, String.valueOf(actuaExchangeRate));


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

    
    private void getAndShowMarketExchangeRateData(final View container) {

        final int MAX_DECIMAL_FOR_RATE = 2;
        final int MIN_DECIMAL_FOR_RATE = 2;

         fermatWorker = new FermatWorker(getActivity()) {
            @Override
            protected Object doInBackground()  {

                ExchangeRate rate = null;
                try{

                    //default Exchange rate Provider

                        if(lossProtectedWalletmanager.getExchangeProvider()==null) {
                            List<ExchangeRateProvider> providers = new ArrayList(lossProtectedWalletmanager.getExchangeRateProviderManagers());

                            exchangeProviderId = providers.get(0).getProviderId();
                            lossProtectedWalletmanager.setExchangeProvider(exchangeProviderId);

                        }
                        else
                        {
                            exchangeProviderId =lossProtectedWalletmanager.getExchangeProvider();
                        }


                     rate =  lossProtectedWalletmanager.getCurrencyExchange(exchangeProviderId);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }

                return rate;
            }
        };

        fermatWorker.setCallBack(new FermatWorkerCallBack() {
            @Override
            public void onPostExecute(Object... result) {
                if (result != null && result.length > 0) {

                    ExchangeRate rate = (ExchangeRate) result[0];
                    if(rate != null)
                    {
                        // progressBar.setVisibility(View.GONE);
                        txt_exchange_rate.setText("1 BTC = " + String.valueOf(
                                WalletUtils.formatAmountStringWithDecimalEntry(
                                        rate.getPurchasePrice(),
                                        MAX_DECIMAL_FOR_RATE,
                                        MIN_DECIMAL_FOR_RATE)) + " USD");

                        //get available balance to actual exchange rate
                        actuaExchangeRate = Double.parseDouble(
                                WalletUtils.formatAmountStringWithDecimalEntry(rate.getPurchasePrice(),
                                        MAX_DECIMAL_FOR_RATE,
                                        MIN_DECIMAL_FOR_RATE));

                        appSession.setData(SessionConstant.ACTUAL_EXCHANGE_RATE, actuaExchangeRate);

                        updateBalances();

                    }
                    else {
                        ErrorExchangeRateConnectionDialog dialog_error = new ErrorExchangeRateConnectionDialog(getActivity());
                        dialog_error.show();
                    }


                }
                else {
                    ErrorExchangeRateConnectionDialog dialog_error = new ErrorExchangeRateConnectionDialog(getActivity());
                    dialog_error.show();
                    //makeText(getActivity(), "Cant't Get Exhange Rate Info, check your internet connection.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onErrorOccurred(Exception ex) {
                //  progressBar.setVisibility(View.GONE);

                txt_exchange_rate.setVisibility(View.GONE);

                ErrorManager errorManager = appSession.getErrorManager();
                if (errorManager != null)
                    errorManager.reportUnexpectedWalletException(Wallets.CWP_WALLET_RUNTIME_WALLET_BITCOIN_WALLET_ALL_BITDUBAI,
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
    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
        if (e != null) {
            try {

                Log.i("VAL SELECTED",
                        "Value: " + e.getVal() + ", xIndex: " + e.getXIndex()
                                + ", DataSet index: " + dataSetIndex);

                BitcoinLossProtectedWalletSpend spent = allWalletSpendingList.get(e.getXIndex());

                ChartDetailDialog dialogSpent = new ChartDetailDialog(
                        getActivity(),
                        spent,
                        lossProtectedWalletmanager,
                        errorManager,
                        appSession);
                dialogSpent.show();
                chart.invalidate(); // refresh

            }catch (Exception el){
                el.getCause();
            }
        }
    }


    @Override
    public void onNothingSelected() {

    }

    @Override
    public void onBackPressed() {
        hideSoftKeyboard(this.getActivity());

    }

    public static void hideSoftKeyboard(Activity activity) {

       InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if(activity.getCurrentFocus() != null)
         inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }


    @Override
    public void onStop() {
        if(fermatWorker != null)
            fermatWorker.shutdownNow();
        super.onStop();
    }
}
