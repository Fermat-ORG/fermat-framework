package com.bitdubai.fermat_dap_android_sub_app_asset_issuer_community_bitdubai.models;

import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.AssetIssuerActorRecord;

/**
 * Actor Model
 */
public class ActorIssuer {

    public boolean selected;
    private AssetIssuerActorRecord record;

    public ActorIssuer() {
        super();
    }

    public ActorIssuer(AssetIssuerActorRecord record) {
        this.record = record;
    }

    public AssetIssuerActorRecord getRecord() {
        return record;
    }

    public void setRecord(AssetIssuerActorRecord record) {
        this.record = record;
    }
}
