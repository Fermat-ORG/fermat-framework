package com.bitdubai.sub_app.crypto_customer_identity.app_connection;

import android.content.Context;

import com.bitdubai.fermat_android_api.engine.FermatFragmentFactory;
import com.bitdubai.fermat_android_api.engine.FooterViewPainter;
import com.bitdubai.fermat_android_api.engine.HeaderViewPainter;
import com.bitdubai.fermat_android_api.engine.NavigationViewPainter;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.AppConnections;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Developers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_identity.interfaces.CryptoCustomerIdentityModuleManager;
import com.bitdubai.sub_app.crypto_customer_identity.R;
import com.bitdubai.sub_app.crypto_customer_identity.fragmentFactory.CryptoCustomerIdentityFragmentFactory;
import com.bitdubai.sub_app.crypto_customer_identity.util.FragmentsCommons;


/**
 * Created by Nelson Ramirez
 *
 * @since 2015.12.17
 */
public class CryptoCustomerIdentityFermatAppConnection extends AppConnections<ReferenceAppFermatSession<CryptoCustomerIdentityModuleManager>> {

    public CryptoCustomerIdentityFermatAppConnection(Context activity) {
        super(activity);
    }

    @Override
    public FermatFragmentFactory getFragmentFactory() {
        return new CryptoCustomerIdentityFragmentFactory();
    }

    @Override
    public PluginVersionReference[] getPluginVersionReference() {
        return new PluginVersionReference[]{new PluginVersionReference(
                Platforms.CRYPTO_BROKER_PLATFORM,
                Layers.SUB_APP_MODULE,
                Plugins.CRYPTO_CUSTOMER_IDENTITY,
                Developers.BITDUBAI,
                new Version()
        )};
    }

    @Override
    public ReferenceAppFermatSession<CryptoCustomerIdentityModuleManager> getSession() {
        return getFullyLoadedSession();
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

    @Override
    public int getResource(int id) {
        switch (id) {
            case FragmentsCommons.ADD_IDENTITY_OPTION_MENU_ID:
                return R.drawable.new_identity_button;

            case FragmentsCommons.HELP_OPTION_MENU_ID:
                return R.drawable.help_button;

            case FragmentsCommons.CREATE_IDENTITY_MENU_ID:
                return R.drawable.save_changes_button;

            case FragmentsCommons.GEOLOCATION_SETTINGS_OPTION_MENU_ID:
                return R.drawable.cbp_id_geolocation_icon;

            default:
                return 0;
        }
    }
}
