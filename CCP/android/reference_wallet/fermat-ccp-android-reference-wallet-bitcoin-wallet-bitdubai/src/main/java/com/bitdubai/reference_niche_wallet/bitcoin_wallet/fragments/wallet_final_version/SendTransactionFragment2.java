package com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments.wallet_final_version;

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
import android.support.v7.widget.Toolbar;
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
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.ReferenceWallet;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.enums.VaultType;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
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
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWalletTransaction;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWalletWalletContact;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.BitcoinWalletConstants;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.adapters.ReceivetransactionsExpandableAdapter;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.animation.AnimationManager;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.enums.ShowMoneyType;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.models.GrouperItem;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.popup.PresentationBitcoinWalletDialog;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.utils.WalletUtils;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.session.ReferenceWalletSession;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.session.SessionConstant;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static android.widget.Toast.makeText;


/**
 * Fragment the show the list of open negotiations waiting for the broker and the customer un the Home activity
 *
 * @author MAtias Furszyfer
 */
public class SendTransactionFragment2 extends FermatWalletExpandableListFragment<GrouperItem,ReferenceWalletSession,ResourceProviderManager>
        implements FermatListItemListeners<CryptoWalletTransaction>{


    final TransactionType transactionType = TransactionType.DEBIT;
    ReferenceWalletSession referenceWalletSession;
//    SettingsManager<BitcoinWalletSettings> settingsManager;
    BlockchainNetworkType blockchainNetworkType;
    long before = 0;
    long after = 0;
    boolean pressed = false;
    CircularProgressBar circularProgressBar;
    Thread background;
    private int MAX_TRANSACTIONS = 20;
    // Fermat Managers
    private CryptoWallet moduleManager;
    //private ErrorManager errorManager;
    // Data
    private List<GrouperItem> openNegotiationList;
    private TextView txt_type_balance;
    private TextView txt_balance_amount;
    private long balanceAvailable;
    private View rootView;
    private List<CryptoWalletTransaction> lstCryptoWalletTransactionsAvailable;
    private int available_offset=0;
    private int book_offset=0;
    private long bookBalance;
    private LinearLayout emptyListViewsContainer;
    private AnimationManager animationManager;
    private FermatTextView txt_balance_amount_type;
    private int progress1=1;
    private  Map<Long, Long> runningDailyBalance;
    final Handler handler = new Handler();

    private BitcoinWalletSettings bitcoinWalletSettings = null;




    private ExecutorService _executor;

    public static SendTransactionFragment2 newInstance() {
        return new SendTransactionFragment2();
    }



    int progress = 1;
    int broadcasterID = 0;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        _executor = Executors.newFixedThreadPool(5);

        setHasOptionsMenu(true);

        lstCryptoWalletTransactionsAvailable = new ArrayList<>();

       // lstCryptoWalletTransactionsBook = new ArrayList<>();

        _executor.execute(new Runnable() {
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
            referenceWalletSession = appSession;
            moduleManager = referenceWalletSession.getModuleManager();
            //errorManager = appSession.getErrorManager();

//            if(lst==null){
//                startWizard(WizardTypes.CCP_WALLET_BITCOIN_START_WIZARD.getKey(),appSession, walletSettings, walletResourcesProviderManager, null);
//            }

            //get wallet settings


            try {
                bitcoinWalletSettings = moduleManager.loadAndGetSettings(referenceWalletSession.getAppPublicKey());
            } catch (Exception e) {
                bitcoinWalletSettings = null;
            }

            if (bitcoinWalletSettings == null) {
                bitcoinWalletSettings = new BitcoinWalletSettings();
                bitcoinWalletSettings.setIsContactsHelpEnabled(true);
                bitcoinWalletSettings.setIsPresentationHelpEnabled(true);
                bitcoinWalletSettings.setNotificationEnabled(true);

                blockchainNetworkType = BlockchainNetworkType.getDefaultBlockchainNetworkType();
                bitcoinWalletSettings.setBlockchainNetworkType(blockchainNetworkType);

                if(moduleManager!=null) moduleManager.persistSettings(referenceWalletSession.getAppPublicKey(), bitcoinWalletSettings);
            }
            else
            {
                if (bitcoinWalletSettings.getBlockchainNetworkType() == null) {
                    bitcoinWalletSettings.setBlockchainNetworkType(BlockchainNetworkType.getDefaultBlockchainNetworkType());
                }
                else
                {
                    blockchainNetworkType = bitcoinWalletSettings.getBlockchainNetworkType();
                }

            }

            try {
                if(moduleManager!=null) moduleManager.persistSettings(referenceWalletSession.getAppPublicKey(), bitcoinWalletSettings);
            } catch (Exception e) {
                e.printStackTrace();
            }



            System.out.println("Network Type" + blockchainNetworkType);
            final BitcoinWalletSettings bitcoinWalletSettingsTemp = bitcoinWalletSettings;

            _executor.submit(new Runnable() {
                @Override
                public void run() {
                    try {

                        handler.postDelayed(new Runnable() {
                            public void run() {
                                if (bitcoinWalletSettingsTemp.isPresentationHelpEnabled()) {
                                    setUpPresentation(false);
                                }
                            }
                        }, 500);

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });


            _executor.submit(new Runnable() {
                @Override
                public void run() {
                    setRunningDailyBalance();

                }
            });


        } catch (Exception ex) {
//            if (errorManager != null)
//                errorManager.reportUnexpectedWalletException(Wallets.CWP_WALLET_RUNTIME_WALLET_BITCOIN_WALLET_ALL_BITDUBAI,
//                        UnexpectedWalletExceptionSeverity.DISABLES_THIS_FRAGMENT, ex);
            ex.printStackTrace();
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
                    ActiveActorIdentityInformation cryptoWalletIntraUserIdentity = referenceWalletSession.getIntraUserModuleManager();
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
//            errorManager.reportUnexpectedWalletException(Wallets.CWP_WALLET_RUNTIME_WALLET_BITCOIN_WALLET_ALL_BITDUBAI,
//                    UnexpectedWalletExceptionSeverity.DISABLES_THIS_FRAGMENT, e);
            e.printStackTrace();
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
    String runningBalance;

    private void setUpDonut(LayoutInflater inflater)  {
        try {
        final RelativeLayout container_header_balance = getToolbarHeader();
        try {
            container_header_balance.removeAllViews();
        }catch (Exception e){

        }


        container_header_balance.setBackgroundColor(Color.parseColor("#06356f"));

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

            _executor.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        runningBalance = WalletUtils.formatBalanceStringNotDecimal(moduleManager.getBalance(BalanceType.AVAILABLE, referenceWalletSession.getAppPublicKey(), blockchainNetworkType), ShowMoneyType.BITCOIN.getCode());
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                circularProgressBar.setProgressValue(Integer.valueOf(runningBalance));
                                circularProgressBar.setProgressValue2(getBalanceAverage());
                                circularProgressBar.setBackgroundProgressColor(Color.parseColor("#022346"));
                                circularProgressBar.setProgressColor(Color.parseColor("#05ddd2"));
                                circularProgressBar.setProgressColor2(Color.parseColor("#05537c"));
                            }
                        });
                    } catch (CantGetBalanceException e) {
                        e.printStackTrace();
                    }

                }
            });


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

                        circularProgressBar.setProgressValue(Integer.valueOf(runningBalance));
                        circularProgressBar.setProgressValue2(getBalanceAverage());
                        return true;
                    }else {
                        //String receivedAddress = GET("http://52.27.68.19:15400/mati/address/");
                        String receivedAddress = "";

                       // try{


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
            _executor.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        final long balance = moduleManager.getBalance(BalanceType.getByCode(referenceWalletSession.getBalanceTypeSelected()), referenceWalletSession.getAppPublicKey(), blockchainNetworkType);

                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                txt_balance_amount.setText(WalletUtils.formatBalanceString(balance, referenceWalletSession.getTypeAmount()));
                            }
                        });
                    } catch (CantGetBalanceException e) {
                        e.printStackTrace();
                    }

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

        txt_balance_amount_type = (FermatTextView) balance_header.findViewById(R.id.txt_balance_amount_type);
        }
        catch (Exception e){
            e.printStackTrace();
//            errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.CRASH, FermatException.wrapException(e));

        }
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
                    ReferenceWallet.BASIC_WALLET_BITCOIN_WALLET,
                    blockchainNetworkType
            );
            walletAddres = cryptoAddress.getAddress();
        } catch (CantRequestCryptoAddressException e) {
//            errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.CRASH, FermatException.wrapException(e));
            Toast.makeText(getActivity().getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();
            e.printStackTrace();

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
                            CryptoWalletWalletContact cryptoWalletWalletContact = null;
                            try {
                                cryptoWalletWalletContact = moduleManager.createWalletContact(cryptoAddress, "regtest_bitcoins", "", "", Actors.EXTRA_USER, appSession.getAppPublicKey(),blockchainNetworkType);
                            } catch (CantCreateWalletContactException | ContactNameAlreadyExistsException e) {
                                try {
                                    cryptoWalletWalletContact = moduleManager.findWalletContactByName("regtest_bitcoins", appSession.getAppPublicKey(), referenceWalletSession.getIntraUserModuleManager().getPublicKey());
                                } catch (CantFindWalletContactException | CantListCryptoWalletIntraUserIdentityException | WalletContactNotFoundException | CantGetCryptoWalletException e3) {

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
                                    Toast.makeText(context, "Regtest bitcoin arrived", Toast.LENGTH_SHORT).show();
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
                setUpPresentation(moduleManager.loadAndGetSettings(referenceWalletSession.getAppPublicKey()).isPresentationHelpEnabled());
                return true;
            }



        } catch (Exception e) {
//            errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));
            makeText(getActivity(), "Oooops! recovering from system error",
                    Toast.LENGTH_SHORT).show();
            e.printStackTrace();
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
            ActiveActorIdentityInformation intraUserLoginIdentity = referenceWalletSession.getIntraUserModuleManager();
            if(intraUserLoginIdentity!=null) {
                String intraUserPk = intraUserLoginIdentity.getPublicKey();


                List<CryptoWalletTransaction> list = moduleManager.listLastActorTransactionsByTransactionType(BalanceType.AVAILABLE, TransactionType.DEBIT, referenceWalletSession.getAppPublicKey(), intraUserPk, blockchainNetworkType, MAX_TRANSACTIONS, 0);

                if(list!=null) {
                    lstCryptoWalletTransactionsAvailable.addAll(list);
                }

                //available_offset = lstCryptoWalletTransactionsAvailable.size();

                for (CryptoWalletTransaction cryptoWalletTransaction : lstCryptoWalletTransactionsAvailable) {
                    List<CryptoWalletTransaction> lst = moduleManager.listTransactionsByActorAndType(BalanceType.AVAILABLE, TransactionType.DEBIT, referenceWalletSession.getAppPublicKey(), cryptoWalletTransaction.getActorToPublicKey(), intraUserPk, blockchainNetworkType, MAX_TRANSACTIONS, 0);

                    GrouperItem<CryptoWalletTransaction, CryptoWalletTransaction> grouperItem = new GrouperItem<CryptoWalletTransaction, CryptoWalletTransaction>(lst, false, cryptoWalletTransaction);
                    data.add(grouperItem);
                }

                if(!data.isEmpty()){
                    FermatAnimationsUtils.showEmpty(getActivity(),true,emptyListViewsContainer);
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

                if(openNegotiationList.size() > 0)
                    FermatAnimationsUtils.showEmpty(getActivity(), false, emptyListViewsContainer);
            }
            else {

                FermatAnimationsUtils.showEmpty(getActivity(), true, emptyListViewsContainer);

            }
        }
    }

    @Override
    public void onErrorOccurred(Exception ex) {
        isRefreshing = false;
        if (isAttached) {
            swipeRefreshLayout.setRefreshing(false);
//            errorManager.reportUnexpectedPluginException(Plugins.CRYPTO_WALLET, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, ex);
            ex.printStackTrace();
        }
    }

    private void changeAmountType(){

        ShowMoneyType showMoneyType = (referenceWalletSession.getTypeAmount()== ShowMoneyType.BITCOIN.getCode()) ? ShowMoneyType.BITS : ShowMoneyType.BITCOIN;
        referenceWalletSession.setTypeAmount(showMoneyType);
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
            balance = referenceWalletSession.getModuleManager().getBalance(balanceType, referenceWalletSession.getAppPublicKey(),blockchainNetworkType);
            System.out.println("THE BALANCE IS " + balance);
        } catch (CantGetBalanceException e) {
            e.printStackTrace();
        } catch (Exception e) {
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

          /*  BitcoinWalletSettings bitcoinWalletSettings = null;
            try {
                bitcoinWalletSettings = settingsManager.loadAndGetSettings(referenceWalletSession.getAppPublicKey());
            }catch (Exception e){
                bitcoinWalletSettings = null;
            }*/

            if(bitcoinWalletSettings != null){


                blockchainNetworkType = bitcoinWalletSettings.getBlockchainNetworkType();

                if (bitcoinWalletSettings.getRunningDailyBalance() == null){

                    try {
                        long balance = moduleManager.getBalance(BalanceType.AVAILABLE, referenceWalletSession.getAppPublicKey(), blockchainNetworkType);

                        runningDailyBalance.put(currentTime, balance);

                    }catch (Exception e){
                        Log.e(TAG,"Balance null, please check this, line:"+new Throwable().getStackTrace()[0].getLineNumber());
                    }

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

                        runningDailyBalance.put(currentTime, moduleManager.getBalance(BalanceType.AVAILABLE, referenceWalletSession.getAppPublicKey(),blockchainNetworkType));

                    }
                    else
                    {
                        //update balance
                        this.updateDailyBalance(runningDailyBalance.size()-1,moduleManager.getBalance(BalanceType.AVAILABLE, referenceWalletSession.getAppPublicKey(),blockchainNetworkType));
                    }



                }


                bitcoinWalletSettings.setRunningDailyBalance(runningDailyBalance);
                if(moduleManager!=null) {
                    moduleManager.persistSettings(referenceWalletSession.getAppPublicKey(), bitcoinWalletSettings);
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
            if(code.equals("BlockchainDownloadComplete")) {
                //update toolbar color
                final Toolbar toolBar = getToolbar();
                toolBar.setBackgroundColor(Color.parseColor("#12aca1 "));
                makeText(getActivity(), "Blockchain Download Complete", Toast.LENGTH_SHORT).show();
            } else {
                if(code.equals("Btc_arrive"))
                {
                    //update balance amount
                    final String runningBalance = WalletUtils.formatBalanceStringNotDecimal(
                            moduleManager.getBalance(BalanceType.AVAILABLE, referenceWalletSession.getAppPublicKey(),
                                    blockchainNetworkType),ShowMoneyType.BITCOIN.getCode());

                    changeBalanceType(txt_type_balance, txt_balance_amount);
                    //System.out.println(System.currentTimeMillis());

                    circularProgressBar.setProgressValue(Integer.valueOf(runningBalance));
                    circularProgressBar.setProgressValue2(getBalanceAverage());
                }

            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}

