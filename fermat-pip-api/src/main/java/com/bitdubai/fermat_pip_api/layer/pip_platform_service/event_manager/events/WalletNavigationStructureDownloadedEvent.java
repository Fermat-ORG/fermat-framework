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


    public WalletNavigationStructureDownloadedEvent() {
        super(EventType.WALLET_RESOURCES_NAVIGATION_STRUCTURE_DOWNLOADED);
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

    public void setXmlText(String xmlText) {
        this.xmlText = xmlText;
    }

    public void setSkinId(UUID skinId) {
        this.skinId = skinId;
    }

    public void setLinkToRepo(String linkToRepo) {
        this.linkToRepo = linkToRepo;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
