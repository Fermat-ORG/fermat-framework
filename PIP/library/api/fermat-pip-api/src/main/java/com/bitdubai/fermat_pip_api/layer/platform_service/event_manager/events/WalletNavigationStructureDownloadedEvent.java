package com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.events;

import java.util.UUID;

/**
 * Created by Matias Furszyfer on 2015.08.03..
 */

public class WalletNavigationStructureDownloadedEvent extends AbstractFermatEvent {

    private String xmlText;

    private UUID skinId;

    private String linkToRepo;

    private String filename;

    private String walletPublicKey;


    public WalletNavigationStructureDownloadedEvent(com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.enums.EventType eventType){
        super(eventType);
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

    public String getWalletPublicKey() {
        return walletPublicKey;
    }
    public void setWalletPublicKey(String walletPublicKey){
        this.walletPublicKey=walletPublicKey;
    }
}
