// IServerBrokerService.aidl
package com.bitdubai.android_core.app.common.version_1.communication.server_system_broker.aidl;

// Declare any non-default types here with import statements
import com.bitdubai.android_core.app.common.version_1.communication.server_system_broker.structure.FermatModuleObjectWrapper;
import com.bitdubai.android_core.app.common.version_1.communication.server_system_broker.structure.ModuleObjectParameterWrapper;

interface IPlatformService {


    FermatModuleObjectWrapper invoqueModuleMethod(
            in String clientKey,
            in String dataId,
            in String platformCode,
            in String layerCode,
            in String pluginsCode,
            in String developerCode,
            in String version,
            in String method,
            in ModuleObjectParameterWrapper[] parameters
            );

    FermatModuleObjectWrapper invoqueModuleLargeDataMethod(
          in String clientKey,
          in String dataId,
          in String platformCode,
          in String layerCode,
          in String pluginsCode,
          in String developerCode,
          in String version,
          in String method,
          in ModuleObjectParameterWrapper[] parameters
          );

    // This method will be only used for runtime
    FermatModuleObjectWrapper invoqueRuntimeMethod(
                in String clientKey,
                in String dataId,
                in String platformCode,
                in String layerCode,
                in String pluginsCode,
                in String developerCode,
                in String version,
                in String method,
                in ModuleObjectParameterWrapper[] parameters
                );

    String register();

    boolean isFermatSystemRunning();
}
