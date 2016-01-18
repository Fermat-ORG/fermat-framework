package com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments.wallet_final_version;

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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bitdubai.android_fermat_ccp_wallet_bitcoin.R;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.Views.CircularProgressBar;
import com.bitdubai.fermat_android_api.ui.enums.FermatRefreshTypes;
import com.bitdubai.fermat_android_api.ui.expandableRecicler.ExpandableRecyclerAdapter;
import com.bitdubai.fermat_android_api.ui.fragments.FermatWalletExpandableListFragment;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatListItemListeners;
import com.bitdubai.fermat_android_api.ui.util.FermatAnimationsUtils;
import com.bitdubai.fermat_android_api.ui.util.FermatDividerItemDecoration;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
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
import com.bitdubai.fermat_ccp_api.layer.actor.Actor;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums.TransactionType;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.BitcoinWalletSettings;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantCreateWalletContactException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantFindWalletContactException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantGetBalanceException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantGetCryptoWalletException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantListCryptoWalletIntraUserIdentityException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantListTransactionsException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantRequestCryptoAddressException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.ContactNameAlreadyExistsException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.WalletContactNotFoundException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWallet;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWalletIntraUserIdentity;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWalletTransaction;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWalletWalletContact;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedWalletExceptionSeverity;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.BitcoinWalletConstants;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.adapters.ReceivetransactionsExpandableAdapter;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.animation.AnimationManager;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.enums.ShowMoneyType;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.models.GrouperItem;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.popup.PresentationBitcoinWalletDialog;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.utils.WalletUtils;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.session.ReferenceWalletSession;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.session.SessionConstant;

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
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;


/**
 * Fragment the show the list of open negotiations waiting for the broker and the customer un the Home activity
 *
 * @author Nelson Ramirez
 * @version 1.0
 * @since 20/10/2015
 */
