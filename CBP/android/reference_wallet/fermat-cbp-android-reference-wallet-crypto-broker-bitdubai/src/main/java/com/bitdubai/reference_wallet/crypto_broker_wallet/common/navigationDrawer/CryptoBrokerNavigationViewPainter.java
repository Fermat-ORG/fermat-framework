package com.bitdubai.reference_wallet.crypto_broker_wallet.common.navigationDrawer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.bitdubai.fermat_android_api.engine.FermatApplicationCaller;
import com.bitdubai.fermat_android_api.engine.NavigationViewPainter;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.CurrencyTypes;
import com.bitdubai.fermat_api.layer.all_definition.enums.TimeFrequency;
import com.bitdubai.fermat_api.layer.all_definition.util.BitcoinConverter;
import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.interfaces.CryptoBrokerIdentity;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.interfaces.EarningTransaction;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.interfaces.EarningsPair;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.setting.CryptoBrokerWalletAssociatedSetting;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.interfaces.CryptoBrokerWalletModuleManager;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CantGetActiveLoginIdentityException;
import com.bitdubai.reference_wallet.crypto_broker_wallet.R;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.models.EarningsDetailData;
import com.bitdubai.reference_wallet.crypto_broker_wallet.util.FragmentsCommons;

import java.lang.ref.WeakReference;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import static com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT;
import static com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets.CBP_CRYPTO_BROKER_WALLET;


/**
 * Created by Nelson Ramirez
 * on 2015.11.24
 */
public class CryptoBrokerNavigationViewPainter implements NavigationViewPainter {

    private static final String TAG = "BrokerNavigationView";

    private CryptoBrokerWalletModuleManager moduleManager;
    private ReferenceAppFermatSession<CryptoBrokerWalletModuleManager> session;
    private CryptoBrokerIdentity actorIdentity;
    private WeakReference<Context> activity;
    private ErrorManager errorManager;
    private NumberFormat numberFormat;
    private WeakReference<FermatApplicationCaller> applicationsHelper;

    public CryptoBrokerNavigationViewPainter(Context activity, ReferenceAppFermatSession<CryptoBrokerWalletModuleManager> session,
                                             FermatApplicationCaller applicationsHelper) {
        this.activity = new WeakReference<>(activity);
        this.session = session;

        errorManager = session.getErrorManager();

        try {
            moduleManager = session.getModuleManager();
            actorIdentity = this.moduleManager.getAssociatedIdentity(session.getAppPublicKey());
            this.applicationsHelper = new WeakReference<>(applicationsHelper);

        } catch (FermatException ex) {
            if (errorManager == null)
                Log.e(TAG, ex.getMessage(), ex);
            else
                errorManager.reportUnexpectedWalletException(CBP_CRYPTO_BROKER_WALLET,
                        DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, ex);
        }
    }

