package com.bitdubai.fermat_pip_addon.layer.platform_service.platform_info.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.Addon;
import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.developer.LogManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
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
public class PlatformInfoPlatformServicePluginRootOld implements Addon, DealsWithErrors, DealsWithPlatformFileSystem, LogManagerForDevelopers,PlatformInfoManager,Service,Serializable {
    PlatformInfoPlatformService platformInfoPlatformService;

    /**
     * DealsWithErrors interface variable
     */
    private ErrorManager errorManager;

    /**
     * * DealsWithPlatformFileSystem interface member variables
     */
    PlatformFileSystem platformFileSystem;

    @Override
    public void setPlatformFileSystem(PlatformFileSystem platformFileSystem) {
        this.platformFileSystem = platformFileSystem;
    }

    ;
    /**
     * Service Interface member variables.
     */
    private ServiceStatus serviceStatus;
    private UUID uuid;

    private Addons plugins;

    // DealsWithlogManager interface member variable.
    private LogManager logManager = null;
    private static Map<String, LogLevel> newLoggingLevel = new HashMap<String, LogLevel>();

    /**
     * DealsWithErrors Interface implementation.
     */
    @Override
    public void setErrorManager(ErrorManager errorManager) {
        this.errorManager = errorManager;
    }



    public void start() throws CantStartPluginException {
        this.serviceStatus = ServiceStatus.STARTED;
    }

    /**
     * Service Interface implementation.
     */

    @Override
    public void pause() {
        this.serviceStatus = ServiceStatus.PAUSED;
    }

    @Override
    public void resume() {
        this.serviceStatus = ServiceStatus.STARTED;
    }

    @Override
    public void stop() {
        this.serviceStatus = ServiceStatus.STOPPED;
    }

    @Override
    public ServiceStatus getStatus() {
        return this.serviceStatus;
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
    public void setPlatformInfo(PlatformInfo platformInfo) throws CantLoadPlatformInformationException {
        if (platformInfoPlatformService == null) {
            platformInfoPlatformService = new PlatformInfoPlatformService();
            platformInfoPlatformService.setPlatformFileSystem(this.platformFileSystem);
        }
         platformInfoPlatformService.setPlatformInfo((com.bitdubai.fermat_pip_addon.layer.platform_service.platform_info.developer.bitdubai.version_1.structure.PlatformInfo) platformInfo);
    }

    /**
     * LogManagerForDevelopers Interface implementation.
     */

    @Override
    public List<String> getClassesFullPath() {
        List<String> returnedClasses = new ArrayList<String>();
        returnedClasses.add("com.bitdubai.fermat_pip_addon.layer.platform_service.platform_info.developer.bitdubai.version_1.PlatformInfoPlatformServicePluginRootOld");
        returnedClasses.add("com.bitdubai.fermat_pip_addon.layer.platform_service.platform_info.developer.bitdubai.version_1.structure.PlatformInfoPlatformService");
           /**
         * I return the values.
         */
        return returnedClasses;
    }

    @Override
    public void setLoggingLevelPerClass(Map<String, LogLevel> newLoggingLevel) {
        /**
         * I will check the current values and update the LogLevel in those which is different
         */
        for (Map.Entry<String, LogLevel> pluginPair : newLoggingLevel.entrySet()) {
            /**
             * if this path already exists in the Root.bewLoggingLevel I'll update the value, else, I will put as new
             */
            if (PlatformInfoPlatformServicePluginRootOld.newLoggingLevel.containsKey(pluginPair.getKey())) {
                PlatformInfoPlatformServicePluginRootOld.newLoggingLevel.remove(pluginPair.getKey());
                PlatformInfoPlatformServicePluginRootOld.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            } else {
                PlatformInfoPlatformServicePluginRootOld.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            }
        }
    }
}