public class SendTransactionFragment2 extends FermatWalletExpandableListFragment<GrouperItem>
        implements FermatListItemListeners<CryptoWalletTransaction>{


    private int MAX_TRANSACTIONS = 20;

    final TransactionType transactionType = TransactionType.DEBIT;

    // Fermat Managers
    private CryptoWallet moduleManager;
    private ErrorManager errorManager;

    // Data
    private List<GrouperItem> openNegotiationList;
    private TextView txt_type_balance;
    private TextView txt_balance_amount;
    private long balanceAvailable;
    private View rootView;
    private List<CryptoWalletTransaction> lstCryptoWalletTransactionsAvailable;
    private List<CryptoWalletTransaction> lstCryptoWalletTransactionsBook;
    private int available_offset=0;
    private int book_offset=0;

    ReferenceWalletSession referenceWalletSession;
    private long bookBalance;
    private LinearLayout emptyListViewsContainer;
    private AnimationManager animationManager;
    private FermatTextView txt_balance_amount_type;
    SettingsManager<BitcoinWalletSettings> settingsManager;
    private int progress1=1;

    public static SendTransactionFragment2 newInstance() {
        return new SendTransactionFragment2();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

        lstCryptoWalletTransactionsAvailable = new ArrayList<>();

        lstCryptoWalletTransactionsBook = new ArrayList<>();



        try {
            referenceWalletSession = (ReferenceWalletSession) appSession;
            moduleManager = referenceWalletSession.getModuleManager().getCryptoWallet();
            errorManager = appSession.getErrorManager();

//            if(lst==null){
//                startWizard(WizardTypes.CCP_WALLET_BITCOIN_START_WIZARD.getKey(),appSession, walletSettings, walletResourcesProviderManager, null);
//            }
             settingsManager = referenceWalletSession.getModuleManager().getSettingsManager();
            BitcoinWalletSettings bitcoinWalletSettings = null;
            try {
                 bitcoinWalletSettings = settingsManager.loadAndGetSettings(referenceWalletSession.getAppPublicKey());
            }catch (Exception e){
                bitcoinWalletSettings = null;
            }

            if(bitcoinWalletSettings == null){
                bitcoinWalletSettings = new BitcoinWalletSettings();
                bitcoinWalletSettings.setIsContactsHelpEnabled(true);
                bitcoinWalletSettings.setIsPresentationHelpEnabled(true);
                settingsManager.persistSettings(referenceWalletSession.getAppPublicKey(),bitcoinWalletSettings);
            }

            final BitcoinWalletSettings bitcoinWalletSettingsTemp = bitcoinWalletSettings;

            Handler handlerTimer = new Handler();
            handlerTimer.postDelayed(new Runnable(){
                public void run() {
                    if(bitcoinWalletSettingsTemp.isPresentationHelpEnabled()){
                        setUpPresentation(false);
                    }
//                    Object o = referenceWalletSession.getData(SessionConstant.PRESENTATION_SCREEN_ENABLED);
//                    if(o!=null) {
//                        if (!(Boolean) o)
//                            setUpPresentation();
//                    }else{
//
//                    }
                }}, 500);
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
                        referenceWalletSession,
                        null,
                        (moduleManager.getActiveIdentities().isEmpty()) ? PresentationBitcoinWalletDialog.TYPE_PRESENTATION : PresentationBitcoinWalletDialog.TYPE_PRESENTATION_WITHOUT_IDENTITIES,
                        checkButton);
        presentationBitcoinWalletDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                Object o = referenceWalletSession.getData(SessionConstant.PRESENTATION_IDENTITY_CREATED);
                if(o!=null){
                    if((Boolean)(o)){
                        //invalidate();
                        referenceWalletSession.removeData(SessionConstant.PRESENTATION_IDENTITY_CREATED);
                    }
                }
                try {
                    CryptoWalletIntraUserIdentity cryptoWalletIntraUserIdentity = referenceWalletSession.getIntraUserModuleManager();
                    if(cryptoWalletIntraUserIdentity==null){
                        getActivity().onBackPressed();
                    }else{
                        invalidate();
                    }
                } catch (CantListCryptoWalletIntraUserIdentityException e) {
                    e.printStackTrace();
                } catch (CantGetCryptoWalletException e) {
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
            referenceWalletSession.getErrorManager().reportUnexpectedUIException(UISource.VIEW, UnexpectedUIExceptionSeverity.CRASH, e);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = super.onCreateView(inflater, container, savedInstanceState);
        setUp(inflater);


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


    long before = 0;
    long after = 0;
    boolean pressed = false;
    CircularProgressBar circularProgressBar;
    Thread background;

    private void setUpDonut(LayoutInflater inflater){
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

//        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams()
//        balance_header.setLayoutParams();

        container_header_balance.setVisibility(View.VISIBLE);

        circularProgressBar = (CircularProgressBar) balance_header.findViewById(R.id.progress);

        circularProgressBar.setProgressValue(progress1);
        circularProgressBar.setProgressValue2(3);
        circularProgressBar.setBackgroundProgressColor(Color.parseColor("#022346"));
        circularProgressBar.setProgressColor(Color.parseColor("#05ddd2"));
        circularProgressBar.setProgressColor2(Color.parseColor("#05537c"));


        txt_type_balance = (TextView) balance_header.findViewById(R.id.txt_type_balance);
        //txt_type_balance.setTypeface(tf);

        //((TextView) balance_header.findViewById(R.id.txt_touch_to_change)).setTypeface(tf);
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
                                } catch (java.lang.InterruptedException e) {
                                    // if something fails do something smart
                                }
                            }
                        });
                        background.start();
                    }


