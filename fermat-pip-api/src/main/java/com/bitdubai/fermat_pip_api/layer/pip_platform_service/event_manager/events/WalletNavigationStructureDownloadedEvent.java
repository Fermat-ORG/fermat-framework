package com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.events;

import com.bitdubai.fermat_api.layer.all_definition.event.EventType;

import java.util.UUID;

/**
 * Created by Matias Furszyfer on 2015.08.03..
 */

public class WalletNavigationStructureDownloadedEvent extends AbstractPlatformEvent{

    private String xmlText;

    private UUID skinId;

    private String linkToRepo;

    private String filename;


    public WalletNavigationStructureDownloadedEvent(String xmlText,String linkToRepo,String filename,UUID skinId) {
        super(EventType.WALLET_RESOURCES_NAVIGATION_STRUCTURE_DOWNLOADED);
        this.xmlText=xmlText;
        this.skinId=skinId;
        this.linkToRepo=linkToRepo;
        this.filename=filename;
    }

    public UUID getSkinId() {
        return skinId;
    }


    public String getXmlText() {
        return xmlText;
    }

    public String getLinkToRepo() {
        return linkToRepo;
    }

    public String getFilename() {
        return filename;
    }
}
