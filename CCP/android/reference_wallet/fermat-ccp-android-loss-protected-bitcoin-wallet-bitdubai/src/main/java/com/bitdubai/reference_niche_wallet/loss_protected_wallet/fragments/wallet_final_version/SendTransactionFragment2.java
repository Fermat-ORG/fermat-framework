package com.bitdubai.reference_niche_wallet.loss_protected_wallet.fragments.wallet_final_version;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bitdubai.android_fermat_ccp_loss_protected_wallet_bitcoin.R;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.Views.CircularProgressBar;
import com.bitdubai.fermat_android_api.ui.enums.FermatRefreshTypes;
import com.bitdubai.fermat_android_api.ui.expandableRecicler.ExpandableRecyclerAdapter;
import com.bitdubai.fermat_android_api.ui.fragments.FermatWalletExpandableListFragment;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatListItemListeners;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatWorkerCallBack;
import com.bitdubai.fermat_android_api.ui.util.FermatAnimationsUtils;
import com.bitdubai.fermat_android_api.ui.util.FermatDividerItemDecoration;
import com.bitdubai.fermat_android_api.ui.util.FermatWorker;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ReferenceWallet;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.enums.VaultType;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.IndexInfoSummary;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums.TransactionType;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantListCryptoWalletIntraUserIdentityException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.ContactNameAlreadyExistsException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.LossProtectedWalletSettings;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions.CantFindLossProtectedWalletContactException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions.CantGetCryptoLossProtectedWalletException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions.CantGetLossProtectedBalanceException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions.CantListLossProtectedTransactionsException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions.CantRequestLossProtectedAddressException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.interfaces.LossProtectedWallet;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.interfaces.LossProtectedWalletContact;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.interfaces.LossProtectedWalletIntraUserIdentity;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.interfaces.LossProtectedWalletTransaction;
import com.bitdubai.fermat_cer_api.all_definition.interfaces.ExchangeRate;
import com.bitdubai.fermat_cer_api.layer.provider.interfaces.CurrencyExchangeRateProviderManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedWalletExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.BitcoinWalletConstants;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.adapters.ReceivetransactionsExpandableAdapter;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.animation.AnimationManager;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.enums.ShowMoneyType;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.models.GrouperItem;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.popup.PresentationBitcoinWalletDialog;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.utils.WalletUtils;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.session.LossProtectedWalletSession;

import com.bitdubai.reference_niche_wallet.loss_protected_wallet.session.SessionConstant;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static android.widget.Toast.makeText;


/**
 * Fragment the show the list of open negotiations waiting for the broker and the customer un the Home activity
 *
 * @author MAtias Furszyfer
 * Modified by Natalia Cortez
 */
