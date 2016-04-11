package org.fermat.fermat_dap_android_sub_app_asset_user_community.models;

import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.AssetUserActorRecord;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;

/**
 * Actor Model
 */
public class Actor extends AssetUserActorRecord
        implements ActorAssetUser {

    public boolean selected;

    public Actor() {
        super();
    }

    public Actor(AssetUserActorRecord record) {
        super(
                record.getActorPublicKey(),
                record.getName(),
                record.getAge(),
                record.getGenders(),
                record.getDapConnectionState(),
                record.getLocationLatitude(),
                record.getLocationLongitude(),
                record.getCryptoAddress(),
                record.getRegistrationDate(),
                record.getLastConnectionDate(),
                record.getBlockchainNetworkType(),
                record.getType(),
                record.getProfileImage()

        );
    }
}
