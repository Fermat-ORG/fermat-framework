package com.bitdubai.fermat_dap_android_sub_app_asset_user_community_bitdubai.models;

import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.AssetUserActorRecord;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;

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
        super(record.getPublicKey(), record.getName(), record.getAge(), record.getGenders(), record.getDapConnectionState(), record.getLocationLatitude(), record.getLocationLongitude(),
                record.getRegistrationDate(), record.getLastConnectionDate(), record.getProfileImage());
    }
}
