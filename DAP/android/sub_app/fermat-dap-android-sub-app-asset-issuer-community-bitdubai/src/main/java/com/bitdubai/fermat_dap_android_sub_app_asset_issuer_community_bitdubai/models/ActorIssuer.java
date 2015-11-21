package com.bitdubai.fermat_dap_android_sub_app_asset_issuer_community_bitdubai.models;

import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.AssetIssuerActorRecord;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuer;

/**
 * Actor Model
 */
public class ActorIssuer extends AssetIssuerActorRecord
        implements ActorAssetIssuer {

    public boolean selected;

    public ActorIssuer() {
        super();
    }

    public ActorIssuer(AssetIssuerActorRecord record) {
        super(
                record.getPublicKey(),
                record.getName(),
                record.getDapConnectionState(),
                record.getLocationLatitude(),
                record.getLocationLongitude(),
                record.getRegistrationDate(),
                record.getLastConnectionDate(),
                record.getProfileImage(),
                record.getDescription()
        );
    }
}
