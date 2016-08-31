package com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.event_handler;

import java.util.UUID;

/**
 * Created by Gabriel Araujo on 24/08/16.
 */
public class EventRecord {

    private UUID eventPackageId;

    private String senderPublicKey;

    private String destinationPublicKey;

    public EventRecord() {
    }

    public EventRecord(UUID eventPackageId, String senderPublicKey, String destinationPublicKey) {
        this.eventPackageId = eventPackageId;
        this.senderPublicKey = senderPublicKey;
        this.destinationPublicKey = destinationPublicKey;
    }

    public UUID getEventPackageId() {
        return eventPackageId;
    }

    public void setEventPackageId(UUID eventPackageId) {
        this.eventPackageId = eventPackageId;
    }

    public String getSenderPublicKey() {
        return senderPublicKey;
    }

    public void setSenderPublicKey(String senderPublicKey) {
        this.senderPublicKey = senderPublicKey;
    }

    public String getDestinationPublicKey() {
        return destinationPublicKey;
    }

    public void setDestinationPublicKey(String destinationPublicKey) {
        this.destinationPublicKey = destinationPublicKey;
    }
}
