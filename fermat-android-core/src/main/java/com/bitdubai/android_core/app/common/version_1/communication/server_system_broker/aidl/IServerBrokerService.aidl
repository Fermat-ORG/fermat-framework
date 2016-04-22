// IServerBrokerService.aidl
package com.bitdubai.android_core.app.common.version_1.communication.server_system_broker.aidl;

// Declare any non-default types here with import statements
import com.bitdubai.android_core.app.common.version_1.communication.structure.AIDLObject;

interface IServerBrokerService {


    AIDLObject invoqueModuleMethod(
            in String platformCode,
            in String layerCode,
            in String pluginsCode,
            in String developerCode,
            in String version,
            in String method,
            in AIDLObject[] parameters
            );
}
