package com.bitdubai.fermat_pip_addon.layer.platform_service.location_subsystem.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.osa_android.location_system.DealsWithDeviceLocation;
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_api.layer.osa_android.location_system.LocationManager;
import com.bitdubai.fermat_pip_addon.layer.platform_service.location_subsystem.developer.bitdubai.version_1.exception.CantGetDeviceLocationException;
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateTableException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_addon.layer.platform_service.location_subsystem.developer.bitdubai.version_1.exception.LocationServiceException;
import com.bitdubai.fermat_pip_addon.layer.platform_service.location_subsystem.developer.bitdubai.version_1.interfaces.LocationServiceAgent;

import java.util.UUID;

/**
 * This class, once it is started, request the location (with certain frequency) from the device and store (if the request was successful) the information.
 * Created by firuzzz on 5/9/15.
 */
public class LocationServiceMonitorAgent implements DealsWithErrors,DealsWithDeviceLocation, DealsWithPluginDatabaseSystem, LocationServiceAgent, Plugin { //DealsWithDeviceLocation

    private LocationManager locationManager;
    private ErrorManager errorManager;
    private PluginDatabaseSystem pluginDatabaseSystem;
    private UUID pluginId;
    private Thread locationCollectorThread;
    /**
     * The time (in milliseconds) the thread is going to sleep after requesting the device location.
     */
    private int requestingLocationFrequency = 60_000;
    private LocationServiceRegistry registry;
    //TODO: Falta Plugins ENUM para el módulo
    private Plugins plugins;


    public LocationServiceMonitorAgent(ErrorManager errorManager, PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId, Plugins plugins) {
        this.errorManager = errorManager;
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
        this.plugins = plugins;
    }

    /**
     * DealsWithDeviceLocation Interface implementation.
     */
    @Override
    public void setLocationManager(LocationManager locationManager){
        this.locationManager = locationManager;
    }

    /**
     * The thread requests the device's location according to the specified frequency ({@link #requestingLocationFrequency})
     * <br>If an instance of  {@link } is successfully retrieved, it is stored
     */
    private void initLocationCollectorThread() {
        locationCollectorThread = new Thread(new Runnable() {
            @Override
            public void run() {
                /**
                 * Local flag to exist of the infinite loop
                 */
                boolean keepWorking = true;
                while (keepWorking) {
                    synchronized (this) {
                        try {
                            doTheMainTask();
                        } catch (Exception ex) {
                            errorManager.reportUnexpectedPluginException(plugins, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, ex);
                        }
                        try {
                            Thread.sleep(requestingLocationFrequency);
                        } catch (InterruptedException e) {
                            cleanResources();
                            keepWorking = false;
                        }
                    }
                }
            }
        });
    }

    private void doTheMainTask() throws CantGetDeviceLocationException, CantInsertRecordException {
        Location location = null;
        try {
            location = locationManager.getLocation();
        } catch (com.bitdubai.fermat_api.layer.osa_android.location_system.exceptions.CantGetDeviceLocationException e) {
            errorManager.reportUnexpectedPluginException(plugins, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }

        if (location != null) {
            registry.create(location);
        }
    }

    private void cleanResources() {
        registry = null;
    }


    @Override
    public void start() throws LocationServiceException {
        if (locationCollectorThread != null && locationCollectorThread.isAlive()) {
            return;
        }
        registry = new LocationServiceRegistry(pluginId, pluginId);
        registry.setPluginDatabaseSystem(pluginDatabaseSystem);
        try {
            registry.initialize();
        } catch (CantCreateDatabaseException | CantOpenDatabaseException | CantCreateTableException ex) {
            LocationServiceException ex2 = new LocationServiceException("Error en start");
            ex2.initCause(ex);
            throw ex2;
        }
        initLocationCollectorThread();
        locationCollectorThread.start();
    }

    @Override
    public void stop() {
        if (!locationCollectorThread.isInterrupted()) {
            locationCollectorThread.interrupt();
            locationCollectorThread = null;
        }
    }



    @Override
    public void setErrorManager(ErrorManager errorManager) {
        this.errorManager = errorManager;
    }

    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }


    @Override
    public void setId(UUID pluginId) {
        this.pluginId = pluginId;
    }
}
