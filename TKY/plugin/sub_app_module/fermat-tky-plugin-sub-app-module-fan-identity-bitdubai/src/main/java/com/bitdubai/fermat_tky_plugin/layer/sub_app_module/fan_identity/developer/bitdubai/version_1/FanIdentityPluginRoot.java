package com.bitdubai.fermat_tky_plugin.layer.sub_app_module.fan_identity.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededPluginReference;
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
import com.bitdubai.fermat_tky_api.all_definitions.exceptions.TKYException;
import com.bitdubai.fermat_tky_api.layer.identity.fan.interfaces.TokenlyFanIdentityManager;
import com.bitdubai.fermat_tky_plugin.layer.sub_app_module.fan_identity.developer.bitdubai.version_1.structure.FanIdentityManager;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 14/03/16.
 */
public class FanIdentityPluginRoot extends AbstractPlugin {
    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER)
    private ErrorManager errorManager;

    @NeededPluginReference(platform = Platforms.TOKENLY, layer = Layers.IDENTITY,plugin = Plugins.TOKENLY_FAN)
    private TokenlyFanIdentityManager tokenlyFanIdentityManager;

    FanIdentityManager fanIdentityManager;
    /**
     * Default constructor
     */
    public FanIdentityPluginRoot() {
        super(new PluginVersionReference(new Version()));
    }

    /**
     * This method is used by the fermat-core to get the plugin manager in execution time.
     * @return
     */
    public FermatManager getManager(){
        return this.fanIdentityManager;
    }

    private void initPluginManager(){
        this.fanIdentityManager = new FanIdentityManager(errorManager,tokenlyFanIdentityManager);
    }

    public FanIdentityManager getFanIdentityManager() throws TKYException{
       try{
           if(fanIdentityManager == null) {
               fanIdentityManager = new FanIdentityManager(errorManager, tokenlyFanIdentityManager);
           }
           return fanIdentityManager;
       }catch (Exception e){
           errorManager.reportUnexpectedPluginException(
                   Plugins.TOKENLY_FAN_SUB_APP_MODULE,
                   UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                   e);
           throw new TKYException(FermatException.wrapException(e));
       }

    }

    @Override
    public void start() throws CantStartPluginException {
        try{
            initPluginManager();
        } catch (Exception e) {
            this.errorManager.reportUnexpectedPluginException(
                    Plugins.TOKENLY_FAN_SUB_APP_MODULE,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    e);
            throw new CantStartPluginException(
                    CantStartPluginException.DEFAULT_MESSAGE,
                    e, "Cant start Sub App Fan Identity Module plugin.",
                    null);
        }
        /**
         * nothing left to do.
         */
        this.serviceStatus = ServiceStatus.STARTED;

    }
    @Override
    public void stop(){
        this.serviceStatus = ServiceStatus.STOPPED;
    }
}
