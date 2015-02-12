package com.bitdubai.wallet_platform_core.layer._6_world.crypto_index.developer.bitdubai.version_1;

import com.bitdubai.wallet_platform_api.Plugin;
import com.bitdubai.wallet_platform_api.Service;
import com.bitdubai.wallet_platform_api.layer._1_definition.enums.ServiceStatus;
import com.bitdubai.wallet_platform_api.layer._2_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.wallet_platform_api.layer._2_platform_service.error_manager.ErrorManager;
import com.bitdubai.wallet_platform_api.layer._2_platform_service.event_manager.DealsWithEvents;
import com.bitdubai.wallet_platform_api.layer._2_platform_service.event_manager.EventManager;
import com.bitdubai.wallet_platform_api.layer._3_os.File_System.DealsWithFileSystem;
import com.bitdubai.wallet_platform_api.layer._3_os.File_System.PluginFileSystem;
import com.bitdubai.wallet_platform_api.layer._8_communication.CommunicationChannel;

import java.util.UUID;

/**
 * Created by loui on 12/02/15.
 */
public class CryptoIndexPluginRoot implements Service, CommunicationChannel, DealsWithEvents, DealsWithErrors, DealsWithFileSystem, Plugin {
 
    //TODO: implement this methods correctly.

    @Override
    public void start() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void stop() {

    }
    
    @Override
    public ServiceStatus getStatus() {
        return null;
    }
    
    @Override
    public void setErrorManager(ErrorManager errorManager) {

    }

    @Override
    public void setEventManager(EventManager eventManager) {

    }

    @Override
    public void setPluginFileSystem(PluginFileSystem pluginFileSystem) {

    }

    @Override
    public void setId(UUID pluginId) {

    }

}
