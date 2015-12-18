package com.bitdubai.reference_wallet.cash_money_wallet.app_connection;

import android.app.Activity;

import com.bitdubai.fermat_android_api.engine.FermatFragmentFactory;
import com.bitdubai.fermat_android_api.engine.FooterViewPainter;
import com.bitdubai.fermat_android_api.engine.HeaderViewPainter;
import com.bitdubai.fermat_android_api.engine.NavigationViewPainter;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.AppConnections;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.FermatSession;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserLoginIdentity;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.interfaces.FermatSettings;
import com.bitdubai.reference_wallet.cash_money_wallet.fragmentFactory.CashMoneyWalletFragmentFactory;
import com.bitdubai.reference_wallet.cash_money_wallet.preference_settings.CashMoneyWalletPreferenceSettings;
import com.bitdubai.reference_wallet.cash_money_wallet.session.CashMoneyWalletSession;

/**
 * Created by Alejandro Bicelis on 12/17/2015.
 */
public class CashMoneyWalletFermatAppConnection extends AppConnections{

    Activity activity;
    IntraUserLoginIdentity intraUserLoginIdentity;

    public CashMoneyWalletFermatAppConnection(Activity activity, IntraUserLoginIdentity intraUserLoginIdentity) {
        this.activity = activity;
        this.intraUserLoginIdentity = intraUserLoginIdentity;
    }

    @Override
    public FermatFragmentFactory getFragmentFactory() {
        return new CashMoneyWalletFragmentFactory();
    }

    @Override
    public FermatSession getSession() {
        return new CashMoneyWalletSession();
    }

    @Override
    public FermatSettings getSettings() {
        return new CashMoneyWalletPreferenceSettings();
    }

    @Override
    public NavigationViewPainter getNavigationViewPainter() {
        return null;
    }

    @Override
    public HeaderViewPainter getHeaderViewPainter() {
        return null;
    }

    @Override
    public FooterViewPainter getFooterViewPainter() {
        return null;
    }
}
