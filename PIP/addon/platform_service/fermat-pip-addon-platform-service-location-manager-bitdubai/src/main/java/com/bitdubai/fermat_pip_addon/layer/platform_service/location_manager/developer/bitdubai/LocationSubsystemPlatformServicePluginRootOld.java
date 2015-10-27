package com.bitdubai.fermat_pip_addon.layer.platform_service.location_manager.developer.bitdubai;

import com.bitdubai.fermat_api.Addon;
import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
//TODO se movio la interface
//import com.bitdubai.fermat_api.layer.osa_android.device_location.DealsWithDeviceLocation;
//import com.bitdubai.fermat_api.layer.osa_android.device_location.LocationManager;
import com.bitdubai.fermat_api.layer.osa_android.location_system.DealsWithDeviceLocation;
import com.bitdubai.fermat_api.layer.osa_android.location_system.LocationManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.DealsWithEvents;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventManager;
import com.bitdubai.fermat_pip_addon.layer.platform_service.location_manager.developer.bitdubai.version_1.exception.LocationServiceException;
import com.bitdubai.fermat_pip_addon.layer.platform_service.location_manager.developer.bitdubai.version_1.structure.LocationServiceMonitorAgent;

import java.util.UUID;


/**
 * Created by loui on 28/04/15.
 */
public class LocationSubsystemPlatformServicePluginRootOld implements Addon, DealsWithDeviceLocation, DealsWithErrors, DealsWithEvents, DealsWithPluginDatabaseSystem, Plugin, Service {

    private ErrorManager errorManager;
    /**
     * This plugin still doesn't listen or handle any kind of event, but the implementation will be keep for future features.
     */
    private EventManager eventManager;
    private PluginDatabaseSystem pluginDatabaseSystem;
    /**
     * {@link Service} Interface member variables.
     */
    private ServiceStatus serviceStatus;
    private UUID uuid;
    private LocationServiceMonitorAgent locationServiceMonitorAgent;
    //TODO: Falta Plugins ENUM para el módulo
    private Plugins plugins;


    private LocationManager locationManager;



    public LocationSubsystemPlatformServicePluginRootOld() {
        serviceStatus = ServiceStatus.CREATED;
    }

    /**
     * DealsWithDeviceLocation Interface implementation.
     */
    @Override
    public void setLocationManager(LocationManager locationManager){
        this.locationManager = locationManager;
    }

    /**
     * DealsWithErrors Interface implementation.
     */
    @Override
    public void setErrorManager(ErrorManager errorManager) {
        this.errorManager = errorManager;
    }

    /**
     * DealsWithEvents Interface implementation.
     */

    @Override
    public void setEventManager(EventManager eventManager) {
        this.eventManager = eventManager;
    }

    /**
     * DealsWithPluginDatabaseSystem Interface implementation.
     */

    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }



    /**
     * Service Interface implementation.
     */

    @Override
    public void start() throws CantStartPluginException {

        serviceStatus = ServiceStatus.STARTED;
       // locationServiceMonitorAgent = new LocationServiceMonitorAgent(errorManager, locationManager, pluginDatabaseSystem, uuid, plugins);
        try {
            locationServiceMonitorAgent.start();

        } catch (LocationServiceException ex) {
            //TODO: No estoy seguro en que momentos o nivel o de quien es la responsabilidad de generar estos reportes de la interface ErrorManager
            errorManager.reportUnexpectedPluginException(plugins, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, ex);
            CantStartPluginException ex2 = new CantStartPluginException(plugins);
            ex2.initCause(ex);
            throw ex2;

        }

    }

    @Override
    public void pause() {
        serviceStatus = ServiceStatus.PAUSED;
        locationServiceMonitorAgent.stop();
    }

    @Override
    public void resume() {
        try {
            start();
        } catch (CantStartPluginException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void stop() {
        serviceStatus = ServiceStatus.STOPPED;
        locationServiceMonitorAgent.stop();
    }

    @Override
    public ServiceStatus getStatus() {
        return serviceStatus;
    }

    /**
     * PlugIn Interface implementation.
     */

    @Override
    public void setId(UUID uuid) {
        this.uuid = uuid;
    }

}
