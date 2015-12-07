package com.bitdubai.fermat_dap_android_sub_app_redeem_point_community_bitdubai.models;

import com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.RedeemPointActorRecord;
import com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.interfaces.ActorAssetRedeemPoint;

/**
 * Actor Model
 */
public class Actor extends RedeemPointActorRecord
        implements ActorAssetRedeemPoint {

    public boolean selected;

    public Actor() {
        super();
    }

    public Actor(RedeemPointActorRecord record) {
        super(
                record.getActorPublicKey(),
                record.getName(),
                record.getDapConnectionState(),
                record.getLocationLatitude(),
                record.getLocationLongitude(),
                record.getRegistrationDate(),
                record.getLastConnectionDate(),
                record.getProfileImage()
        );
    }

}
