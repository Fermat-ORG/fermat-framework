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
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.interfaces.CryptoBrokerIdentity;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.setting.CryptoBrokerWalletAssociatedSetting;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.interfaces.CryptoBrokerWalletManager;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.interfaces.CryptoBrokerWalletModuleManager;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CantGetActiveLoginIdentityException;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedWalletExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.reference_wallet.crypto_broker_wallet.R;
import com.bitdubai.reference_wallet.crypto_broker_wallet.session.CryptoBrokerWalletSession;
import com.bitdubai.reference_wallet.crypto_broker_wallet.util.FragmentsCommons;

import java.lang.ref.WeakReference;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nelson Ramirez
 * on 2015.11.24
 */
public class CryptoBrokerNavigationViewPainter implements NavigationViewPainter {

    private static final String TAG = "BrokerNavigationView";

    private CryptoBrokerWalletManager walletManager;
    private CryptoBrokerWalletSession session;
    private CryptoBrokerIdentity actorIdentity;
    private WeakReference<Context> activity;
    private ErrorManager errorManager;

    public CryptoBrokerNavigationViewPainter(Context activity, CryptoBrokerWalletSession session) {
        this.activity = new WeakReference<Context>(activity);
        this.session = session;

        errorManager = session.getErrorManager();

        try {
            final CryptoBrokerWalletModuleManager moduleManager = session.getModuleManager();
            walletManager = moduleManager.getCryptoBrokerWallet(session.getAppPublicKey());
            actorIdentity = walletManager.getAssociatedIdentity(session.getAppPublicKey());

        } catch (FermatException ex) {
            if (errorManager == null)
                Log.e(TAG, ex.getMessage(), ex);
            else
                errorManager.reportUnexpectedWalletException(Wallets.CBP_CRYPTO_BROKER_WALLET,
                        UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, ex);
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

            return new CryptoBrokerNavigationViewAdapter(activity.get(), stockData, earningsData);
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
            List<CryptoBrokerWalletAssociatedSetting> associatedWallets = walletManager.getCryptoBrokerWalletAssociatedSettings(session.getAppPublicKey());
            NumberFormat numberFormat = DecimalFormat.getInstance();

            for (CryptoBrokerWalletAssociatedSetting associatedWallet : associatedWallets) {
                Currency currency = associatedWallet.getMerchandise();
                float balance = walletManager.getAvailableBalance(currency, session.getAppPublicKey());

                stockItems.add(new NavViewFooterItem(currency.getFriendlyName(), numberFormat.format(balance)));
            }

        } catch (Exception ex) {

            stockItems.add(new NavViewFooterItem("Bitcoin", "145.32"));
            stockItems.add(new NavViewFooterItem("US Dollar", "14.04"));
            stockItems.add(new NavViewFooterItem("Bolivar", "350,400.25"));

            if (errorManager == null)
                Log.e(TAG, ex.getMessage(), ex);
            else
                errorManager.reportUnexpectedWalletException(Wallets.CBP_CRYPTO_BROKER_WALLET,
                        UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, ex);
        }

        return stockItems;
    }

    private List<NavViewFooterItem> getEarningsData() {
        ArrayList<NavViewFooterItem> earningsItems = new ArrayList<>();

        earningsItems.add(new NavViewFooterItem("US Dollar", "1,400.01"));
        earningsItems.add(new NavViewFooterItem("Bolivar", "350,251.87"));

        return earningsItems;
    }
}
