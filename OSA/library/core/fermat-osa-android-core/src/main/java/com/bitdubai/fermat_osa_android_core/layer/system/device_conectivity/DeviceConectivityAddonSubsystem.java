package com.bitdubai.fermat_osa_android_core.layer.system.device_conectivity;

import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.AddonDeveloperReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.AddonReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Developers;
import com.bitdubai.fermat_core_api.layer.all_definition.system.abstract_classes.AbstractAddonSubsystem;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantStartSubsystemException;
import com.bitdubai.fermat_osa_addon.layer.android.device_conectivity.developer.bitdubai.DeveloperBitDubai;

/**
 * @version 1.0
 * @since Java JDK 1.7
 */
public class DeviceConectivityAddonSubsystem extends AbstractAddonSubsystem {

    public DeviceConectivityAddonSubsystem() {
        super(new AddonReference(Addons.DEVICE_CONNECTIVITY));
    }

    @Override
    public void start() throws CantStartSubsystemException {
        try {
            registerDeveloper(new DeveloperBitDubai(new AddonDeveloperReference(Developers.BITDUBAI)));
        } catch (Exception e) {
            System.err.println(new StringBuilder().append("Exception: ").append(e.getMessage()).toString());
            throw new CantStartSubsystemException(e, null, null);
        }
    }
}
