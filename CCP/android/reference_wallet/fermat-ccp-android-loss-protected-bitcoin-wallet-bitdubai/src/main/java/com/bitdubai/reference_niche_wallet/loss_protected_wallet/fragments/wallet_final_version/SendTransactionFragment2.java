package com.bitdubai.reference_niche_wallet.loss_protected_wallet.fragments.wallet_final_version;


import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bitdubai.android_fermat_ccp_loss_protected_wallet_bitcoin.R;
import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.Views.CircularProgressBar;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatWorkerCallBack;
import com.bitdubai.fermat_android_api.ui.util.FermatWorker;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.ReferenceWallet;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.enums.VaultType;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantListCryptoWalletIntraUserIdentityException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.LossProtectedWalletSettings;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions.CantGetCryptoLossProtectedWalletException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions.CantGetLossProtectedBalanceException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions.CantRequestLossProtectedAddressException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.interfaces.LossProtectedWallet;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.interfaces.LossProtectedWalletContact;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.interfaces.LossProtectedWalletIntraUserIdentity;
import com.bitdubai.fermat_cer_api.all_definition.interfaces.ExchangeRate;
import com.bitdubai.fermat_cer_api.layer.provider.interfaces.CurrencyExchangeRateProviderManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedWalletExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.LossProtectedWalletConstants;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.animation.AnimationManager;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.enums.ShowMoneyType;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.popup.PresentationBitcoinWalletDialog;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.utils.WalletUtils;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.session.LossProtectedWalletSession;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.session.SessionConstant;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static android.widget.Toast.makeText;


/**
 * Fragment the show the list of open negotiations waiting for the broker and the customer un the Home activity
 *
 * @author MAtias Furszyfer
 * Modified by Natalia Cortez
 */
public class SendTransactionFragment2 extends AbstractFermatFragment<LossProtectedWalletSession,ResourceProviderManager> {



    LossProtectedWalletSession lossProtectedWalletSession;
    SettingsManager<LossProtectedWalletSettings> settingsManager;
    BlockchainNetworkType blockchainNetworkType;
    long before = 0;
    long after = 0;
    boolean pressed = false;
    CircularProgressBar circularProgressBar;
    Thread background;

    // Fermat Managers
    private LossProtectedWallet moduleManager;
    private ErrorManager errorManager;

    private TextView txt_type_balance;
    private TextView txt_balance_amount;
    private TextView txt_exchange_rate;
    private long balanceAvailable;
    private View rootView;


    private long realBalance;
    private LinearLayout emptyListViewsContainer;
    private AnimationManager animationManager;
    private FermatTextView txt_balance_amount_type;
    private int progress1=1;


    private UUID exchangeProviderId = null;

    private long exchangeRate = 0;

