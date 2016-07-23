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
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
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
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
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
import com.bitdubai.fermat_api.layer.modules.exceptions.ActorIdentityNotSelectedException;
import com.bitdubai.fermat_api.layer.modules.exceptions.CantGetSelectedActorIdentityException;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.fermat_bch_api.layer.crypto_network.faucet.CantGetCoinsFromFaucetException;
import com.bitdubai.fermat_bch_api.layer.definition.crypto_fee.BitcoinFee;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums.TransactionType;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.BitcoinWalletSettings;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantCreateWalletContactException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantFindWalletContactException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantGetBalanceException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantListTransactionsException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantRequestCryptoAddressException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.WalletContactNotFoundException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWallet;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWalletTransaction;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWalletWalletContact;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.adapters.ReceivetransactionsExpandableAdapter;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.animation.AnimationManager;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.enums.ShowMoneyType;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.models.GrouperItem;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.popup.BlockchainDownloadInfoDialog;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.popup.PresentationBitcoinWalletDialog;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.utils.WalletUtils;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.session.SessionConstant;


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


public class SendTransactionFragment2 extends FermatWalletExpandableListFragment<GrouperItem,ReferenceAppFermatSession<CryptoWallet>,ResourceProviderManager>
        implements FermatListItemListeners<CryptoWalletTransaction> {
    private  BlockchainNetworkType blockchainNetworkType;
    private long before = 0;
    private long after = 0;
    private boolean pressed = false;
    private CircularProgressBar circularProgressBar;
    private Thread background;

    FermatWorker worker;

    // Fermat Managers
    private CryptoWallet moduleManager;
    private List<GrouperItem> openNegotiationList;
    private TextView txt_type_balance;
    private TextView txt_balance_amount;
    private long balanceAvailable;
    private View rootView;
    private List<CryptoWalletTransaction> lstCryptoWalletTransactionsAvailable;
    private long bookBalance;
    private LinearLayout emptyListViewsContainer;
    private AnimationManager animationManager;
    private FermatTextView txt_balance_amount_type;
    private int progress1=1;
    private Map<Long, Long> runningDailyBalance;
    final Handler handler = new Handler();
    private BalanceType balanceType = BalanceType.AVAILABLE;

    private ActiveActorIdentityInformation intraUserLoginIdentity;

    private BitcoinWalletSettings bitcoinWalletSettings = null;

    private ExecutorService _executor;
    private ShowMoneyType typeAmountSelected =ShowMoneyType.BITCOIN;

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
            moduleManager = appSession.getModuleManager();

            intraUserLoginIdentity = appSession.getModuleManager().getSelectedActorIdentity();


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
                bitcoinWalletSettings = moduleManager.loadAndGetSettings(appSession.getAppPublicKey());
            } catch (Exception e) {
                bitcoinWalletSettings = null;
            }

            if (bitcoinWalletSettings == null) {
                bitcoinWalletSettings = new BitcoinWalletSettings();
                bitcoinWalletSettings.setIsContactsHelpEnabled(true);
                bitcoinWalletSettings.setIsPresentationHelpEnabled(true);
                bitcoinWalletSettings.setNotificationEnabled(true);
                bitcoinWalletSettings.setIsBlockchainDownloadEnabled(true);
                bitcoinWalletSettings.setFeedLevel(BitcoinFee.NORMAL.toString());
                blockchainNetworkType = BlockchainNetworkType.getDefaultBlockchainNetworkType();
                bitcoinWalletSettings.setBlockchainNetworkType(blockchainNetworkType);
                if(moduleManager!=null)
                    moduleManager.persistSettings(appSession.getAppPublicKey(), bitcoinWalletSettings);
            } else {
                if (bitcoinWalletSettings.getBlockchainNetworkType() == null)
                    bitcoinWalletSettings.setBlockchainNetworkType(BlockchainNetworkType.getDefaultBlockchainNetworkType());
                else
                    blockchainNetworkType = bitcoinWalletSettings.getBlockchainNetworkType();
            }


            try {
                if(moduleManager!=null) moduleManager.persistSettings(appSession.getAppPublicKey(), bitcoinWalletSettings);
            } catch (Exception e) {
                e.printStackTrace();
            }

            final BitcoinWalletSettings bitcoinWalletSettingsTemp = bitcoinWalletSettings;
            _executor.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        handler.postDelayed(new Runnable() {
                            public void run() {
                                try {

                                    if (bitcoinWalletSettingsTemp.isPresentationHelpEnabled()) {
                                        setUpPresentation(false);
                                    }
                                    else
                                    {
                                        showBlockchainProgress();
                                    }

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                setRunningDailyBalance();

                            }
                        }, 500);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });

            onRefresh();


        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void showBlockchainProgress()
    {
        //check blockchain progress

        try {
            int pendingBlocks = moduleManager.getBlockchainDownloadProgress(blockchainNetworkType).getPendingBlocks();
            final Toolbar toolBar = getToolbar();
            int toolbarColor = 0;
            if (pendingBlocks > 0) {
                //paint toolbar on red
                toolbarColor = Color.RED;
                if (bitcoinWalletSettings.isBlockchainDownloadEnabled())
                    setUpBlockchainProgress(false);
            } else {
                toolbarColor = Color.parseColor("#05CFC2");
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
        }


    }

    private void setUpPresentation(boolean checkButton) {
        PresentationBitcoinWalletDialog presentationBitcoinWalletDialog =
                null;
        try {


            presentationBitcoinWalletDialog = new PresentationBitcoinWalletDialog(
                    getActivity(),
                    appSession,
                    null,
                    (intraUserLoginIdentity == null) ? PresentationBitcoinWalletDialog.TYPE_PRESENTATION : PresentationBitcoinWalletDialog.TYPE_PRESENTATION_WITHOUT_IDENTITIES,
                    checkButton);

        } catch (Exception e) {
            e.printStackTrace();
        }


        presentationBitcoinWalletDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                Object o = appSession.getData(SessionConstant.PRESENTATION_IDENTITY_CREATED);
                if (o != null) {
                    if ((Boolean) (o))
                        appSession.removeData(SessionConstant.PRESENTATION_IDENTITY_CREATED);
                }
                //noinspection TryWithIdenticalCatches
                ActiveActorIdentityInformation cryptoWalletIntraUserIdentity = null;
                try {
                    cryptoWalletIntraUserIdentity = appSession.getModuleManager().getSelectedActorIdentity();
                } catch (CantGetSelectedActorIdentityException e) {
                    e.printStackTrace();
                } catch (ActorIdentityNotSelectedException e) {
                    e.printStackTrace();
                }
                if (cryptoWalletIntraUserIdentity == null) {
                    getActivity().onBackPressed();
                } else {
                    invalidate();
                }

                showBlockchainProgress();

            }
        });
        presentationBitcoinWalletDialog.show();
    }

    private void setUpBlockchainProgress(final boolean checkButton) {

        final int type;
        try {
            type = (intraUserLoginIdentity != null) ? PresentationBitcoinWalletDialog.TYPE_PRESENTATION : PresentationBitcoinWalletDialog.TYPE_PRESENTATION_WITHOUT_IDENTITIES;

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
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
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }




    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        try {
            super.onActivityCreated(savedInstanceState);
            animationManager = new AnimationManager(rootView,emptyListViewsContainer);
            getPaintActivtyFeactures().addCollapseAnimation(animationManager);
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
        getPaintActivtyFeactures().addCollapseAnimation(animationManager);
        super.onResume();
    }

    @Override
    public void onStop() {
        getPaintActivtyFeactures().removeCollapseAnimation(animationManager);

        if(worker != null)
          worker.shutdownNow();

        if(_executor != null)
          _executor.shutdownNow();

        super.onStop();
    }



    private void setUp(LayoutInflater inflater){
        try {
            setUpDonut(inflater);
            setUpScreen();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setUpScreen(){
        int[] emptyOriginalPos = new int[2];
        if(emptyListViewsContainer!=null)
            emptyListViewsContainer.getLocationOnScreen(emptyOriginalPos);    }
    String runningBalance;

    private void setUpDonut(LayoutInflater inflater)  {
        try {
            final RelativeLayout container_header_balance = getToolbarHeader();
            try {
                container_header_balance.removeAllViews();
            } catch (Exception e) {
                e.printStackTrace();
            }

            container_header_balance.setBackgroundColor(Color.parseColor("#06356f"));
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    Bitmap bitmap;
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

                _executor.submit(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            runningBalance = WalletUtils.formatBalanceStringNotDecimal(
                                    moduleManager.getBalance(BalanceType.AVAILABLE, appSession.getAppPublicKey(),
                                    blockchainNetworkType), ShowMoneyType.BITCOIN.getCode());
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                      setCircularProgressBar();
                                    }catch (Exception e){
                                        e.printStackTrace();
                                    }
                                }
                            });
                        } catch (CantGetBalanceException e) {
                            e.printStackTrace();
                        }

                    }
                });


            txt_type_balance = (TextView) balance_header.findViewById(R.id.txt_type_balance);

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

            final TextView txt_amount_type = (TextView) balance_header.findViewById(R.id.txt_balance_amount_type);
            txt_type_balance.setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    try {
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            pressed = true;
                            before = System.currentTimeMillis();

                            //TODO fijatse que no se lancen mas de un hilo
                            if (pressed) {
                                background = new Thread(new Runnable() {
                                    public void run() {
                                        try {
                                            // enter the code to be run while displaying the progressbar.
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

                            return true;

                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            pressed = false;
                            background.interrupt();
                            after = System.currentTimeMillis();
                            if (after - before < 2000) {
                                changeBalanceType(txt_type_balance, txt_balance_amount);
                                //System.out.println(System.currentTimeMillis());
                                setCircularProgressBar();
                                return true;
                            } else {
                                //String receivedAddress = GET("http://52.27.68.19:15400/mati/address/");
                                //TestNet Faucet
                                GETTestNet(getActivity());
                                progress1 = 1;
                                circularProgressBar.setProgressValue(progress1);
                                return true;


                            }
                        }
                    }catch (Exception e){
                        e.printStackTrace();
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
            try {
                _executor.submit(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            final long balance = moduleManager.getBalance(BalanceType.getByCode(
                                    balanceType.getCode()), appSession.getAppPublicKey(), blockchainNetworkType);

                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    txt_balance_amount.setText(WalletUtils.formatBalanceString(balance,typeAmountSelected.getCode()));

                                    switch (typeAmountSelected) {
                                        case BITCOIN:

                                            if (txt_balance_amount.getText().length() >= 7)
                                                txt_balance_amount.setTextSize(18);
                                            else
                                                txt_balance_amount.setTextSize(26);
                                            break;
                                        case BITS:

                                            txt_balance_amount.setTextSize(16);
                                            break;
                                    }
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
            // errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.CRASH, FermatException.wrapException(e));
        }
    }

    private CryptoAddress getWalletAddress(String actorPublicKey) {
        CryptoAddress walletAddress = null;
        //noinspection TryWithIdenticalCatches
        try {
            CryptoAddress cryptoAddress = moduleManager.requestAddressToKnownUser(
                    intraUserLoginIdentity.getPublicKey(),
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
            walletAddress = cryptoAddress;
        } catch (CantRequestCryptoAddressException e) {
            // errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.CRASH, FermatException.wrapException(e));
            Toast.makeText(getActivity().getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();
            e.printStackTrace();


        } catch (Exception e) {
            e.printStackTrace();
        }
        return walletAddress;
    }


    public void GETTestNet( final Context context){

         worker = new FermatWorker() {
            @Override
            protected Object doInBackground() throws Exception {

                String finalResponse = "";
                CryptoWalletWalletContact cryptoWalletWalletContact = null;

                try {
                    CryptoAddress cryptoAddress = new CryptoAddress("mtMFTiGfBpjL1GBki8zrk5UW8otD6Gt541", CryptoCurrency.BITCOIN);

                    try {
                        cryptoWalletWalletContact = moduleManager.findWalletContactByName("Testnet_bitcoins", appSession.getAppPublicKey(), moduleManager.getSelectedActorIdentity().getPublicKey());

                        if(cryptoWalletWalletContact == null)
                        {
                            cryptoWalletWalletContact = moduleManager.createWalletContact(
                                    cryptoAddress, "Testnet_bitcoins", "", "", Actors.EXTRA_USER, appSession.getAppPublicKey(),blockchainNetworkType);

                        }
                    } catch (WalletContactNotFoundException e) {

                        cryptoWalletWalletContact = moduleManager.createWalletContact(
                                cryptoAddress, "Testnet_bitcoins", "", "", Actors.EXTRA_USER, appSession.getAppPublicKey(),blockchainNetworkType);


                    } catch (CantFindWalletContactException |CantCreateWalletContactException e) {

                        finalResponse = "transaccion fallida";
                        e.printStackTrace();

                    } catch (Exception e) {
                        finalResponse = "transaccion fallida";
                        e.printStackTrace();
                    }

                    if(cryptoWalletWalletContact != null)
                        moduleManager.testNetGiveMeCoins(blockchainNetworkType, getWalletAddress(cryptoWalletWalletContact.getActorPublicKey()));

                    }
                    catch (CantGetCoinsFromFaucetException e) {
                        finalResponse = "transaccion fallida";
                        e.printStackTrace();
                    }


                    return finalResponse;

            }
        };
        worker.setContext(getActivity());
        worker.setCallBack(new FermatWorkerCallBack() {
            @SuppressWarnings("unchecked")
            @Override
            public void onPostExecute(Object... result) {

                if (result != null &&
                        result.length > 0) {
                    if (!result[0].toString().equals("transaccion fallida"))
                        Toast.makeText(context, "TestNet bitcoin arrived", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onErrorOccurred(Exception ex) {
                Toast.makeText(context, "TestNet Request Error", Toast.LENGTH_SHORT).show();

            }
        });
        worker.execute();


    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try {
            int id = item.getItemId();
            if (id == 1) {
//                moduleManager.launchNotification();
                changeActivity(Activities.CCP_BITCOIN_WALLET_SEND_FORM_ACTIVITY, appSession.getAppPublicKey());
                return true;
            } else
            {
                if (id == 4) {
                    changeActivity(Activities.CCP_BITCOIN_WALLET_REQUEST_FORM_ACTIVITY, appSession.getAppPublicKey());
                    return true;
                }
                else {
                    setUpPresentation(moduleManager.loadAndGetSettings(appSession.getAppPublicKey()).isPresentationHelpEnabled());
                    return true;
                }


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

        if(openNegotiationList!=null) {
            if (openNegotiationList.isEmpty()) {
               recyclerView.setVisibility(View.GONE);
                emptyListViewsContainer = (LinearLayout) layout.findViewById(R.id.empty);
                FermatAnimationsUtils.showEmpty(getActivity(), true, emptyListViewsContainer);
                emptyListViewsContainer.setVisibility(View.VISIBLE);
            }
        }else{
            recyclerView.setVisibility(View.GONE);
            emptyListViewsContainer = (LinearLayout) layout.findViewById(R.id.empty);
            FermatAnimationsUtils.showEmpty(getActivity(), true, emptyListViewsContainer);
            emptyListViewsContainer.setVisibility(View.VISIBLE);
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
            //noinspection unchecked
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

        //noinspection TryWithIdenticalCatches
        try {

            if(intraUserLoginIdentity!=null) {
                String intraUserPk = intraUserLoginIdentity.getPublicKey();


                int MAX_TRANSACTIONS = 20;
                List<CryptoWalletTransaction> list = moduleManager.listLastActorTransactionsByTransactionType(
                        BalanceType.AVAILABLE, TransactionType.DEBIT, appSession.getAppPublicKey(),
                        intraUserPk, blockchainNetworkType, MAX_TRANSACTIONS, 0);

                if(list!=null) {
                    lstCryptoWalletTransactionsAvailable.addAll(list);
                }

                //available_offset = lstCryptoWalletTransactionsAvailable.size();

                for (CryptoWalletTransaction cryptoWalletTransaction : lstCryptoWalletTransactionsAvailable) {
                    List<CryptoWalletTransaction> lst = moduleManager.listTransactionsByActorAndType(
                            BalanceType.AVAILABLE, TransactionType.DEBIT, appSession.getAppPublicKey(),
                            cryptoWalletTransaction.getActorToPublicKey(), intraUserPk, blockchainNetworkType, MAX_TRANSACTIONS, 0);

                    GrouperItem<CryptoWalletTransaction, CryptoWalletTransaction> grouperItem = new GrouperItem<>(lst, false, cryptoWalletTransaction);
                    data.add(grouperItem);
                }

                if(!data.isEmpty())
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            FermatAnimationsUtils.showEmpty(getActivity(), true, emptyListViewsContainer);
                        }
                    });
            }
        } catch (CantListTransactionsException e) {
            e.printStackTrace();
        } catch (Exception e){
            //time out
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
                //noinspection unchecked
                openNegotiationList = (ArrayList) result[0];
                if (adapter != null)
                    adapter.changeDataSet(openNegotiationList);

                if(openNegotiationList.size() > 0)
                {
                    recyclerView.setVisibility(View.VISIBLE);
                    FermatAnimationsUtils.showEmpty(getActivity(), false, emptyListViewsContainer);
                }
                else
                {
                    recyclerView.setVisibility(View.GONE);
                    FermatAnimationsUtils.showEmpty(getActivity(), true, emptyListViewsContainer);
                }
            }
            else {
                recyclerView.setVisibility(View.GONE);
                FermatAnimationsUtils.showEmpty(getActivity(), true, emptyListViewsContainer);
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
        appSession.setData(SessionConstant.TYPE_AMOUNT_SELECTED, showMoneyType);
        typeAmountSelected= showMoneyType;
        String moneyTpe = "";
        switch (showMoneyType){
            case BITCOIN:
                moneyTpe = "btc";
                if (txt_balance_amount.getText().length() >= 7)
                    txt_balance_amount.setTextSize(18);
                else
                    txt_balance_amount.setTextSize(26);
                break;
            case BITS:
                moneyTpe = "bits";
                txt_balance_amount.setTextSize(16);
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
            if (balanceType.getCode().equals(BalanceType.AVAILABLE.getCode())) {
               // balanceAvailable = loadBalance(BalanceType.AVAILABLE);
                txt_balance_amount.setText(WalletUtils.formatBalanceString(bookBalance, typeAmountSelected.getCode()));
                txt_type_balance.setText(R.string.book_balance);
                appSession.setData(SessionConstant.TYPE_BALANCE_SELECTED, BalanceType.BOOK);
                balanceType = BalanceType.BOOK;
            } else if (balanceType.getCode().equals(BalanceType.BOOK.getCode())) {
               // bookBalance = loadBalance(BalanceType.BOOK);
               txt_balance_amount.setText(WalletUtils.formatBalanceString(balanceAvailable, typeAmountSelected.getCode()));
                txt_type_balance.setText(R.string.available_balance);
                balanceType = BalanceType.AVAILABLE;
                appSession.setData(SessionConstant.TYPE_BALANCE_SELECTED, BalanceType.AVAILABLE);
            }

            switch (typeAmountSelected) {
                case BITCOIN:

                    if (txt_balance_amount.getText().length() >= 7)
                        txt_balance_amount.setTextSize(18);
                    else
                        txt_balance_amount.setTextSize(26);
                    break;
                case BITS:

                    txt_balance_amount.setTextSize(16);
                    break;
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

            if(bitcoinWalletSettings != null){

                blockchainNetworkType = bitcoinWalletSettings.getBlockchainNetworkType();
                if (bitcoinWalletSettings.getRunningDailyBalance() == null) {
                    try {
                        long balance = balanceAvailable;
                        runningDailyBalance.put(currentTime, balance);
                    }catch (Exception e) {
                        Log.e(TAG,"Balance null, please check this, line:"+new Throwable().getStackTrace()[0].getLineNumber());
                    }
                } else {
                    runningDailyBalance = bitcoinWalletSettings.getRunningDailyBalance();

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
                        this.updateDailyBalance(runningDailyBalance.size()-1,balanceAvailable);
                    }
                }

                bitcoinWalletSettings.setRunningDailyBalance(runningDailyBalance);
                if(moduleManager!=null) {
                    moduleManager.persistSettings(appSession.getAppPublicKey(), bitcoinWalletSettings);
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

                toolBar.setBackgroundColor(Color.parseColor("#05CFC2"));

               // makeText(getActivity(), "Blockchain Download Complete", Toast.LENGTH_SHORT).show();
            } else {
                if(code.equals("Btc_arrive"))
                {
                    //update balance amount

                    changeBalanceType(txt_type_balance, txt_balance_amount);

                    runningBalance = WalletUtils.formatBalanceStringNotDecimal(balanceAvailable,ShowMoneyType.BITCOIN.getCode());

                    setCircularProgressBar();

                    //update transactions
                    onRefresh();
                }

            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void setCircularProgressBar()
    {
        int balance = 0;
        if(Integer.valueOf(runningBalance) > 100)
            balance = 100;
         else
            balance = Integer.valueOf(runningBalance);


        circularProgressBar.setProgressValue(Integer.valueOf(runningBalance));
        circularProgressBar.setProgressValue2(getBalanceAverage());
        circularProgressBar.setBackgroundProgressColor(Color.parseColor("#022346"));
        circularProgressBar.setProgressColor(Color.parseColor("#05ddd2"));
        circularProgressBar.setProgressColor2(Color.parseColor("#05537c"));
    }
}

