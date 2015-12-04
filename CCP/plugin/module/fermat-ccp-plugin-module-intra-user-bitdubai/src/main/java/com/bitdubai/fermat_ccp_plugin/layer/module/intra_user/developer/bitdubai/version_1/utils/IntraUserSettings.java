package com.bitdubai.fermat_ccp_plugin.layer.module.intra_user.developer.bitdubai.version_1.utils;

import java.io.Serializable;

/**
 * Created by natalia on 25/08/15.
 */
public class IntraUserSettings implements Serializable {

    private String intrauserloggedinpublickey;


    public IntraUserSettings(String intrauserloggedinpublickey) {
        this.intrauserloggedinpublickey = intrauserloggedinpublickey;
    }

    public IntraUserSettings() {

    }


    public String getLoggedInPublicKey() {
        return intrauserloggedinpublickey;
    }


    public void setLoggedInPublicKey(String intrauserloggedinpublickey) {
        this.intrauserloggedinpublickey = intrauserloggedinpublickey;
    }

    @Override
    public String toString() {
        return "intra_user_module_login{" +
                "intra_user_public_key=" + intrauserloggedinpublickey +
                '}';
    }
}
