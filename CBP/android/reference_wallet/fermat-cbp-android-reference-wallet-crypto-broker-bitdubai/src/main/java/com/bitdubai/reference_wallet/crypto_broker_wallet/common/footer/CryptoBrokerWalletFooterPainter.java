package com.bitdubai.reference_wallet.crypto_broker_wallet.common.footer;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.bitdubai.fermat_android_api.engine.FooterViewPainter;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.setting.CryptoBrokerWalletAssociatedSetting;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.exceptions.CantGetCryptoBrokerWalletException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.interfaces.CryptoBrokerWalletManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedWalletExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.reference_wallet.crypto_broker_wallet.R;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.adapters.StockBarChartPageAdapter;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.models.TestData;
import com.bitdubai.reference_wallet.crypto_broker_wallet.fragments.home.StockStatisticsData;
import com.bitdubai.reference_wallet.crypto_broker_wallet.session.CryptoBrokerWalletSession;
import com.viewpagerindicator.CirclePageIndicator;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nelson Ramirez
 *
 * @since 17/12/15.
 */
public class CryptoBrokerWalletFooterPainter implements FooterViewPainter, ViewPager.OnPageChangeListener {

    private final List<StockStatisticsData> data;
    private final Activity activity;
    private final CryptoBrokerWalletSession session;
    private FermatTextView stockCurrency;
    private FermatTextView stockQuantity;

    public CryptoBrokerWalletFooterPainter(Activity activity, CryptoBrokerWalletSession fullyLoadedSession) {
        this.activity = activity;
        session = fullyLoadedSession;

        data = getData();
    }

    @Override
    public ViewGroup addFooterViewContainer(LayoutInflater layoutInflater, ViewGroup footer_container) {
        ViewGroup layout = (ViewGroup) layoutInflater.inflate(R.layout.cbw_footer_stock_bar_chart, footer_container, true);

        StockBarChartPageAdapter pageAdapter = new StockBarChartPageAdapter(activity.getFragmentManager(), data);
        ViewPager stockViewPager = (ViewPager) layout.findViewById(R.id.cbw_stock_view_pager);
        stockViewPager.setAdapter(pageAdapter);
        stockViewPager.setOffscreenPageLimit(3);
        stockViewPager.addOnPageChangeListener(this);

        CirclePageIndicator indicator = (CirclePageIndicator) layout.findViewById(R.id.cbw_stock_chart_indicator);
        indicator.setViewPager(stockViewPager);
        indicator.setSnap(true);

        return layout;
    }

    @Override
    public View addNavigationViewFooterElementVisible(LayoutInflater layoutInflater, FrameLayout slide_container) {
        View footerBar = layoutInflater.inflate(R.layout.cbw_footer_view_bar, slide_container, true);

        StockStatisticsData stockStatisticsData = data.get(0);

        stockCurrency = (FermatTextView) footerBar.findViewById(R.id.cbw_footer_bar_stock_currency);
        stockCurrency.setText(stockStatisticsData.getCurrency().getFriendlyName());

        stockQuantity = (FermatTextView) footerBar.findViewById(R.id.cbw_footer_bar_stock_quantity);
        stockQuantity.setText(DecimalFormat.getInstance().format(stockStatisticsData.getBalance()));

        return footerBar;
    }

    @Override
    public Drawable addBodyBackground() {
        return null;
    }

    @Override
    public int addBodyBackgroundColor() {
        return Color.parseColor("#AAAAAA");
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        StockStatisticsData stockStatisticsData = data.get(position);

        stockQuantity.setText(DecimalFormat.getInstance().format(stockStatisticsData.getBalance()));
        stockCurrency.setText(stockStatisticsData.getCurrency().getFriendlyName());
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private List<StockStatisticsData> getData() {
        List<StockStatisticsData> data = new ArrayList<>();

        ErrorManager errorManager = session.getErrorManager();

        try {
            CryptoBrokerWalletManager walletManager = session.getModuleManager().getCryptoBrokerWallet(session.getAppPublicKey());
            List<CryptoBrokerWalletAssociatedSetting> associatedWallets = walletManager.getCryptoBrokerWalletAssociatedSettings(session.getAppPublicKey());

            for (CryptoBrokerWalletAssociatedSetting associatedWallet : associatedWallets) {
                data.add(new StockStatisticsData(associatedWallet, session));
            }

        } catch (Exception e) {

            data.addAll(TestData.getStockStadisticsData());

            if (errorManager == null)
                Log.e("BrokerWalletFooter", e.getMessage(), e);
            else
                errorManager.reportUnexpectedWalletException(Wallets.CBP_CRYPTO_BROKER_WALLET,
                        UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
        }

        return data;
    }
}
