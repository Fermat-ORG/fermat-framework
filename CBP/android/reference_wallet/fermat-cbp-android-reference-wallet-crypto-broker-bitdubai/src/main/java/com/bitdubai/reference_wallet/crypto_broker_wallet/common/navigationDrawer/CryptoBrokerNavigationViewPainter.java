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

import com.bitdubai.fermat_android_api.engine.NavigationViewPainter;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.TimeFrequency;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.interfaces.CryptoBrokerIdentity;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.interfaces.EarningsPair;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.interfaces.EarningsSearch;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.setting.CryptoBrokerWalletAssociatedSetting;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.interfaces.CryptoBrokerWalletModuleManager;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CantGetActiveLoginIdentityException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.reference_wallet.crypto_broker_wallet.R;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.models.EarningsDetailData;
import com.bitdubai.reference_wallet.crypto_broker_wallet.session.CryptoBrokerWalletSession;
import com.bitdubai.reference_wallet.crypto_broker_wallet.util.FragmentsCommons;

import java.lang.ref.WeakReference;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import static com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets.CBP_CRYPTO_BROKER_WALLET;
import static com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT;


/**
 * Created by Nelson Ramirez
 * on 2015.11.24
 */
public class CryptoBrokerNavigationViewPainter implements NavigationViewPainter {

    private static final String TAG = "BrokerNavigationView";

    private CryptoBrokerWalletModuleManager moduleManager;
    private CryptoBrokerWalletSession session;
    private CryptoBrokerIdentity actorIdentity;
    private WeakReference<Context> activity;
    private ErrorManager errorManager;
    private NumberFormat numberFormat;

    public CryptoBrokerNavigationViewPainter(Context activity, CryptoBrokerWalletSession session) {
        this.activity = new WeakReference<>(activity);
        this.session = session;

        errorManager = session.getErrorManager();

        try {
            moduleManager = session.getModuleManager();
            actorIdentity = this.moduleManager.getAssociatedIdentity(session.getAppPublicKey());

        } catch (FermatException ex) {
            if (errorManager == null)
                Log.e(TAG, ex.getMessage(), ex);
            else
                errorManager.reportUnexpectedWalletException(CBP_CRYPTO_BROKER_WALLET,
                        DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, ex);
        }
    }

    @Override
    public View addNavigationViewHeader(ActiveActorIdentityInformation intraUserLoginIdentity) {
        try {
            return FragmentsCommons.setUpHeaderScreen((LayoutInflater) activity.get()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE), activity.get(), actorIdentity);
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
            adapter.setStockTitle("Daily Earnings");

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

        try {
            List<CryptoBrokerWalletAssociatedSetting> associatedWallets = moduleManager.getCryptoBrokerWalletAssociatedSettings(session.getAppPublicKey());
            numberFormat = DecimalFormat.getInstance();

            for (CryptoBrokerWalletAssociatedSetting associatedWallet : associatedWallets) {
                Currency currency = associatedWallet.getMerchandise();
                float balance = moduleManager.getAvailableBalance(currency, session.getAppPublicKey());

                stockItems.add(new NavViewFooterItem(currency.getFriendlyName(), numberFormat.format(balance)));
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
                final String linkedCurrency = earningsPair.getLinkedCurrency().getCode();
                final String earningCurrency = earningsPair.getEarningCurrency().getCode();

                String currencies = linkedCurrency + " / " + earningCurrency;
                String value = "0.0";

                final EarningsSearch search = earningsPair.getSearch();

                final List<EarningsDetailData> earningsDetails = EarningsDetailData.generateEarningsDetailData(search.listResults(), TimeFrequency.DAILY);
                if (!earningsDetails.isEmpty()) {
                    final double amount = earningsDetails.get(0).getAmount();
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