    @Override
    public View addNavigationViewHeader() {
        try {
            return FragmentsCommons.setUpHeaderScreen((LayoutInflater) activity.get()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE), activity.get(), actorIdentity, applicationsHelper.get());
        } catch (CantGetActiveLoginIdentityException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public FermatAdapter addNavigationViewAdapter() {
        try {
            List<NavViewFooterItem> stockData = getStockData();
            List<NavViewFooterItem> earningsData = getEarningsData();

            final CryptoBrokerNavigationViewAdapter adapter = new CryptoBrokerNavigationViewAdapter(activity.get(), stockData, earningsData);
            adapter.setStockTitle("Current Stock");
            adapter.setEarningsTitle("Daily Earnings");

            return adapter;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ViewGroup addNavigationViewBodyContainer(LayoutInflater layoutInflater, ViewGroup base) {
        return (RelativeLayout) layoutInflater.inflate(R.layout.cbw_navigation_view_bottom, base, true);
    }

    @Override
    public Bitmap addBodyBackground() {
        Bitmap drawable = null;
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inScaled = true;
            options.inSampleSize = 5;
            drawable = BitmapFactory.decodeResource(activity.get().getResources(), R.drawable.cbw_navigation_drawer_background, options);
        } catch (OutOfMemoryError error) {
            error.printStackTrace();
        }
        return drawable;
    }

    @Override
    public int addBodyBackgroundColor() {
        return 0;
    }

    @Override
    public RecyclerView.ItemDecoration addItemDecoration() {
        return null;
    }

    @Override
    public boolean hasBodyBackground() {
        return true;
    }

    @Override
    public boolean hasClickListener() {
        return false;
    }

    private List<NavViewFooterItem> getStockData() {
        List<NavViewFooterItem> stockItems = new ArrayList<>();
        List<Currency> merchandises = new ArrayList<>();

        try {
            numberFormat = DecimalFormat.getInstance();


            //Get a list of merchandises
            List<CryptoBrokerWalletAssociatedSetting> associatedWallets = moduleManager.getCryptoBrokerWalletAssociatedSettings(session.getAppPublicKey());
            for (CryptoBrokerWalletAssociatedSetting associatedWallet : associatedWallets) {

                if (!merchandises.contains(associatedWallet.getMerchandise()))
                    merchandises.add(associatedWallet.getMerchandise());
            }


            //Iterate through them and get their balances and friendly names
            for (Currency merchandise : merchandises) {
                double balance = moduleManager.getAvailableBalance(merchandise, session.getAppPublicKey());

                //If Bitcoin, convert to satoshi
                if (CurrencyTypes.CRYPTO.equals(merchandise.getType()) && CryptoCurrency.BITCOIN.equals(merchandise))
                    balance = BitcoinConverter.convert(balance, BitcoinConverter.Currency.SATOSHI, BitcoinConverter.Currency.BITCOIN);

                stockItems.add(new NavViewFooterItem(merchandise.getFriendlyName(), numberFormat.format(balance)));
            }

        } catch (Exception ex) {
            if (errorManager == null)
                Log.e(TAG, ex.getMessage(), ex);
            else
                errorManager.reportUnexpectedWalletException(CBP_CRYPTO_BROKER_WALLET, DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, ex);
        }

        return stockItems;
    }

    private List<NavViewFooterItem> getEarningsData() {
        ArrayList<NavViewFooterItem> earningsItems = new ArrayList<>();

        try {
            final List<EarningsPair> earningsPairs = moduleManager.getEarningsPairs(session.getAppPublicKey());
            for (EarningsPair earningsPair : earningsPairs) {
                final Currency linkedCurrency = earningsPair.getLinkedCurrency();
                final String linkedCurrencyCode = linkedCurrency.getCode();

                final Currency earningCurrency = earningsPair.getEarningCurrency();
                final String earningCurrencyCode = earningCurrency.getCode();

                String currencies = new StringBuilder().append(linkedCurrencyCode).append(" / ").append(earningCurrencyCode).toString();
                String value = "0.0";

                final List<EarningTransaction> earnings = moduleManager.searchEarnings(earningsPair);
                final List<EarningsDetailData> earningsDetails = EarningsDetailData.generateEarningsDetailData(earnings, TimeFrequency.DAILY);
                if (!earningsDetails.isEmpty()) {
                    double amount = earningsDetails.get(0).getAmount();

                    if (earningCurrency.getType() == CurrencyTypes.CRYPTO && CryptoCurrency.BITCOIN.getCode().equals(earningCurrencyCode))
                        amount = BitcoinConverter.convert(amount, BitcoinConverter.Currency.SATOSHI, BitcoinConverter.Currency.BITCOIN);

                    value = numberFormat.format(amount);
                }

                earningsItems.add(new NavViewFooterItem(currencies, value));
            }

        } catch (Exception ex) {
            if (errorManager == null)
                Log.e(TAG, ex.getMessage(), ex);
            else
                errorManager.reportUnexpectedWalletException(CBP_CRYPTO_BROKER_WALLET, DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, ex);
        }

        return earningsItems;
    }
}
