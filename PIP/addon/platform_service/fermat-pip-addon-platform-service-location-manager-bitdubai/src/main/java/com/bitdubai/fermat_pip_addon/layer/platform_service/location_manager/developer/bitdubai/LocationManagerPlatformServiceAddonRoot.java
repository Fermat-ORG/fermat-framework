package com.bitdubai.fermat_pip_addon.layer.platform_service.location_manager.developer.bitdubai;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractAddon;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.AddonVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PlatformDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.location_system.LocationManager;
import com.bitdubai.fermat_pip_addon.layer.platform_service.location_manager.developer.bitdubai.version_1.exception.LocationServiceException;
import com.bitdubai.fermat_pip_addon.layer.platform_service.location_manager.developer.bitdubai.version_1.structure.LocationServiceMonitorAgent;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedAddonsExceptionSeverity;

/**
 * This plugin is the responsible to keep updated the location values of the device to deliver this necessary data to other plugins,.
 *
 * Created by lnacosta (laion.cj91@gmail.com) on 26/10/2015.
 */
public class LocationManagerPlatformServiceAddonRoot extends AbstractAddon {

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM   , layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER       )
    private ErrorManager errorManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLATFORM_DATABASE_SYSTEM)
    private PlatformDatabaseSystem platformDatabaseSystem;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.DEVICE_LOCATION)
    private LocationManager locationManager            ;

    private LocationServiceMonitorAgent locationServiceMonitorAgent;


    public LocationManagerPlatformServiceAddonRoot() {
        super(new AddonVersionReference(new Version()));
    }

    @Override
    public FermatManager getManager() {
        // TODO THE ADD-ON MUST RETURN SOMETHING....
        return null;
    }

    /**
     * Service Interface implementation.
     */
    @Override
    public void start() throws CantStartPluginException {

        try {

            locationServiceMonitorAgent = new LocationServiceMonitorAgent(errorManager, locationManager, platformDatabaseSystem);

            locationServiceMonitorAgent.start();

        } catch (final LocationServiceException ex) {

            errorManager.reportUnexpectedAddonsException(Addons.LOCATION_MANAGER, UnexpectedAddonsExceptionSeverity.DISABLES_THIS_ADDONS, ex);
        }

        this.serviceStatus = ServiceStatus.STARTED;

    }

    @Override
    public void pause() {
        locationServiceMonitorAgent.stop();
        serviceStatus = ServiceStatus.PAUSED;
    }

    @Override
    public void resume() {

        try {
            locationServiceMonitorAgent.start();

            serviceStatus = ServiceStatus.STARTED;

        } catch (final LocationServiceException ex) {

            errorManager.reportUnexpectedAddonsException(Addons.LOCATION_MANAGER, UnexpectedAddonsExceptionSeverity.DISABLES_THIS_ADDONS, ex);

        }
    }

    @Override
    public void stop() {
        locationServiceMonitorAgent.stop();
        serviceStatus = ServiceStatus.STOPPED;
    }
}
