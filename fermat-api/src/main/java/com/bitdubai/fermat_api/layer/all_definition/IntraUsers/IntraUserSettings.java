package com.bitdubai.fermat_api.layer.all_definition.IntraUsers;

import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Layout;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Resource;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.all_definition.util.VersionCompatibility;

import java.io.Serializable;
import java.util.Map;
import java.util.UUID;

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
