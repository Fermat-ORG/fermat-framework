package com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.events;

import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.enums.EventType;

/**
 * Created by Nerio on 08/10/15.
 */
public class AssetUserActorConnectionCancelledEvent extends AbstractFermatEvent {

    String assetUserLinkedIdentiyPublicKey;
    String assetUserToAddPublicKey;

    public AssetUserActorConnectionCancelledEvent(EventType eventType){
        super(eventType);
    }

    public void setAssetUserLinkedIdentiyPublicKey(String assetUserLinkedIdentiyPublicKey) {
        this.assetUserLinkedIdentiyPublicKey = assetUserLinkedIdentiyPublicKey;
    }

    public void setAssetUserToAddPublicKey(String assetUserToAddPublicKey) {
        this.assetUserToAddPublicKey = assetUserToAddPublicKey;
    }

    public String getAssetUserLinkedIdentiyPublicKey() {
        return assetUserLinkedIdentiyPublicKey;
    }

    public String getAssetUserToAddPublicKey() {
        return assetUserToAddPublicKey;
    }


}