public class SendTransactionFragment2 extends FermatWalletExpandableListFragment<GrouperItem,LossProtectedWalletSession,ResourceProviderManager>
        implements FermatListItemListeners<LossProtectedWalletTransaction>{


    final TransactionType transactionType = TransactionType.DEBIT;
    LossProtectedWalletSession lossProtectedWalletSession;
    SettingsManager<LossProtectedWalletSettings> settingsManager;
    BlockchainNetworkType blockchainNetworkType;
    long before = 0;
    long after = 0;
    boolean pressed = false;
    CircularProgressBar circularProgressBar;
    Thread background;
    private int MAX_TRANSACTIONS = 20;
    // Fermat Managers
    private LossProtectedWallet moduleManager;
    private ErrorManager errorManager;
    // Data
    private List<GrouperItem> openNegotiationList;
    private TextView txt_type_balance;
    private TextView txt_balance_amount;
    private TextView txt_exchange_rate;
    private long balanceAvailable;
    private View rootView;
    private List<LossProtectedWalletTransaction> lstCryptoWalletTransactionsAvailable;
    //private List<CryptoWalletTransaction> lstCryptoWalletTransactionsBook;
    private int available_offset=0;
    private int book_offset=0;
    private long bookBalance;
    private LinearLayout emptyListViewsContainer;
    private AnimationManager animationManager;
    private FermatTextView txt_balance_amount_type;
    private int progress1=1;
    private  Map<Long, Long> runningDailyBalance;

    private UUID exchangeProviderId = null;

    public static SendTransactionFragment2 newInstance() {
        return new SendTransactionFragment2();
    }

    public static String GET(String url){
        InputStream inputStream = null;
        String result = "";
        try {

            // create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // make GET request to the given URL
            HttpResponse httpResponse = httpclient.execute(new HttpGet(url));

            // receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            // convert inputstream to string
            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        return result;
    }

    // convert inputstream to String
    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setHasOptionsMenu(true);

        lstCryptoWalletTransactionsAvailable = new ArrayList<>();

       // lstCryptoWalletTransactionsBook = new ArrayList<>();

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
                            }catch (OutOfMemoryError o){
                                o.printStackTrace();
                            }
                        }
                    });
                }
            }
        });

        try {
            lossProtectedWalletSession = appSession;
            moduleManager = lossProtectedWalletSession.getModuleManager().getCryptoWallet();
            errorManager = appSession.getErrorManager();


             settingsManager = lossProtectedWalletSession.getModuleManager().getSettingsManager();


            LossProtectedWalletSettings bitcoinWalletSettings = null;
            try {
                 bitcoinWalletSettings = settingsManager.loadAndGetSettings(lossProtectedWalletSession.getAppPublicKey());
            }catch (Exception e){
                bitcoinWalletSettings = null;
            }
            if(bitcoinWalletSettings == null){
                bitcoinWalletSettings = new LossProtectedWalletSettings();
                bitcoinWalletSettings.setIsContactsHelpEnabled(true);
                bitcoinWalletSettings.setIsPresentationHelpEnabled(true);

                settingsManager.persistSettings(lossProtectedWalletSession.getAppPublicKey(), bitcoinWalletSettings);
            }

            //deault btc network
            if(bitcoinWalletSettings.getBlockchainNetworkType()==null){
                bitcoinWalletSettings.setBlockchainNetworkType(BlockchainNetworkType.getDefaultBlockchainNetworkType());
            }

            //default Exchange rate Provider

            if(bitcoinWalletSettings.getExchangeProvider()==null){
                List<CurrencyExchangeRateProviderManager> providers = new ArrayList(moduleManager.getExchangeRateProviderManagers());

                exchangeProviderId = providers.get(0).getProviderId();
                bitcoinWalletSettings.setExchangeProvider(exchangeProviderId);

            }
            else
            {
                exchangeProviderId =bitcoinWalletSettings.getExchangeProvider();
            }

            settingsManager.persistSettings(lossProtectedWalletSession.getAppPublicKey(),bitcoinWalletSettings);

            blockchainNetworkType = settingsManager.loadAndGetSettings(lossProtectedWalletSession.getAppPublicKey()).getBlockchainNetworkType();

            final LossProtectedWalletSettings bitcoinWalletSettingsTemp = bitcoinWalletSettings;


            Handler handlerTimer = new Handler();
            handlerTimer.postDelayed(new Runnable(){
                public void run() {
                    if(bitcoinWalletSettingsTemp.isPresentationHelpEnabled()){
                        setUpPresentation(false);
                    }
                }}, 500);

            setRunningDailyBalance();
        } catch (Exception ex) {
            if (errorManager != null)
                errorManager.reportUnexpectedWalletException(Wallets.CWP_WALLET_RUNTIME_WALLET_BITCOIN_WALLET_ALL_BITDUBAI,
                        UnexpectedWalletExceptionSeverity.DISABLES_THIS_FRAGMENT, ex);
        }

        openNegotiationList = (ArrayList) getMoreDataAsync(FermatRefreshTypes.NEW, 0);
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
        rootView = super.onCreateView(inflater, container, savedInstanceState);
        setUp(inflater);

        getAndShowMarketExchangeRateData(rootView);
        return rootView;
    }

    private void setUp(LayoutInflater inflater){
        try {
            //setUpHeader(inflater);
            setUpDonut(inflater);
            setUpScreen();
        }catch (Exception e){
            errorManager.reportUnexpectedWalletException(Wallets.CWP_WALLET_RUNTIME_WALLET_BITCOIN_WALLET_ALL_BITDUBAI,
                    UnexpectedWalletExceptionSeverity.DISABLES_THIS_FRAGMENT, e);
        }
    }

    private void setUpScreen(){
        int[] emptyOriginalPos = new int[2];
        if(emptyListViewsContainer!=null) {
            emptyListViewsContainer.getLocationOnScreen(emptyOriginalPos);
            if (animationManager != null)
                animationManager.setEmptyOriginalPos(emptyOriginalPos);
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

        final View balance_header = inflater.inflate(R.layout.donut_header, container_header_balance, true);

        container_header_balance.setVisibility(View.VISIBLE);

        circularProgressBar = (CircularProgressBar) balance_header.findViewById(R.id.progress);

       final String runningBalance = WalletUtils.formatBalanceStringNotDecimal(moduleManager.getBalance(BalanceType.AVAILABLE, lossProtectedWalletSession.getAppPublicKey(), blockchainNetworkType), ShowMoneyType.BITCOIN.getCode());

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

            long balance = moduleManager.getBalance(BalanceType.getByCode(lossProtectedWalletSession.getBalanceTypeSelected()), lossProtectedWalletSession.getAppPublicKey(),blockchainNetworkType);
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
            if(moduleManager.getBalance(BalanceType.AVAILABLE,appSession.getAppPublicKey(),blockchainNetworkType)<500000000L) {
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
                                cryptoWalletWalletContact = moduleManager.createWalletContact(cryptoAddress, "mati_bitcoins", "", "", Actors.EXTRA_USER, appSession.getAppPublicKey(),blockchainNetworkType);
                            } catch ( ContactNameAlreadyExistsException e) {
                                try {
                                    cryptoWalletWalletContact = moduleManager.findWalletContactByName("mati_bitcoins", appSession.getAppPublicKey(), lossProtectedWalletSession.getIntraUserModuleManager().getPublicKey());
                                } catch (CantFindLossProtectedWalletContactException  e3) {

                                } catch (com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.WalletContactNotFoundException e1) {
                                    e1.printStackTrace();
                                } catch (CantListCryptoWalletIntraUserIdentityException e1) {
                                    e1.printStackTrace();
                                } catch (CantGetCryptoLossProtectedWalletException e1) {
                                    e1.printStackTrace();
                                }
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
                                    Toast.makeText(context, "Mati Bitcoins!!", Toast.LENGTH_SHORT).show();
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

        menu.add(0, BitcoinWalletConstants.IC_ACTION_SEND, 0, "send").setIcon(R.drawable.ic_actionbar_send)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        menu.add(1, BitcoinWalletConstants.IC_ACTION_HELP_PRESENTATION, 1, "help").setIcon(R.drawable.help_icon)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        //inflater.inflate(R.menu.home_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try {

            int id = item.getItemId();

            if(id == BitcoinWalletConstants.IC_ACTION_SEND){
                changeActivity(Activities.CCP_BITCOIN_WALLET_SEND_FORM_ACTIVITY,lossProtectedWalletSession.getAppPublicKey());
                return true;
            }else if(id == BitcoinWalletConstants.IC_ACTION_HELP_PRESENTATION){
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



    @Override
    protected void initViews(View layout) {
        super.initViews(layout);

        RecyclerView.ItemDecoration itemDecoration = new FermatDividerItemDecoration(getActivity(), R.drawable.cbw_divider_shape);
        recyclerView.addItemDecoration(itemDecoration);

        if (openNegotiationList.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            emptyListViewsContainer =(LinearLayout) layout.findViewById(R.id.empty);
            FermatAnimationsUtils.showEmpty(getActivity(), true, emptyListViewsContainer);
            //emptyListViewsContainer.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected boolean hasMenu() {
        return true;
    }

    @Override
    public ExpandableRecyclerAdapter getAdapter() {
        if (adapter == null) {
            adapter = new ReceivetransactionsExpandableAdapter(getActivity(), openNegotiationList,getResources());
            // setting up event listeners
            adapter.setChildItemFermatEventListeners(this);
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
        return R.layout.home_send_main;
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
    public List<GrouperItem> getMoreDataAsync(FermatRefreshTypes refreshType, int pos) {
        ArrayList<GrouperItem> data = new ArrayList<>();
        lstCryptoWalletTransactionsAvailable = new ArrayList<>();

        try {
            LossProtectedWalletIntraUserIdentity intraUserLoginIdentity = lossProtectedWalletSession.getIntraUserModuleManager();
            if(intraUserLoginIdentity!=null) {
                String intraUserPk = intraUserLoginIdentity.getPublicKey();

                BlockchainNetworkType blockchainNetworkType = BlockchainNetworkType.getByCode(settingsManager.loadAndGetSettings(lossProtectedWalletSession.getAppPublicKey()).getBlockchainNetworkType().getCode());

                List<LossProtectedWalletTransaction> list = moduleManager.listLastActorTransactionsByTransactionType(BalanceType.AVAILABLE, TransactionType.DEBIT, lossProtectedWalletSession.getAppPublicKey(), intraUserPk, blockchainNetworkType, MAX_TRANSACTIONS, available_offset);

                lstCryptoWalletTransactionsAvailable.addAll(list);

                available_offset = lstCryptoWalletTransactionsAvailable.size();

                for (LossProtectedWalletTransaction cryptoWalletTransaction : lstCryptoWalletTransactionsAvailable) {
//                    List<CryptoWalletTransaction> lst = moduleManager.listTransactionsByActorAndType(BalanceType.getByCode(referenceWalletSession.getBalanceTypeSelected()), TransactionType.DEBIT, referenceWalletSession.getAppPublicKey(), cryptoWalletTransaction.getActorToPublicKey(), intraUserPk, MAX_TRANSACTIONS, 0);
                    List<LossProtectedWalletTransaction> lst = moduleManager.listTransactionsByActorAndType(BalanceType.AVAILABLE, TransactionType.DEBIT, lossProtectedWalletSession.getAppPublicKey(), cryptoWalletTransaction.getActorToPublicKey(), intraUserPk, blockchainNetworkType, MAX_TRANSACTIONS, 0);

                    GrouperItem<LossProtectedWalletTransaction, LossProtectedWalletTransaction> grouperItem = new GrouperItem<LossProtectedWalletTransaction, LossProtectedWalletTransaction>(lst, false, cryptoWalletTransaction);
                    data.add(grouperItem);
                }

                if(!data.isEmpty()){
                    FermatAnimationsUtils.showEmpty(getActivity(),false,emptyListViewsContainer);
                }


            }

        } catch (CantListLossProtectedTransactionsException e) {
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
        return data;
    }

    @Override
    protected boolean recyclerHasFixedSize() {
        return true;
    }

    @Override
    public void onItemClickListener(LossProtectedWalletTransaction data, int position) {

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
                openNegotiationList = (ArrayList) result[0];
                if (adapter != null)
                    adapter.changeDataSet(openNegotiationList);
            }
        }
    }

    @Override
    public void onErrorOccurred(Exception ex) {
        isRefreshing = false;
        if (isAttached) {
            swipeRefreshLayout.setRefreshing(false);
            errorManager.reportUnexpectedPluginException(Plugins.CRYPTO_WALLET, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, ex);
        }
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
        setRunningDailyBalance();
        try {
            if (((LossProtectedWalletSession)appSession).getBalanceTypeSelected().equals(BalanceType.AVAILABLE.getCode())) {
                balanceAvailable = loadBalance(BalanceType.AVAILABLE);
                txt_balance_amount.setText(WalletUtils.formatBalanceString(bookBalance, lossProtectedWalletSession.getTypeAmount()));
                txt_type_balance.setText(R.string.book_balance);
                lossProtectedWalletSession.setBalanceTypeSelected(BalanceType.BOOK);
            } else if (lossProtectedWalletSession.getBalanceTypeSelected().equals(BalanceType.BOOK.getCode())) {
                bookBalance = loadBalance(BalanceType.BOOK);
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
            balance = lossProtectedWalletSession.getModuleManager().getCryptoWallet().getBalance(balanceType, lossProtectedWalletSession.getAppPublicKey(),blockchainNetworkType);
            System.out.println("THE BALANCE IS " + balance);

        } catch (CantGetLossProtectedBalanceException e) {
            e.printStackTrace();
        } catch (CantGetCryptoLossProtectedWalletException e) {
            e.printStackTrace();
        }
        return balance;
    }


    private void updateBalances(){
        bookBalance = loadBalance(BalanceType.BOOK);
        balanceAvailable = loadBalance(BalanceType.AVAILABLE);
        txt_balance_amount.setText(
                WalletUtils.formatBalanceString(
                        (lossProtectedWalletSession.getBalanceTypeSelected() == BalanceType.AVAILABLE.getCode())
                                ? balanceAvailable : bookBalance,
                        lossProtectedWalletSession.getTypeAmount())
        );
    }


    private int getBalanceAverage(){
        int cant = runningDailyBalance.size();
        long balanceSum = 0;
        int average = 0;
        try {

            for (Map.Entry<Long, Long> entry :  runningDailyBalance.entrySet())
            {
                balanceSum += Integer.valueOf(WalletUtils.formatBalanceStringNotDecimal(entry.getValue(), ShowMoneyType.BITCOIN.getCode()));
            }

            if(balanceSum > 0 )
                average = (int) ((Integer.valueOf(WalletUtils.formatBalanceStringNotDecimal(getBalanceValue(runningDailyBalance.size() - 1), ShowMoneyType.BITCOIN.getCode())) * 100) / balanceSum);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return average;
    }

    private void setRunningDailyBalance()
    {
        try {

            long currentTime = System.currentTimeMillis();
            runningDailyBalance = new HashMap<Long, Long>();

            LossProtectedWalletSettings bitcoinWalletSettings = null;
            try {
                bitcoinWalletSettings = settingsManager.loadAndGetSettings(lossProtectedWalletSession.getAppPublicKey());
            }catch (Exception e){
                bitcoinWalletSettings = null;
            }

            if(bitcoinWalletSettings == null){

                runningDailyBalance.put(currentTime,  moduleManager.getBalance(BalanceType.AVAILABLE,lossProtectedWalletSession.getAppPublicKey(),blockchainNetworkType));
                bitcoinWalletSettings.setRunningDailyBalance(runningDailyBalance);
                settingsManager.persistSettings(lossProtectedWalletSession.getAppPublicKey(),bitcoinWalletSettings);
            }
            else {

                if (bitcoinWalletSettings.getRunningDailyBalance() == null){
                    runningDailyBalance.put(currentTime,  moduleManager.getBalance(BalanceType.AVAILABLE,lossProtectedWalletSession.getAppPublicKey(),blockchainNetworkType));
                }
                else
                {
                    runningDailyBalance = bitcoinWalletSettings.getRunningDailyBalance();


                    //verify that I have this day added
                    long lastDate = getKeyDate(runningDailyBalance.size()-1);

                    long dif = currentTime - lastDate;

                    double dias = Math.floor(dif / (1000 * 60 * 60 * 24));
                    if(dias > 1)
                    {
                        //if I have 30 days I start counting again
                        if(runningDailyBalance.size() == 30)
                            runningDailyBalance = new HashMap<Long, Long>();

                        runningDailyBalance.put(currentTime, moduleManager.getBalance(BalanceType.AVAILABLE, lossProtectedWalletSession.getAppPublicKey(),blockchainNetworkType));

                    }
                    else
                    {
                        //update balance
                        this.updateDailyBalance(runningDailyBalance.size()-1,moduleManager.getBalance(BalanceType.AVAILABLE, lossProtectedWalletSession.getAppPublicKey(),blockchainNetworkType));
                    }



                }


                bitcoinWalletSettings.setRunningDailyBalance(runningDailyBalance);
                settingsManager.persistSettings(lossProtectedWalletSession.getAppPublicKey(), bitcoinWalletSettings);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    private long getKeyDate(int pos){
        int i = 0;
        long date = 0;

        try {

            for (Map.Entry<Long, Long> entry :  runningDailyBalance.entrySet())
            {
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

            for (Map.Entry<Long, Long> entry :  runningDailyBalance.entrySet())
            {
                if(i == pos)
                    date += entry.getValue();

                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    private long updateDailyBalance(int pos, long balance){
        int i = 0;
        long date = 0;

        try {

            for (Map.Entry<Long, Long> entry :  runningDailyBalance.entrySet())
            {
                if(i == pos)
                {
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

            //update balance amount

            final String runningBalance = WalletUtils.formatBalanceStringNotDecimal(moduleManager.getBalance(BalanceType.AVAILABLE, lossProtectedWalletSession.getAppPublicKey(),blockchainNetworkType),ShowMoneyType.BITCOIN.getCode());

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
                    Log.e(TAG, ex.getMessage(), ex);
            }
        });

        fermatWorker.execute();
    }
}