    public static SendTransactionFragment2 newInstance() {
        return new SendTransactionFragment2();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setHasOptionsMenu(true);


        try {
            lossProtectedWalletSession = appSession;
            moduleManager = lossProtectedWalletSession.getModuleManager().getCryptoWallet();
            errorManager = appSession.getErrorManager();


             settingsManager = lossProtectedWalletSession.getModuleManager().getSettingsManager();


            LossProtectedWalletSettings bitcoinWalletSettings;
            try {
                 bitcoinWalletSettings = settingsManager.loadAndGetSettings(lossProtectedWalletSession.getAppPublicKey());
            }catch (Exception e){
                bitcoinWalletSettings = null;
            }
            if(bitcoinWalletSettings == null){
                bitcoinWalletSettings = new LossProtectedWalletSettings();
                bitcoinWalletSettings.setIsContactsHelpEnabled(true);
                bitcoinWalletSettings.setIsPresentationHelpEnabled(true);
                bitcoinWalletSettings.setNotificationEnabled(true);

                settingsManager.persistSettings(lossProtectedWalletSession.getAppPublicKey(), bitcoinWalletSettings);
            }

            //deault btc network
            if(bitcoinWalletSettings.getBlockchainNetworkType()==null){
                bitcoinWalletSettings.setBlockchainNetworkType(BlockchainNetworkType.getDefaultBlockchainNetworkType());
            }


            settingsManager.persistSettings(lossProtectedWalletSession.getAppPublicKey(),bitcoinWalletSettings);

            blockchainNetworkType = settingsManager.loadAndGetSettings(lossProtectedWalletSession.getAppPublicKey()).getBlockchainNetworkType();

            final LossProtectedWalletSettings bitcoinWalletSettingsTemp = bitcoinWalletSettings;


            //default Exchange rate Provider
            try {
                    if(moduleManager.getExchangeProvider()==null) {
                        List<CurrencyExchangeRateProviderManager> providers = new ArrayList(moduleManager.getExchangeRateProviderManagers());

                        exchangeProviderId = providers.get(0).getProviderId();
                        moduleManager.setExchangeProvider(exchangeProviderId);

                    }
                    else
                   {
                        exchangeProviderId =moduleManager.getExchangeProvider();
                   }
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            Handler handlerTimer = new Handler();
            handlerTimer.postDelayed(new Runnable(){
                public void run() {
                    if(bitcoinWalletSettingsTemp.isPresentationHelpEnabled()){
                        setUpPresentation(false);
                    }
                }}, 500);

        } catch (Exception ex) {
            if (errorManager != null)
                errorManager.reportUnexpectedWalletException(Wallets.CWP_WALLET_RUNTIME_WALLET_BITCOIN_WALLET_ALL_BITDUBAI,
                        UnexpectedWalletExceptionSeverity.DISABLES_THIS_FRAGMENT, ex);
        }


    }

    private void setUpPresentation(boolean checkButton) {
        PresentationBitcoinWalletDialog presentationBitcoinWalletDialog =
                new PresentationBitcoinWalletDialog(
                        getActivity(),
                        lossProtectedWalletSession,
                        null,
                        (moduleManager.getActiveIdentities().isEmpty()) ? PresentationBitcoinWalletDialog.TYPE_PRESENTATION : PresentationBitcoinWalletDialog.TYPE_PRESENTATION_WITHOUT_IDENTITIES,
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
                    LossProtectedWalletIntraUserIdentity cryptoWalletIntraUserIdentity = lossProtectedWalletSession.getIntraUserModuleManager();
                    if (cryptoWalletIntraUserIdentity == null) {
                        getActivity().onBackPressed();
                    } else {
                        invalidate();
                    }
                } catch (CantListCryptoWalletIntraUserIdentityException e) {
                    e.printStackTrace();
                } catch (CantGetCryptoLossProtectedWalletException e) {
                    e.printStackTrace();
                }

            }
        });
        presentationBitcoinWalletDialog.show();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        try {
            super.onActivityCreated(savedInstanceState);
            animationManager = new AnimationManager(rootView,emptyListViewsContainer);
            getPaintActivtyFeactures().addCollapseAnimation(animationManager);
        } catch (Exception e){
            makeText(getActivity(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();
            lossProtectedWalletSession.getErrorManager().reportUnexpectedUIException(UISource.VIEW, UnexpectedUIExceptionSeverity.CRASH, e);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);
        try {
            rootView = inflater.inflate(R.layout.home_send_main, container, false);
            setUp(inflater);

            getAndShowMarketExchangeRateData(rootView);
            return rootView;
        }
        catch (Exception e) {
            makeText(getActivity(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();
            appSession.getErrorManager().reportUnexpectedUIException(UISource.VIEW, UnexpectedUIExceptionSeverity.CRASH, e);
        }

        return null;

    }

    private void setUp(LayoutInflater inflater){
        try {
            //setUpHeader(inflater);
            setUpDonut(inflater);

        }catch (Exception e){
            errorManager.reportUnexpectedWalletException(Wallets.CWP_WALLET_RUNTIME_WALLET_BITCOIN_WALLET_ALL_BITDUBAI,
                    UnexpectedWalletExceptionSeverity.DISABLES_THIS_FRAGMENT, e);
        }
    }



    private void setUpDonut(LayoutInflater inflater)  {
        try {
        final RelativeLayout container_header_balance = getToolbarHeader();
        try {
            container_header_balance.removeAllViews();
        }catch (Exception e){

        }


        container_header_balance.setBackgroundColor(Color.parseColor("#06356f"));
        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = null;
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inScaled = true;
                options.inSampleSize = 3;
                try {
                    bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.back_header,options);
//                    bitmap = Bitmap.createScaledBitmap(bitmap,300,400,true);
                    final Bitmap finalBitmap = bitmap;
                    if(finalBitmap!=null) {
                        Runnable runnableHandler = new Runnable() {
                            @Override
                            public void run() {
                                container_header_balance.setBackground(new BitmapDrawable(getResources(), finalBitmap));
                            }
                        };
                        handler.post(runnableHandler);
                    }
                }catch (OutOfMemoryError e){
                    e.printStackTrace();
                    System.gc();
                }

            }
        };
        Thread thread = new Thread(runnable);
        thread.start();

        final View balance_header = inflater.inflate(R.layout.loss_donut_header, container_header_balance, true);

        container_header_balance.setVisibility(View.VISIBLE);

        circularProgressBar = (CircularProgressBar) balance_header.findViewById(R.id.progress);

       final String runningBalance = WalletUtils.formatBalanceStringNotDecimal(moduleManager.getRealBalance(lossProtectedWalletSession.getAppPublicKey(), blockchainNetworkType), ShowMoneyType.BITCOIN.getCode());

        circularProgressBar.setProgressValue(Integer.valueOf(runningBalance));
        circularProgressBar.setProgressValue2(getBalanceAverage());
        circularProgressBar.setBackgroundProgressColor(Color.parseColor("#022346"));
        circularProgressBar.setProgressColor(Color.parseColor("#05ddd2"));
        circularProgressBar.setProgressColor2(Color.parseColor("#05537c"));


        txt_type_balance = (TextView) balance_header.findViewById(R.id.txt_type_balance);

       txt_exchange_rate =  (TextView) balance_header.findViewById(R.id.txt_exchange_rate);

        // handler for the background updating
        final Handler progressHandler = new Handler() {
            public void handleMessage(Message msg) {
                progress1++;
                try {
                    circularProgressBar.setProgressValue(progress1);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };

        TextView txt_amount_type = (TextView) balance_header.findViewById(R.id.txt_balance_amount_type);
        txt_type_balance.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    pressed = true;
                    before = System.currentTimeMillis();

//                    progress1 += 10;
//                    circularProgressBar.setProgressValue(progress1);
//                    circularProgressBar.invalidate();
                    //TODO fijatse que no se lancen mas de un hilo
                    if (pressed){
                        background = new Thread(new Runnable() {
                            public void run() {
                                try {
                                    // enter the code to be run while displaying the progressbar.
                                    //
                                    // This example is just going to increment the progress bar:
                                    // So keep running until the progress value reaches maximum value
                                    while (circularProgressBar.getprogress1() <= 300) {
                                        // wait 500ms between each update
                                        Thread.sleep(300);
                                       // System.out.println(circularProgressBar.getprogress1());
                                        // active the update handler
                                        progressHandler.sendMessage(progressHandler.obtainMessage());
                                    }
                                    pressed = false;
                                } catch (InterruptedException e) {
                                    // if something fails do something smart
                                }
                            }
                        });
                        background.start();
                    }


                    return true;
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    pressed = false;
                    background.interrupt();
                    after = System.currentTimeMillis();
                    if (after - before < 2000) {
                        changeBalanceType(txt_type_balance, txt_balance_amount);
                        //System.out.println(System.currentTimeMillis());

                        circularProgressBar.setProgressValue(Integer.valueOf(runningBalance));
                        circularProgressBar.setProgressValue2(getBalanceAverage());
                        return true;
                    }else {
                        //String receivedAddress = GET("http://52.27.68.19:15400/mati/address/");
                        String receivedAddress = "";

                            GET("",getActivity());
                        progress1 = 1;
                        circularProgressBar.setProgressValue(progress1);
                        return true;

                    }
                }
                    return false;
            }
        });



        txt_balance_amount = (TextView) balance_header.findViewById(R.id.txt_balance_amount);

        txt_balance_amount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                changeAmountType();
            }
        });
        txt_amount_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                changeAmountType();
            }
        });

        txt_balance_amount = (TextView) balance_header.findViewById(R.id.txt_balance_amount);
            long balance = 0;
            if(BalanceType.getByCode(lossProtectedWalletSession.getBalanceTypeSelected()).equals(BalanceType.AVAILABLE))
             balance = moduleManager.getBalance(BalanceType.AVAILABLE,lossProtectedWalletSession.getAppPublicKey(),blockchainNetworkType,"0");
            else
                balance = moduleManager.getRealBalance(lossProtectedWalletSession.getAppPublicKey(), blockchainNetworkType);

            txt_balance_amount.setText(WalletUtils.formatBalanceString(balance, lossProtectedWalletSession.getTypeAmount()));

            txt_balance_amount_type = (FermatTextView) balance_header.findViewById(R.id.txt_balance_amount_type);
        }
        catch (Exception e){

            errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.CRASH, FermatException.wrapException(e));

        }
    }

    private String getWalletAddress(String actorPublicKey) {
        String walletAddres="";
        try {
            //TODO parameters deliveredByActorId deliveredByActorType harcoded..
            CryptoAddress cryptoAddress = moduleManager.requestAddressToKnownUser(
                    lossProtectedWalletSession.getIntraUserModuleManager().getPublicKey(),
                    Actors.INTRA_USER,
                    actorPublicKey,
                    Actors.EXTRA_USER,
                    Platforms.CRYPTO_CURRENCY_PLATFORM,
                    VaultType.CRYPTO_CURRENCY_VAULT,
                    "BITV",
                    appSession.getAppPublicKey(),
                    ReferenceWallet.BASIC_WALLET_LOSS_PROTECTED_WALLET,
                    blockchainNetworkType
            );
            walletAddres = cryptoAddress.getAddress();
        } catch (CantRequestLossProtectedAddressException e) {
            errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.CRASH, FermatException.wrapException(e));
            Toast.makeText(getActivity().getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();

        } catch (CantGetCryptoLossProtectedWalletException e) {
            e.printStackTrace();
        } catch (CantListCryptoWalletIntraUserIdentityException e) {
            e.printStackTrace();
        }
        return walletAddres;
    }

    public void GET(String url, final Context context){
        final Handler mHandler = new Handler();
        try {
            if(moduleManager.getRealBalance(appSession.getAppPublicKey(), blockchainNetworkType)<500000000L) {
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String receivedAddress = "";
                        final HttpClient Client = new DefaultHttpClient();
                        try {
                            String SetServerString = "";

                            // Create Request to server and get response

                            HttpGet httpget = new HttpGet("http://52.27.68.19:15400/mati/address/");
                            ResponseHandler<String> responseHandler = new BasicResponseHandler();
                            SetServerString = Client.execute(httpget, responseHandler);
                            // Show response on activity

                            receivedAddress = SetServerString;
                        } catch (ClientProtocolException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        final String finalReceivedAddress = receivedAddress;

                        String response = "";
                        try {


                            String SetServerString = "";
                            CryptoAddress cryptoAddress = new CryptoAddress(finalReceivedAddress, CryptoCurrency.BITCOIN);
                            LossProtectedWalletContact cryptoWalletWalletContact = null;
                            try {
                                cryptoWalletWalletContact = moduleManager.createWalletContact(cryptoAddress, "regtest_bitcoins", "", "", Actors.EXTRA_USER, appSession.getAppPublicKey(), blockchainNetworkType);

                            } catch (Exception e) {

                            }

                            assert cryptoWalletWalletContact != null;
                            String myCryptoAddress = getWalletAddress(cryptoWalletWalletContact.getActorPublicKey());
                            HttpGet httpget = new HttpGet("http://52.27.68.19:15400/mati/hello/?address=" + myCryptoAddress);
                            ResponseHandler<String> responseHandler = new BasicResponseHandler();
                            SetServerString = Client.execute(httpget, responseHandler);

                            response = SetServerString;
                        } catch (IOException e) {

                        }


                        final String finalResponse = response;
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {

                                if (!finalResponse.equals("transaccion fallida")) {
                                    Toast.makeText(context, "Regtest bitcoin arrived", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
                    }
                });
                thread.start();
            }
        } catch (CantGetLossProtectedBalanceException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        super.onCreateOptionsMenu(menu, inflater);

        menu.add(0, LossProtectedWalletConstants.IC_ACTION_SEND, 0, "send").setIcon(R.drawable.ic_actionbar_send)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        menu.add(1, LossProtectedWalletConstants.IC_ACTION_HELP_PRESENTATION, 1, "help").setIcon(R.drawable.loos_help_icon)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        //inflater.inflate(R.menu.home_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try {

            int id = item.getItemId();

            if(id == LossProtectedWalletConstants.IC_ACTION_SEND){
                changeActivity(Activities.CCP_BITCOIN_LOSS_PROTECTED_WALLET_SEND_FORM_ACTIVITY,lossProtectedWalletSession.getAppPublicKey());
                return true;
            }else if(id == LossProtectedWalletConstants.IC_ACTION_HELP_PRESENTATION){
                setUpPresentation(settingsManager.loadAndGetSettings(lossProtectedWalletSession.getAppPublicKey()).isPresentationHelpEnabled());
                return true;
            }


        } catch (Exception e) {
            errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));
            makeText(getActivity(), "Oooops! recovering from system error",
                    Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }



    private void changeAmountType(){

        ShowMoneyType showMoneyType = (lossProtectedWalletSession.getTypeAmount()== ShowMoneyType.BITCOIN.getCode()) ? ShowMoneyType.BITS : ShowMoneyType.BITCOIN;
        lossProtectedWalletSession.setTypeAmount(showMoneyType);
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

        txt_balance_amount_type.setText(moneyTpe);
        updateBalances();
    }



    /**
     * Method to change the balance type
     */
    private void changeBalanceType(TextView txt_type_balance,TextView txt_balance_amount) {
        updateBalances();
         try {
            if (appSession.getBalanceTypeSelected().equals(BalanceType.AVAILABLE.getCode())) {
                realBalance = loadBalance(BalanceType.REAL);
                txt_balance_amount.setText(WalletUtils.formatBalanceString(realBalance, lossProtectedWalletSession.getTypeAmount()));
                txt_type_balance.setText(R.string.real_balance);
                lossProtectedWalletSession.setBalanceTypeSelected(BalanceType.REAL);
            } else if (lossProtectedWalletSession.getBalanceTypeSelected().equals(BalanceType.REAL.getCode())) {
                balanceAvailable = loadBalance(BalanceType.AVAILABLE);
               txt_balance_amount.setText(WalletUtils.formatBalanceString(balanceAvailable, lossProtectedWalletSession.getTypeAmount()));
                txt_type_balance.setText(R.string.available_balance);
                lossProtectedWalletSession.setBalanceTypeSelected(BalanceType.AVAILABLE);
            }
        } catch (Exception e) {
            lossProtectedWalletSession.getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.CRASH, FermatException.wrapException(e));
            Toast.makeText(getActivity().getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();
        }

    }

    private long loadBalance(BalanceType balanceType){
        long balance = 0;
        try {

            if(balanceType.equals(BalanceType.REAL))
             balance =  lossProtectedWalletSession.getModuleManager().getCryptoWallet().getRealBalance(lossProtectedWalletSession.getAppPublicKey(), blockchainNetworkType);

            if(balanceType.equals(BalanceType.AVAILABLE))
                balance =  lossProtectedWalletSession.getModuleManager().getCryptoWallet().getBalance(balanceType, lossProtectedWalletSession.getAppPublicKey(),blockchainNetworkType,String.valueOf(exchangeRate));


        } catch (CantGetLossProtectedBalanceException e) {
            e.printStackTrace();
        } catch (CantGetCryptoLossProtectedWalletException e) {
            e.printStackTrace();
        }
        return balance;
    }


    private void updateBalances(){
        realBalance = loadBalance(BalanceType.REAL);
        balanceAvailable = loadBalance(BalanceType.AVAILABLE);
        txt_balance_amount.setText(
                WalletUtils.formatBalanceString(
                        (lossProtectedWalletSession.getBalanceTypeSelected() == BalanceType.AVAILABLE.getCode())
                                ? balanceAvailable : realBalance,
                        lossProtectedWalletSession.getTypeAmount())
        );
    }


    private int getBalanceAverage(){

        return 20;
    }



    @Override
    public void onUpdateViewOnUIThread(String code){
        try {

            //update balance amount

            final String runningBalance = WalletUtils.formatBalanceStringNotDecimal(moduleManager.getRealBalance(lossProtectedWalletSession.getAppPublicKey(),blockchainNetworkType),ShowMoneyType.BITCOIN.getCode());

             changeBalanceType(txt_type_balance, txt_balance_amount);
            //System.out.println(System.currentTimeMillis());

            circularProgressBar.setProgressValue(Integer.valueOf(runningBalance));
            circularProgressBar.setProgressValue2(getBalanceAverage());
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void getAndShowMarketExchangeRateData(final View container) {

        FermatWorker fermatWorker = new FermatWorker(getActivity()) {
            @Override
            protected Object doInBackground() throws Exception {

                ExchangeRate rate =  moduleManager.getCurrencyExchange(exchangeProviderId);
                return rate;
            }
        };

        fermatWorker.setCallBack(new FermatWorkerCallBack() {
            @Override
            public void onPostExecute(Object... result) {
                if (result != null && result.length > 0) {

                    ExchangeRate rate = (ExchangeRate)result[0];
                    // progressBar.setVisibility(View.GONE);
                    txt_exchange_rate.setText("1 BTC - " + String.valueOf(rate.getPurchasePrice()) +" USD" );

                    //get available balance to actual exchange rate

                    lossProtectedWalletSession.setActualExchangeRate(rate.getPurchasePrice());
                    updateBalances();

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
            }
        });

        fermatWorker.execute();
    }
}

