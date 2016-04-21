package com.bitdubai.reference_niche_wallet.loss_protected_wallet.fragments.wallet_final_version;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
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
import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.Views.CircularProgressBar;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatWorkerCallBack;
import com.bitdubai.fermat_android_api.ui.util.FermatWorker;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantListCryptoWalletIntraUserIdentityException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.LossProtectedWalletSettings;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions.CantGetCryptoLossProtectedWalletException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions.CantGetLossProtectedBalanceException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.interfaces.LossProtectedWallet;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.interfaces.LossProtectedWalletIntraUserIdentity;
import com.bitdubai.fermat_cer_api.all_definition.interfaces.ExchangeRate;
import com.bitdubai.fermat_cer_api.layer.provider.interfaces.CurrencyExchangeRateProviderManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedWalletExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.LossProtectedWalletConstants;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.animation.AnimationManager;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.enums.ShowMoneyType;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.popup.PresentationBitcoinWalletDialog;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.utils.WalletUtils;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.session.LossProtectedWalletSession;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.session.SessionConstant;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static android.widget.Toast.makeText;

/**
 * Created by Gian Barboza on 18/04/16.
 */
public class HomeFragment extends AbstractFermatFragment<LossProtectedWalletSession,ResourceProviderManager> {

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
    private TextView txt_touch_to_change;
    private TextView txt_balance_amount;
    private TextView txt_balance_amount_type;
    private TextView txt_exchange_rate;
    private long balanceAvailable;
    private View rootView;


    private long realBalance;
    private LinearLayout emptyListViewsContainer;
    private AnimationManager animationManager;
   // private FermatTextView txt_balance_amount_type;
    private int progress1=1;


    private UUID exchangeProviderId = null;

    private long exchangeRate = 0;

    public static HomeFragment newInstance(){ return new HomeFragment(); }


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
            rootView = inflater.inflate(R.layout.lossprotected_home, container, false);
            setUp(inflater);


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
            setUpHeader(inflater);
            //setUpDonut(inflater);

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
            if (BalanceType.getByCode(lossProtectedWalletSession.getBalanceTypeSelected()).equals(BalanceType.AVAILABLE))
                balance = moduleManager.getBalance(BalanceType.AVAILABLE, lossProtectedWalletSession.getAppPublicKey(), blockchainNetworkType, "0");
            else
                balance = moduleManager.getRealBalance(lossProtectedWalletSession.getAppPublicKey(), blockchainNetworkType);

            txt_balance_amount.setText(WalletUtils.formatBalanceString(balance, lossProtectedWalletSession.getTypeAmount()));


        }catch (Exception e){
            errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));
            makeText(getActivity(), "Oooops! recovering from system error",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void earningAndLosts(){

    }

    private void changeAmountType(){

        ShowMoneyType showMoneyType = (lossProtectedWalletSession.getTypeAmount()== ShowMoneyType.BITCOIN.getCode()) ? ShowMoneyType.BITS : ShowMoneyType.BITCOIN;
        lossProtectedWalletSession.setTypeAmount(showMoneyType);
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

    /**
     * Method to change the balance type
     */
    private void changeBalanceType(TextView txt_type_balance,TextView txt_balance_amount) {
        updateBalances();
        try {
            if (appSession.getBalanceTypeSelected().equals(BalanceType.AVAILABLE.getCode())) {
                realBalance = loadBalance(BalanceType.REAL);
                txt_balance_amount.setText(WalletUtils.formatBalanceString(realBalance, lossProtectedWalletSession.getTypeAmount()));
                txt_type_balance.setText(R.string.real_balance_text);
                lossProtectedWalletSession.setBalanceTypeSelected(BalanceType.REAL);
            } else if (lossProtectedWalletSession.getBalanceTypeSelected().equals(BalanceType.REAL.getCode())) {
                balanceAvailable = loadBalance(BalanceType.AVAILABLE);
                txt_balance_amount.setText(WalletUtils.formatBalanceString(balanceAvailable, lossProtectedWalletSession.getTypeAmount()));
                txt_type_balance.setText(R.string.available_balance_text);
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

                    ExchangeRate rate = (ExchangeRate) result[0];
                    // progressBar.setVisibility(View.GONE);
                    txt_exchange_rate.setText("1 BTC = " + String.valueOf(rate.getPurchasePrice()) + " USD");

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
