package com.bitdubai.fermat_pip_addon.layer.platform_service.platform_info.developer.bitdubai.version_1;

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
import com.bitdubai.fermat_api.layer.osa_android.file_system.PlatformFileSystem;
import com.bitdubai.fermat_pip_addon.layer.platform_service.platform_info.developer.bitdubai.version_1.structure.PlatformInfoPlatformService;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedAddonsExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.platform_info.interfaces.PlatformInfo;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.platform_info.interfaces.PlatformInfoManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.platform_info.interfaces.exceptions.CantLoadPlatformInformationException;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.platform_info.interfaces.exceptions.CantSetPlatformInformationException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by natalia on 29/07/15.
 * Modified by lnacosta (laion.cj91@gmail.com) on 26/10/2015.
 */
public class PlatformInfoPlatformServiceAddonRoot extends AbstractAddon implements PlatformInfoManager {

    private ErrorManager       errorManager      ;
    private PlatformFileSystem platformFileSystem;

    private PlatformInfoPlatformService platformInfoPlatformService;

    public PlatformInfoPlatformServiceAddonRoot() {
        super(new AddonVersionReference(new Version()));
    }

    public void start() throws CantStartPluginException {
        this.serviceStatus = ServiceStatus.STARTED;
    }

    @Override
    public PlatformInfo getPlatformInfo() throws CantLoadPlatformInformationException {

        try {

            if (platformInfoPlatformService == null)
                platformInfoPlatformService = new PlatformInfoPlatformService(this.platformFileSystem);

            return platformInfoPlatformService.getPlatformInfo();

        } catch(final CantLoadPlatformInformationException e) {

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

        } catch(final CantSetPlatformInformationException e) {

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

    @Override
    public List<AddonVersionReference> getNeededAddonReferences() {

        final List<AddonVersionReference> addonsNeeded = new ArrayList<>();

        addonsNeeded.add(new AddonVersionReference(Platforms.OPERATIVE_SYSTEM_API, Layers.ANDROID         , Addons.PLATFORM_FILE_SYSTEM, Developers.BITDUBAI, new Version()));
        addonsNeeded.add(new AddonVersionReference(Platforms.PLUG_INS_PLATFORM   , Layers.PLATFORM_SERVICE, Addons.ERROR_MANAGER       , Developers.BITDUBAI, new Version()));

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

        AbstractAddon platformFileSystem = this.getAddonReference(new AddonVersionReference(Platforms.OPERATIVE_SYSTEM_API, Layers.ANDROID         , Addons.PLATFORM_FILE_SYSTEM, Developers.BITDUBAI, new Version()));
        AbstractAddon errorManager       = this.getAddonReference(new AddonVersionReference(Platforms.PLUG_INS_PLATFORM   , Layers.PLATFORM_SERVICE, Addons.ERROR_MANAGER       , Developers.BITDUBAI, new Version()));

        if (platformFileSystem != null && platformFileSystem instanceof PlatformFileSystem) {

            this.platformFileSystem = (PlatformFileSystem) platformFileSystem;
        } else {

            throw new MissingReferencesException(
                    "platformFileSystem: "+ platformFileSystem,
                    "There is missing references for Platform Info Addon."
            );
        }

        if (errorManager != null && errorManager instanceof ErrorManager) {

            this.errorManager = (ErrorManager) errorManager;
        } else {

            throw new MissingReferencesException(
                    "errorManager: "+ errorManager,
                    "There is missing references for Platform Info Addon."
            );
        }

    }
}
