package com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_statistics.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_statistics.exceptions.CantGetWalletStatisticsException;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_statistics.interfaces.WalletStatistics;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_statistics.interfaces.WalletStatisticsManager;

import java.util.UUID;

/**
 * TODO: This plugin do .
 * <p/>
 * TODO: DETAIL...............................................
 * <p/>
 * <p/>
 * Created by someone.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class WalletStatisticsNetworkServicePluginRoot extends AbstractPlugin implements
        WalletStatisticsManager {

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER)
    private ErrorManager errorManager;

    public WalletStatisticsNetworkServicePluginRoot() {
        super(new PluginVersionReference(new Version()));
    }

    @Override
    public WalletStatistics getWalletStatistics(UUID walletCatalogId) throws CantGetWalletStatisticsException {
        //TODO METODO CON RETURN NULL - OJO: solo INFORMATIVO de ayuda VISUAL para DEBUG - Eliminar si molesta
        return null;
    }
}
