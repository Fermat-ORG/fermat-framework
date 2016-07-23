package com.bitdubai.fermat_osa_addon.layer.linux.file_system.developer.bitdubai;

import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractAddonDeveloper;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantRegisterVersionException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantStartAddonDeveloperException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.AddonDeveloperReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Developers;
import com.bitdubai.fermat_osa_addon.layer.linux.file_system.developer.bitdubai.version_1.PlatformFileSystemLinuxAddonRoot;

/**
 * The class <code>com.bitdubai.fermat_osa_addon.layer.android.file_system.developer.bitdubai.DeveloperBitDubai</code>
 * Haves the logic of instantiation of all versions of Platform File System Android Addon.
 * <p/>
 * Here we can choose between the different versions of the Platform File System Addon.
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 08/12/2015.
 */
public class PlatformFileSystemDeveloperBitDubai extends AbstractAddonDeveloper {

    public PlatformFileSystemDeveloperBitDubai() {
        super(new AddonDeveloperReference(Developers.BITDUBAI));
    }

    @Override
    public void start() throws CantStartAddonDeveloperException {
        try {

            System.out.println("PlatformFileSystemDeveloperBitDubai - start()");

            this.registerVersion(new PlatformFileSystemLinuxAddonRoot());

        } catch (CantRegisterVersionException e) {

            throw new CantStartAddonDeveloperException(e, "", "Error registering addon versions for the developer.");
        }
    }
}
