package com.bitdubai.fermat_osa_android_core.layer.android.device_location;

import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractAddonSubsystem;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantStartSubsystemException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.AddonReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_osa_addon.layer.android.device_location.developer.bitdubai.DeveloperBitDubai;

/**
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 27/10/2015.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class DeviceLocationAddonSubsystem extends AbstractAddonSubsystem {

    public DeviceLocationAddonSubsystem() {
        super(new AddonReference(Addons.DEVICE_LOCATION));
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