//                    System.out.println(System.currentTimeMillis());
                    return true;
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    pressed = false;
                    background.interrupt();
                    after = System.currentTimeMillis();
                    if (after - before < 2000) {
                        changeBalanceType(txt_type_balance, txt_balance_amount);
                        //System.out.println(System.currentTimeMillis());
                        progress1 = 1;
                        circularProgressBar.setProgressValue(progress1);
                        return true;
                    }else {
                        //String receivedAddress = GET("http://52.27.68.19:15400/mati/address/");
                        String receivedAddress = "";

                       // try{


                            GET("",getActivity());

//                        CryptoAddress cryptoAddress = new CryptoAddress(receivedAddress, CryptoCurrency.BITCOIN);
//                            CryptoWalletWalletContact cryptoWalletWalletContact = moduleManager.createWalletContact(cryptoAddress, "mati_bitcoins", "", "", Actors.EXTRA_USER, appSession.getAppPublicKey());
//
//                            String myCryptoAddress = getWalletAddress(cryptoWalletWalletContact.getActorPublicKey());
//                            String url = "http://52.27.68.19:15400/mati/hello/?address="+myCryptoAddress;
//                            String response = GET(url);
//                            if(!response.equals("transaccion fallida")){
//                                Toast.makeText(getActivity(),"Mati Bitcoins!!",Toast.LENGTH_SHORT).show();
//                            }
//                        } catch (CantCreateWalletContactException e) {
//                            e.printStackTrace();
//                        } catch (ContactNameAlreadyExistsException e) {
//                            e.printStackTrace();
//                        } catch (Exception e){
//                            e.printStackTrace();
//                        }
                        progress1 = 1;
                        circularProgressBar.setProgressValue(progress1);
                        return true;

                    }
                }
                    return false;
            }
        });

