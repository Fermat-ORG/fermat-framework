package com.bitdubai.fermat_pip_addon.layer.platform_service.platform_info.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.AddonVersionReference;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PlatformFileSystem;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedAddonsExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.platform_info.interfaces.PlatformInfo;
import com.bitdubai.fermat_pip_api.layer.platform_service.platform_info.interfaces.PlatformInfoManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.platform_info.exceptions.CantLoadPlatformInformationException;
import com.bitdubai.fermat_pip_api.layer.platform_service.platform_info.exceptions.CantSetPlatformInformationException;

/**
 * The class <code>com.bitdubai.fermat_pip_addon.layer.platform_service.platform_info.developer.bitdubai.version_1.structure.PlatformInfoPlatformServiceManager</code>
 * implements platform info manager interface and contains all the methods that provides platform information.
 * <p>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 23/11/2015.
 */
public final class PlatformInfoPlatformServiceManager implements PlatformInfoManager {

    private final AddonVersionReference addonVersionReference;
    private final ErrorManager          errorManager         ;
    private final PlatformFileSystem    platformFileSystem   ;


    /**
     * PlatformInfoManager interface member variables.
     */
    private PlatformInfoPlatformService platformInfoPlatformService;

    public PlatformInfoPlatformServiceManager(final AddonVersionReference addonVersionReference,
                                              final ErrorManager          errorManager         ,
                                              final PlatformFileSystem    platformFileSystem   ) {

        this.addonVersionReference = addonVersionReference;
        this.errorManager          = errorManager         ;
        this.platformFileSystem    = platformFileSystem   ;
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

    private void reportUnexpectedError(final Exception e) {
        errorManager.reportUnexpectedAddonsException(addonVersionReference, UnexpectedAddonsExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_ADDONS, e);
    }

}
