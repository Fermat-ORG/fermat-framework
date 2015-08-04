package com.bitdubai.fermat_dmp_plugin.layer.engine.wallet_runtime.developer.bitdubai.version_1.event_handlers;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.event.PlatformEvent;
import com.bitdubai.fermat_api.layer.dmp_engine.wallet_runtime.WalletRuntimeManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.EventHandler;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.events.WalletNavigationStructureDownloadedEvent;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.events.WalletResourcesInstalledEvent;

import java.util.UUID;

/**
 * Created by Matias Furszyfer on 2015.08.03..
 */
public class WalletNavigationStructureDownloadedHandler implements EventHandler {

    WalletRuntimeManager walletRuntimeManager;

    public void setWalletRuntimeManager(WalletRuntimeManager walletRuntimeManager) {
        this.walletRuntimeManager = walletRuntimeManager;
    }


    @Override
    public void handleEvent(PlatformEvent platformEvent) throws FermatException {
        WalletNavigationStructureDownloadedEvent walletNavigationStructureDownloadedEvent =(WalletNavigationStructureDownloadedEvent)platformEvent;
        String xmlText = walletNavigationStructureDownloadedEvent.getXmlText();
        String link = walletNavigationStructureDownloadedEvent.getLinkToRepo();
        UUID skinId = walletNavigationStructureDownloadedEvent.getSkinId();
        String filename = walletNavigationStructureDownloadedEvent.getFilename();


        if (((Service) this.walletRuntimeManager).getStatus() == ServiceStatus.STARTED) {


                this.walletRuntimeManager.recordNavigationStructure(xmlText,link,filename,skinId);


        }
    }
}
