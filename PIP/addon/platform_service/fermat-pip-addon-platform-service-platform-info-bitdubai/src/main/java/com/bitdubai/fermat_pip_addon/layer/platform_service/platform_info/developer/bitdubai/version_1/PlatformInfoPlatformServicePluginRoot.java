package com.bitdubai.fermat_pip_addon.layer.platform_service.platform_info.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.layer.all_definition.common.abstract_classes.AbstractAddon;
import com.bitdubai.fermat_api.layer.all_definition.common.exceptions.CantGetFeatureForDevelopersException;
import com.bitdubai.fermat_api.layer.all_definition.common.exceptions.MissingReferencesException;
import com.bitdubai.fermat_api.layer.all_definition.common.interfaces.FeatureForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.common.utils.AddonVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.common.utils.DevelopersUtilReference;
import com.bitdubai.fermat_api.layer.all_definition.developer.LogManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.osa_android.file_system.DealsWithPlatformFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PlatformFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_pip_addon.layer.platform_service.platform_info.developer.bitdubai.version_1.structure.PlatformInfoPlatformService;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.platform_info.interfaces.PlatformInfo;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.platform_info.interfaces.PlatformInfoManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.platform_info.interfaces.exceptions.CantLoadPlatformInformationException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by natalia on 29/07/15.
 */
public class PlatformInfoPlatformServicePluginRoot extends AbstractAddon implements DealsWithPlatformFileSystem, PlatformInfoManager {

    private ErrorManager       errorManager      ;
    private PlatformFileSystem platformFileSystem;

    private PlatformInfoPlatformService platformInfoPlatformService;

    public PlatformInfoPlatformServicePluginRoot() {
        super(new AddonVersionReference(new Version()));
    }

    public void start() throws CantStartPluginException {
        this.serviceStatus = ServiceStatus.STARTED;
    }

    /**
     * PlaformInfoManager Interface implementation.
     */
    @Override
    public PlatformInfo getPlatformInfo() throws CantLoadPlatformInformationException {
        if (platformInfoPlatformService == null) {
            platformInfoPlatformService = new PlatformInfoPlatformService();
            platformInfoPlatformService.setPlatformFileSystem(this.platformFileSystem);
        }
        return platformInfoPlatformService.getPlatformInfo();
    }

    /**
     * persist the changes sent
     * @param platformInfo
     * @throws CantLoadPlatformInformationException
     */
    @Override
    public void setPlatformInfo(final PlatformInfo platformInfo) throws CantLoadPlatformInformationException {
        if (platformInfoPlatformService == null) {
            platformInfoPlatformService = new PlatformInfoPlatformService();
            platformInfoPlatformService.setPlatformFileSystem(this.platformFileSystem);
        }
         platformInfoPlatformService.setPlatformInfo((com.bitdubai.fermat_pip_addon.layer.platform_service.platform_info.developer.bitdubai.version_1.structure.PlatformInfo) platformInfo);
    }

    @Override
    public List<AddonVersionReference> getNeededAddonReferences() {
        return null;
    }

    @Override
    public List<DevelopersUtilReference> getAvailableDeveloperUtils() {
        return null;
    }

    @Override
    public FeatureForDevelopers getFeatureForDevelopers(DevelopersUtilReference developersUtilReference) throws CantGetFeatureForDevelopersException {
        return null;
    }

    @Override
    protected void validateAndAssignReferences() throws MissingReferencesException {

    }

    @Override
    public void setPlatformFileSystem(PlatformFileSystem platformFileSystem) {

    }
}
