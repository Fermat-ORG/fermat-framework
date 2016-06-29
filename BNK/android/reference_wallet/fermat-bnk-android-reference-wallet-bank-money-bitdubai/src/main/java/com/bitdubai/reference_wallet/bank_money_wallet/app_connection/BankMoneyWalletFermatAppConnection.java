package com.bitdubai.reference_wallet.bank_money_wallet.app_connection;

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
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet_module.interfaces.BankMoneyWalletModuleManager;
import com.bitdubai.reference_wallet.bank_money_wallet.R;
import com.bitdubai.reference_wallet.bank_money_wallet.fragmentFactory.BankMoneyWalletFragmentFactory;
import com.bitdubai.reference_wallet.bank_money_wallet.util.ReferenceWalletConstants;

/**
 * Created by memo on 22/12/15.
 */
public class BankMoneyWalletFermatAppConnection extends AppConnections<ReferenceAppFermatSession<BankMoneyWalletModuleManager>> {

    public BankMoneyWalletFermatAppConnection(Context activity) {
        super(activity);
    }

    @Override
    public FermatFragmentFactory getFragmentFactory() {
        return new BankMoneyWalletFragmentFactory();
    }

    @Override
    public PluginVersionReference[] getPluginVersionReference() {
        return new PluginVersionReference[]{ new PluginVersionReference(
                Platforms.BANKING_PLATFORM,
                Layers.WALLET_MODULE,
                Plugins.BITDUBAI_BNK_BANK_MONEY_WALLET_MODULE,
                Developers.BITDUBAI,
                new Version()
        )};
    }

    @Override
    public ReferenceAppFermatSession<BankMoneyWalletModuleManager> getSession() { return getFullyLoadedSession(); }

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

    @Override
    public int getResource(int id) {
        switch (id) {
            case ReferenceWalletConstants.ADD_ACCOUNT_ACTION:
                return R.drawable.bw_add_icon_action_bar;

            case ReferenceWalletConstants.EDIT_ACCOUNT_ACTION:
                return R.drawable.bw_ic_action_edit;

            case ReferenceWalletConstants.SAVE_ACTION:
                return R.drawable.bw_ic_action_edit;

            case ReferenceWalletConstants.HELP_ACTION:
                return R.drawable.bw_help_icon_action_bar;

            default:
                return 0;
        }
    }
}
