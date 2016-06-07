package com.bitdubai.fermat_pip_core.layer.platform_service.location_manager;

import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.AddonReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_core_api.layer.all_definition.system.abstract_classes.AbstractAddonSubsystem;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantStartSubsystemException;
import com.bitdubai.fermat_pip_addon.layer.platform_service.location_manager.developer.DeveloperBitDubai;

/**
 * The class <code>com.bitdubai.fermat_pip_core.layer.platform_service.location_manager.LocationManagerSubsystem</code>
 * haves all the necessary business logic to start the Location Manager Subsystem of the PIP Platform.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 26/10/2015.
 */
public class LocationManagerSubsystem extends AbstractAddonSubsystem {

    public LocationManagerSubsystem() {
        super(new AddonReference(Addons.LOCATION_MANAGER));
    }

    @Override
    public void start() throws CantStartSubsystemException {

        try {

            registerDeveloper(new DeveloperBitDubai());

        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
            throw new CantStartSubsystemException(e, null, null);
        }
    }
}
