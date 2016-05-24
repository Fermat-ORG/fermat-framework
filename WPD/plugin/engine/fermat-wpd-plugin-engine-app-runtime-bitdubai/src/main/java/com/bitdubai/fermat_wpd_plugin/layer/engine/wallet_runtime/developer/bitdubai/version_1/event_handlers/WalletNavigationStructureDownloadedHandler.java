package com.bitdubai.fermat_wpd_plugin.layer.engine.wallet_runtime.developer.bitdubai.version_1.event_handlers;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_wpd_api.layer.wpd_engine.wallet_runtime.interfaces.WalletRuntimeManager;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_wpd_api.all_definition.events.WalletNavigationStructureDownloadedEvent;

import java.util.UUID;

/**
 * Created by Matias Furszyfer on 2015.08.03..
 */
public class WalletNavigationStructureDownloadedHandler implements FermatEventHandler {

    private final WalletRuntimeManager walletRuntimeManager;

    public WalletNavigationStructureDownloadedHandler(final WalletRuntimeManager runtimeManager){
        this.walletRuntimeManager = runtimeManager;
    }


    @Override
    public void handleEvent(FermatEvent fermatEvent) throws FermatException {
        WalletNavigationStructureDownloadedEvent walletNavigationStructureDownloadedEvent =(WalletNavigationStructureDownloadedEvent) fermatEvent;
        String xmlText = walletNavigationStructureDownloadedEvent.getXmlText();
        String link = walletNavigationStructureDownloadedEvent.getLinkToRepo();
        UUID skinId = walletNavigationStructureDownloadedEvent.getSkinId();
        String filename = walletNavigationStructureDownloadedEvent.getFilename();
        String walletPubicKey = walletNavigationStructureDownloadedEvent.getWalletPublicKey();
        System.out.println("JORGEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE HOla");

        //if (((Service) this.walletRuntimeManager).getStatus() == ServiceStatus.STARTED) {

        System.out.println("JORGEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE HOla111");
        this.walletRuntimeManager.recordNavigationStructure(xmlText,link,filename,skinId,walletPubicKey);


        //}
    }
}
