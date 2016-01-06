package com.bitdubai.fermat_ccp_plugin.layer.module.intra_user_identity.developer.bitdubai.version_1.utils;

import java.io.Serializable;

/**
 * Created by natalia on 05/01/15.
 */
public class IntraUserIdentitySettings implements Serializable {

    private String intrauserloggedinpublickey;


    public IntraUserIdentitySettings(String intrauserloggedinpublickey) {
        this.intrauserloggedinpublickey = intrauserloggedinpublickey;
    }

    public IntraUserIdentitySettings() {

    }


    public String getLoggedInPublicKey() {
        return intrauserloggedinpublickey;
    }


    public void setLoggedInPublicKey(String intrauserloggedinpublickey) {
        this.intrauserloggedinpublickey = intrauserloggedinpublickey;
    }

    @Override
    public String toString() {
        return "intra_user_identity_module_login{" +
                "intra_user_public_key=" + intrauserloggedinpublickey +
                '}';
    }
}
