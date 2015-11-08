package com.bitdubai.fermat_pip_addon.layer.platform_service.platform_info.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractAddon;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.AddonVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PlatformFileSystem;
import com.bitdubai.fermat_pip_addon.layer.platform_service.platform_info.developer.bitdubai.version_1.structure.PlatformInfoPlatformService;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedAddonsExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.platform_info.interfaces.PlatformInfo;
import com.bitdubai.fermat_pip_api.layer.platform_service.platform_info.interfaces.PlatformInfoManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.platform_info.interfaces.exceptions.CantLoadPlatformInformationException;
import com.bitdubai.fermat_pip_api.layer.platform_service.platform_info.interfaces.exceptions.CantSetPlatformInformationException;

/**
 * Created by natalia on 29/07/15.
 * Modified by lnacosta (laion.cj91@gmail.com) on 26/10/2015.
 */
public class PlatformInfoPlatformServiceAddonRoot extends AbstractAddon implements PlatformInfoManager {

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM   , layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER       )
    private ErrorManager errorManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM          , addon = Addons.PLATFORM_FILE_SYSTEM)
    private PlatformFileSystem platformFileSystem;

    /**
     * PlatformInfoManager interface member variables.
     */
    private PlatformInfoPlatformService platformInfoPlatformService;

    /**
     * Constructor without parameters.
     */
    public PlatformInfoPlatformServiceAddonRoot() {
        super(new AddonVersionReference(new Version()));
    }

    /**
     * PlatformInfoManager interface implementation.
     */

    @Override
    public PlatformInfo getPlatformInfo() throws CantLoadPlatformInformationException {

        try {

            if (platformInfoPlatformService == null)
                platformInfoPlatformService = new PlatformInfoPlatformService(this.platformFileSystem);

            return platformInfoPlatformService.getPlatformInfo();

        } catch (final CantLoadPlatformInformationException e) {

            reportUnexpectedError(e);
            throw e;
        } catch (final Exception e) {

            reportUnexpectedError(e);
            throw new CantLoadPlatformInformationException(
                    e,
                    "",
                    "Unhandled error trying to get platform info."
            );
        }
    }

    @Override
    public void setPlatformInfo(final PlatformInfo platformInfo) throws CantSetPlatformInformationException {

        try {

            if (platformInfoPlatformService == null)
                platformInfoPlatformService = new PlatformInfoPlatformService(this.platformFileSystem);

            platformInfoPlatformService.setPlatformInfo(platformInfo);

        } catch (final CantSetPlatformInformationException e) {

            reportUnexpectedError(e);
            throw e;
        } catch (final Exception e) {

            reportUnexpectedError(e);
            throw new CantSetPlatformInformationException(
                    e,
                    "",
                    "Unhandled error trying to set platform info."
            );
        }
    }

    private void reportUnexpectedError(Exception e) {
        errorManager.reportUnexpectedAddonsException(Addons.PLATFORM_INFO, UnexpectedAddonsExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_ADDONS, e);
    }
}