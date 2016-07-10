package com.bitdubai.reference_wallet.cash_money_wallet.app_connection;

import android.content.Context;
import com.bitdubai.fermat_android_api.engine.FermatFragmentFactory;
import com.bitdubai.fermat_android_api.engine.FooterViewPainter;
import com.bitdubai.fermat_android_api.engine.HeaderViewPainter;
import com.bitdubai.fermat_android_api.engine.NavigationViewPainter;
import com.bitdubai.fermat_android_api.layer.definition.wallet.abstracts.AbstractReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.AppConnections;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Developers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserLoginIdentity;
import com.bitdubai.fermat_csh_api.layer.csh_wallet_module.interfaces.CashMoneyWalletModuleManager;
import com.bitdubai.reference_wallet.cash_money_wallet.fragmentFactory.CashMoneyWalletFragmentFactory;

/**
 * Created by Alejandro Bicelis on 12/17/2015.
 */
public class CashMoneyWalletFermatAppConnection extends AppConnections<ReferenceAppFermatSession<CashMoneyWalletModuleManager>>{

    IntraUserLoginIdentity intraUserLoginIdentity;

    public CashMoneyWalletFermatAppConnection(Context activity, IntraUserLoginIdentity intraUserLoginIdentity) {
        super(activity);
        this.intraUserLoginIdentity = intraUserLoginIdentity;
    }

    @Override
    public FermatFragmentFactory getFragmentFactory() {
        return new CashMoneyWalletFragmentFactory();
    }

    @Override
    public PluginVersionReference[] getPluginVersionReference() {
        return new PluginVersionReference[]{ new PluginVersionReference(
                Platforms.CASH_PLATFORM,
                Layers.WALLET_MODULE,
                Plugins.BITDUBAI_CSH_MONEY_WALLET_MODULE,
                Developers.BITDUBAI,
                new Version()
        )};
    }

    @Override
    public ReferenceAppFermatSession<CashMoneyWalletModuleManager> getSession() { return getFullyLoadedSession(); }

    @Override
    public NavigationViewPainter getNavigationViewPainter() { return null; }

    @Override
    public HeaderViewPainter getHeaderViewPainter() {
        return null;
    }

    @Override
    public FooterViewPainter getFooterViewPainter() {
        return null;
    }
}
