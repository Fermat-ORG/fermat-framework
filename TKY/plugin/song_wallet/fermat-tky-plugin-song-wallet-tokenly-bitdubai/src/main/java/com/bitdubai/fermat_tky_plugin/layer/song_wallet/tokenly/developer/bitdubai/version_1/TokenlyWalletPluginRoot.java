package com.bitdubai.fermat_tky_plugin.layer.song_wallet.tokenly.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_tky_plugin.layer.song_wallet.tokenly.developer.bitdubai.version_1.structure.TokenlyWalletManager;


/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 15/03/16.
 */
public class TokenlyWalletPluginRoot extends AbstractPlugin {

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER)
    private ErrorManager errorManager;

    /**
     * Represents the TokenlyWalletManager
     */
    TokenlyWalletManager tokenlyWalletManager;

    /**
     * Default constructor
     */
    public TokenlyWalletPluginRoot() {
        super(new PluginVersionReference(new Version()));
    }

    /**
     * This method returns the Plugin Manager.
     * @return
     */
    public FermatManager getFermatManager(){
        return this.tokenlyWalletManager;
    }

    private void initPluginManager(){
        this.tokenlyWalletManager = new TokenlyWalletManager();
    }

    public void start() throws CantStartPluginException{
        try{
            initPluginManager();
        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.API_TOKENLY,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    e);
            throw new CantStartPluginException(
                    CantStartPluginException.DEFAULT_MESSAGE,
                    e, "Cant start Song Wallet Tokenly plugin.",
                    null);
        }
        /**
         * nothing left to do.
         */
        this.serviceStatus = ServiceStatus.STARTED;
    }

}
