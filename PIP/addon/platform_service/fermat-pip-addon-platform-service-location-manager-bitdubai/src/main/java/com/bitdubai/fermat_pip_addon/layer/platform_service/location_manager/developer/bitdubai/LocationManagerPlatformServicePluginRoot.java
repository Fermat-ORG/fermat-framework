package com.bitdubai.fermat_pip_addon.layer.platform_service.location_manager.developer.bitdubai;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.layer.all_definition.common.abstract_classes.AbstractAddon;
import com.bitdubai.fermat_api.layer.all_definition.common.exceptions.CantGetFeatureForDevelopersException;
import com.bitdubai.fermat_api.layer.all_definition.common.exceptions.MissingReferencesException;
import com.bitdubai.fermat_api.layer.all_definition.common.interfaces.FeatureForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.common.utils.AddonVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.common.utils.DevelopersUtilReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Developers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PlatformDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.location_system.LocationManager;
import com.bitdubai.fermat_pip_addon.layer.platform_service.location_manager.developer.bitdubai.version_1.exception.LocationServiceException;
import com.bitdubai.fermat_pip_addon.layer.platform_service.location_manager.developer.bitdubai.version_1.structure.LocationServiceMonitorAgent;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedAddonsExceptionSeverity;

import java.util.ArrayList;
import java.util.List;

/**
 * This plugin is the responsible to keep updated the location values of the device to deliver this necessary data to other plugins,.
 *
 * Created by lnacosta (laion.cj91@gmail.com) on 26/10/2015.
 */
public class LocationManagerPlatformServicePluginRoot extends AbstractAddon {

    private ErrorManager                errorManager               ;
    private LocationManager             locationManager            ;
    private PlatformDatabaseSystem      platformDatabaseSystem     ;

    private LocationServiceMonitorAgent locationServiceMonitorAgent;

    public LocationManagerPlatformServicePluginRoot() {
        super(new AddonVersionReference(new Version()));
    }

    /**
     * Service Interface implementation.
     */
    @Override
    public void start() throws CantStartPluginException {

        try {

            validateAndAssignReferences();

            locationServiceMonitorAgent = new LocationServiceMonitorAgent(errorManager, locationManager, platformDatabaseSystem);

            locationServiceMonitorAgent.start();

        } catch (final LocationServiceException ex) {

            errorManager.reportUnexpectedAddonsException(Addons.LOCATION_MANAGER, UnexpectedAddonsExceptionSeverity.DISABLES_THIS_ADDONS, ex);

            throw new CantStartPluginException(ex, "", "Error trying to start Location Manager.");

        } catch (final MissingReferencesException e) {

            throw new CantStartPluginException(
                    "",
                    "There's missing references for plugin location manager platform service pip."
            );
        }

        this.serviceStatus = ServiceStatus.STARTED;

    }

    @Override
    public void pause() {
        locationServiceMonitorAgent.stop();
        serviceStatus = ServiceStatus.PAUSED;
    }

    @Override
    public void stop() {
        locationServiceMonitorAgent.stop();
        serviceStatus = ServiceStatus.STOPPED;
    }

    @Override
    public List<AddonVersionReference> getNeededAddonReferences() {

        final List<AddonVersionReference> addonsNeeded = new ArrayList<>();

        addonsNeeded.add(new AddonVersionReference(Platforms.PLUG_INS_PLATFORM, Layers.PLATFORM_SERVICE, Addons.ERROR_MANAGER, Developers.BITDUBAI, new Version()));

        return addonsNeeded;
    }

    @Override
    public List<DevelopersUtilReference> getAvailableDeveloperUtils() {
        return new ArrayList<>();
    }

    @Override
    public FeatureForDevelopers getFeatureForDevelopers(final DevelopersUtilReference developersUtilReference) throws CantGetFeatureForDevelopersException {
        return null;
    }

    @Override
    protected void validateAndAssignReferences() throws MissingReferencesException {

        this.errorManager = (ErrorManager) this.getAddonReference(new AddonVersionReference(Platforms.PLUG_INS_PLATFORM, Layers.PLATFORM_SERVICE, Addons.ERROR_MANAGER, Developers.BITDUBAI, new Version()));

        if (errorManager == null) {
            throw new MissingReferencesException(
                    "errorManager: "+ errorManager,
                    "There is missing references for Event Manager Addon."
            );
        }

    }
}
