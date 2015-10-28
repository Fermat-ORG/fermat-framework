package com.bitdubai.fermat_dap_android_sub_app_asset_user_community_bitdubai.models;

import com.bitdubai.fermat_api.layer.all_definition.enums.ConnectionState;
import com.bitdubai.fermat_api.layer.all_definition.enums.Genders;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.AssetUserActorRecord;

/**
 * Actor Model
 */
public class Actor extends AssetUserActorRecord {


    public boolean selected;

    public Actor(String assetUserActorPublicKey, String assetUserActorName, byte[] assetUserActorprofileImage, Double locationLatitude, Double locationLongitude) {
        super(assetUserActorPublicKey, assetUserActorName, assetUserActorprofileImage, locationLatitude, locationLongitude);
    }

    public Actor(String publicKey, String name, byte[] profileImage, Location location) {
        super(publicKey, name, profileImage, location);
    }

    public Actor(String publicKey, String name, String age, Genders genders, ConnectionState connectionState, Double locationLatitude, Double locationLongitude, CryptoAddress cryptoAddress, Long registrationDate, Long lastConnectionDate, byte[] profileImage) {
        super(publicKey, name, age, genders, connectionState, locationLatitude, locationLongitude, cryptoAddress, registrationDate, lastConnectionDate, profileImage);
    }
}
