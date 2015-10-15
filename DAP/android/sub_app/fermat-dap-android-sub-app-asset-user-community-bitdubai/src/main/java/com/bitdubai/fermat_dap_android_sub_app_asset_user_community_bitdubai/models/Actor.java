package com.bitdubai.fermat_dap_android_sub_app_asset_user_community_bitdubai.models;

import com.bitdubai.fermat_api.layer.all_definition.enums.Genders;
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.AssetUserActorRecord;

/**
 * Created by francisco on 15/10/15.
 */
public class Actor extends AssetUserActorRecord {

    private String thumbnail;

    public Actor(String name, String thumbnail) {
        super(name, null, null, null);
        setThumbnail(thumbnail);
    }

    public Actor(String name, String publicKey, byte[] profileImage, Location location) {
        super(name, publicKey, profileImage, location);
    }

    public Actor(String name, String publicKey, byte[] profileImage, long registrationDate, Genders genders, String age) {
        super(name, publicKey, profileImage, registrationDate, genders, age);
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
