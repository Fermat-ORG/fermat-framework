package com.bitdubai.fermat_osa_addon.layer.android.device_conectivity.developer.bitdubai;

import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractAddonDeveloper;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantRegisterVersionException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantStartAddonDeveloperException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.AddonDeveloperReference;
import com.bitdubai.fermat_osa_addon.layer.android.device_conectivity.developer.bitdubai.version_1.DeviceConnectivityAddonRoot;

/**
 * Created by Natalia on 13/05/2015.
 */
public class DeveloperBitDubai extends AbstractAddonDeveloper {


    /**
     * Constructor with params.
     * assigns a developer to the addon developer class
     *
     * @param addonDeveloperReference
     */
    public DeveloperBitDubai(AddonDeveloperReference addonDeveloperReference) {
        super(addonDeveloperReference);
    }

    @Override
    public void start() throws CantStartAddonDeveloperException {
        try {
            registerVersion(new DeviceConnectivityAddonRoot());
        } catch (CantRegisterVersionException e) {
            e.printStackTrace();
        }
    }

}