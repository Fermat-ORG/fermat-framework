package com.bitdubai.fermat_pip_core.layer.user.device_user;

import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.AddonReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_core_api.layer.all_definition.system.abstract_classes.AbstractAddonSubsystem;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantStartSubsystemException;
import com.bitdubai.fermat_pip_addon.layer.user.device_user.developer.bitdubai.DeveloperBitDubai;

/**
 * The class <code>com.bitdubai.fermat_pip_core.layer.platform_service.location_manager.DeviceUserSubsystem</code>
 * haves all the necessary business logic to start the Device User Subsystem of the PIP Platform.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 05/11/2015.
 */
public class DeviceUserSubsystem extends AbstractAddonSubsystem {

    public DeviceUserSubsystem() {
        super(new AddonReference(Addons.DEVICE_USER));
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
