package com.bitdubai.fermat_dap_plugin.digital_asset_transaction.redeem_point_redemption.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.DealsWithPluginIdentity;
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.DealsWithEvents;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventManager;

import java.util.UUID;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 16/09/15.
 */
public class RedeemPointRedemptionPluginRoot implements Plugin, Service, DealsWithEvents, DealsWithErrors, DealsWithPluginIdentity {

    //VARIABLE DECLARATION
    private EventManager eventManager;
    private UUID pluginId;
    private ServiceStatus status;
    private ErrorManager errorManager;

    {
        status = ServiceStatus.CREATED;
    }

    //CONSTRUCTORS

    public RedeemPointRedemptionPluginRoot() {
    }


    //PUBLIC METHODS


    //PRIVATE METHODS

    //GETTER AND SETTERS
    @Override
    public void setEventManager(EventManager eventManager) {
        this.eventManager = eventManager;
    }

    @Override
    public void setId(UUID pluginId) {
        this.pluginId = pluginId;
    }

    @Override
    public void start() throws CantStartPluginException {
        this.status = ServiceStatus.STARTED;
    }

    @Override
    public void pause() {
        this.status = ServiceStatus.PAUSED;
    }

    @Override
    public void resume() {
        this.status = ServiceStatus.STARTED;
    }

    @Override
    public void stop() {
        this.status = ServiceStatus.STOPPED;
    }

    @Override
    public ServiceStatus getStatus() {
        return status;
    }

    @Override
    public void setErrorManager(ErrorManager errorManager) {
        this.errorManager = errorManager;
    }

    @Override
    public void setPluginId(UUID pluginId) {
        this.pluginId = pluginId;
    }

    //INNER CLASSES
}
