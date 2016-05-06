package com.bitdubai.fermat_pip_addon.layer.platform_service.location_manager.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PlatformDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_api.layer.osa_android.location_system.LocationManager;
import com.bitdubai.fermat_pip_addon.layer.platform_service.location_manager.developer.bitdubai.version_1.exception.CantGetDeviceLocationException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateTableException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedAddonsExceptionSeverity;
import com.bitdubai.fermat_pip_addon.layer.platform_service.location_manager.developer.bitdubai.version_1.exception.LocationServiceException;
import com.bitdubai.fermat_pip_addon.layer.platform_service.location_manager.developer.bitdubai.version_1.interfaces.LocationServiceAgent;

/**
 * This class, once it is started, request the location (with certain frequency) from the device and store (if the request was successful) the information.
 * Created by firuzzz on 5/9/15.
 * Updated by lnacosta (laion.cj91@gmail.com) on 26/10/2015.
 */
public class LocationServiceMonitorAgent implements LocationServiceAgent {

    private ErrorManager           errorManager          ;
    private LocationManager        locationManager       ;
    private PlatformDatabaseSystem platformDatabaseSystem;

    private Thread locationCollectorThread;

    /**
     * The time (in milliseconds) the thread is going to sleep after requesting the device location.
     */
    private int requestingLocationFrequency = 60_000;
    private LocationServiceRegistry registry;

    public LocationServiceMonitorAgent(final ErrorManager           errorManager          ,
                                       final LocationManager        locationManager       ,
                                       final PlatformDatabaseSystem platformDatabaseSystem) {

        this.errorManager           = errorManager          ;
        this.locationManager        = locationManager       ;
        this.platformDatabaseSystem = platformDatabaseSystem;
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
                            errorManager.reportUnexpectedAddonsException(Addons.LOCATION_MANAGER, UnexpectedAddonsExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_ADDONS, ex);
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
            errorManager.reportUnexpectedAddonsException(Addons.LOCATION_MANAGER, UnexpectedAddonsExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_ADDONS, e);
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
        registry = new LocationServiceRegistry(platformDatabaseSystem);
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
}