//        txt_type_balance.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //Toast.makeText(getActivity(),"balance cambiado",Toast.LENGTH_SHORT).show();
//                //txt_type_balance.setText(referenceWalletSession.getBalanceTypeSelected());
//                changeBalanceType(txt_type_balance, txt_balance_amount);
//            }
//        });


        txt_balance_amount = (TextView) balance_header.findViewById(R.id.txt_balance_amount);

        txt_balance_amount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getActivity(),"balance cambiado",Toast.LENGTH_SHORT).show();
                //txt_type_balance.setText(referenceWalletSession.getBalanceTypeSelected());
                changeAmountType();
            }
        });
        txt_amount_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getActivity(),"balance cambiado",Toast.LENGTH_SHORT).show();
                //txt_type_balance.setText(referenceWalletSession.getBalanceTypeSelected());
                changeAmountType();
            }
        });

        txt_balance_amount = (TextView) balance_header.findViewById(R.id.txt_balance_amount);
        //txt_balance_amount.setTypeface(tf);

        try {
            long balance = moduleManager.getBalance(BalanceType.getByCode(referenceWalletSession.getBalanceTypeSelected()), referenceWalletSession.getAppPublicKey());
            txt_balance_amount.setText(WalletUtils.formatBalanceString(balance, referenceWalletSession.getTypeAmount()));
        } catch (CantGetBalanceException e) {
            e.printStackTrace();
        }

        txt_balance_amount_type = (FermatTextView) balance_header.findViewById(R.id.txt_balance_amount_type);

    }
    private String getWalletAddress(String actorPublicKey) {
        String walletAddres="";
        try {
            //TODO parameters deliveredByActorId deliveredByActorType harcoded..
            CryptoAddress cryptoAddress = moduleManager.requestAddressToKnownUser(
                    referenceWalletSession.getIntraUserModuleManager().getPublicKey(),
                    Actors.INTRA_USER,
                    actorPublicKey,
                    Actors.EXTRA_USER,
                    Platforms.CRYPTO_CURRENCY_PLATFORM,
                    VaultType.CRYPTO_CURRENCY_VAULT,
                    "BITV",
                    appSession.getAppPublicKey(),
                    ReferenceWallet.BASIC_WALLET_BITCOIN_WALLET
            );
            walletAddres = cryptoAddress.getAddress();
        } catch (CantRequestCryptoAddressException e) {
            errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.CRASH, FermatException.wrapException(e));
            Toast.makeText(getActivity().getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();

        } catch (CantGetCryptoWalletException e) {
            e.printStackTrace();
        } catch (CantListCryptoWalletIntraUserIdentityException e) {
            e.printStackTrace();
        }
        return walletAddres;
    }

    public void GET(String url, final Context context){
        final Handler mHandler = new Handler();
        try {
            if(moduleManager.getBalance(BalanceType.AVAILABLE,appSession.getAppPublicKey())<500000000L) {
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
                            CryptoWalletWalletContact cryptoWalletWalletContact = null;
                            try {
                                cryptoWalletWalletContact = moduleManager.createWalletContact(cryptoAddress, "mati_bitcoins", "", "", Actors.EXTRA_USER, appSession.getAppPublicKey());
                            } catch (CantCreateWalletContactException e) {
                                try {
                                    cryptoWalletWalletContact = moduleManager.findWalletContactByName("mati_bitcoins", appSession.getAppPublicKey(), referenceWalletSession.getIntraUserModuleManager().getPublicKey());
                                } catch (CantFindWalletContactException e3) {

                                } catch (WalletContactNotFoundException e1) {

                                } catch (CantListCryptoWalletIntraUserIdentityException e1) {

                                } catch (CantGetCryptoWalletException e1) {
                                }
                            } catch (ContactNameAlreadyExistsException e) {
                                try {
                                    cryptoWalletWalletContact = moduleManager.findWalletContactByName("mati_bitcoins", appSession.getAppPublicKey(), referenceWalletSession.getIntraUserModuleManager().getPublicKey());
                                } catch (CantFindWalletContactException e1) {

                                } catch (WalletContactNotFoundException e1) {

                                } catch (CantListCryptoWalletIntraUserIdentityException e1) {

                                } catch (CantGetCryptoWalletException e1) {

                                }
                            } catch (Exception e) {

                            }

                            String myCryptoAddress = getWalletAddress(cryptoWalletWalletContact.getActorPublicKey());
                            HttpGet httpget = new HttpGet("http://52.27.68.19:15400/mati/hello/?address=" + myCryptoAddress);
                            ResponseHandler<String> responseHandler = new BasicResponseHandler();
                            SetServerString = Client.execute(httpget, responseHandler);

                            response = SetServerString;
                        } catch (ClientProtocolException e) {

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
        } catch (CantGetBalanceException e) {
            e.printStackTrace();
        }
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
                changeActivity(Activities.CCP_BITCOIN_WALLET_SEND_FORM_ACTIVITY,referenceWalletSession.getAppPublicKey());
                return true;
            }else if(id == BitcoinWalletConstants.IC_ACTION_HELP_PRESENTATION){
                setUpPresentation(settingsManager.loadAndGetSettings(referenceWalletSession.getAppPublicKey()).isPresentationHelpEnabled());
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

        try {
            CryptoWalletIntraUserIdentity intraUserLoginIdentity = referenceWalletSession.getIntraUserModuleManager();
            if(intraUserLoginIdentity!=null) {
                String intraUserPk = intraUserLoginIdentity.getPublicKey();

                List<CryptoWalletTransaction> list = moduleManager.listLastActorTransactionsByTransactionType(BalanceType.AVAILABLE, TransactionType.DEBIT, referenceWalletSession.getAppPublicKey(), intraUserPk, MAX_TRANSACTIONS, available_offset);

                lstCryptoWalletTransactionsAvailable.addAll(list);

                available_offset = lstCryptoWalletTransactionsAvailable.size();

                lstCryptoWalletTransactionsBook.addAll(moduleManager.listLastActorTransactionsByTransactionType(BalanceType.BOOK, TransactionType.DEBIT, referenceWalletSession.getAppPublicKey(), intraUserPk, MAX_TRANSACTIONS, book_offset));

                book_offset = lstCryptoWalletTransactionsBook.size();


                for (CryptoWalletTransaction cryptoWalletTransaction : lstCryptoWalletTransactionsAvailable) {
                    List<CryptoWalletTransaction> lst = new ArrayList<>();
                     lst = moduleManager.listTransactionsByActorAndType(BalanceType.getByCode(referenceWalletSession.getBalanceTypeSelected()), TransactionType.DEBIT, referenceWalletSession.getAppPublicKey(), cryptoWalletTransaction.getActorToPublicKey(), intraUserPk, MAX_TRANSACTIONS, 0);
                    long total = 0;
                    for(CryptoWalletTransaction cwt : lst){
                        total+= cwt.getAmount();
                    }

                    final long finalTotal = total;
                    lst.add(new CryptoWalletTransaction(){

                        @Override
                        public Actor getInvolvedActor() {
                            return null;
                        }

                        @Override
                        public UUID getContactId() {
                            return null;
                        }

                        @Override
                        public UUID getTransactionId() {
                            return null;
                        }

                        @Override
                        public String getTransactionHash() {
                            return null;
                        }

                        @Override
                        public CryptoAddress getAddressFrom() {
                            return null;
                        }

                        @Override
                        public CryptoAddress getAddressTo() {
                            return null;
                        }

                        @Override
                        public String getActorToPublicKey() {
                            return null;
                        }

                        @Override
                        public String getActorFromPublicKey() {
                            return null;
                        }

                        @Override
                        public Actors getActorToType() {
                            return null;
                        }

                        @Override
                        public Actors getActorFromType() {
                            return null;
                        }

                        @Override
                        public BalanceType getBalanceType() {
                            return null;
                        }

                        @Override
                        public TransactionType getTransactionType() {
                            return null;
                        }

                        @Override
                        public long getTimestamp() {
                            return 0;
                        }

                        @Override
                        public long getAmount() {
                            return finalTotal;
                        }

                        @Override
                        public long getRunningBookBalance() {
                            return 0;
                        }

                        @Override
                        public long getRunningAvailableBalance() {
                            return 0;
                        }

                        @Override
                        public String getMemo() {
                            return null;
                        }
                    });
                    GrouperItem<CryptoWalletTransaction, CryptoWalletTransaction> grouperItem = new GrouperItem<CryptoWalletTransaction, CryptoWalletTransaction>(lst, false, cryptoWalletTransaction);
                    data.add(grouperItem);
                }

                if(!data.isEmpty()){
                    FermatAnimationsUtils.showEmpty(getActivity(),false,emptyListViewsContainer);
                }


            }

        } catch (CantListTransactionsException e) {
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
    public void onItemClickListener(CryptoWalletTransaction data, int position) {

    }

    @Override
    public void onLongItemClickListener(CryptoWalletTransaction data, int position) {
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

        ShowMoneyType showMoneyType = (referenceWalletSession.getTypeAmount()== ShowMoneyType.BITCOIN.getCode()) ? ShowMoneyType.BITS : ShowMoneyType.BITCOIN;
        referenceWalletSession.setTypeAmount(showMoneyType);
        String moneyTpe = "";
        switch (showMoneyType){
            case BITCOIN:
                moneyTpe = "btc";
                break;
            case BITS:
                moneyTpe = "bits";
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
            if (((ReferenceWalletSession)appSession).getBalanceTypeSelected().equals(BalanceType.AVAILABLE.getCode())) {
                balanceAvailable = loadBalance(BalanceType.AVAILABLE);
                txt_balance_amount.setText(WalletUtils.formatBalanceString(bookBalance, referenceWalletSession.getTypeAmount()));
                txt_type_balance.setText(R.string.book_balance);
                referenceWalletSession.setBalanceTypeSelected(BalanceType.BOOK);
            } else if (referenceWalletSession.getBalanceTypeSelected().equals(BalanceType.BOOK.getCode())) {
                bookBalance = loadBalance(BalanceType.BOOK);
               txt_balance_amount.setText(WalletUtils.formatBalanceString(balanceAvailable, referenceWalletSession.getTypeAmount()));
                txt_type_balance.setText(R.string.available_balance);
                referenceWalletSession.setBalanceTypeSelected(BalanceType.AVAILABLE);
            }
        } catch (Exception e) {
            referenceWalletSession.getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.CRASH, FermatException.wrapException(e));
            Toast.makeText(getActivity().getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();
        }

    }

    private long loadBalance(BalanceType balanceType){
        long balance = 0;
        try {
            balance = referenceWalletSession.getModuleManager().getCryptoWallet().getBalance(balanceType, referenceWalletSession.getAppPublicKey());
        } catch (CantGetBalanceException e) {
            e.printStackTrace();
        } catch (CantGetCryptoWalletException e) {
            e.printStackTrace();
        }
        return balance;
    }


    private void updateBalances(){
        bookBalance = loadBalance(BalanceType.BOOK);
        balanceAvailable = loadBalance(BalanceType.AVAILABLE);
        txt_balance_amount.setText(
                WalletUtils.formatBalanceString(
                        (referenceWalletSession.getBalanceTypeSelected() == BalanceType.AVAILABLE.getCode())
                                ? balanceAvailable : bookBalance,
                        referenceWalletSession.getTypeAmount())
        );
    }





}

