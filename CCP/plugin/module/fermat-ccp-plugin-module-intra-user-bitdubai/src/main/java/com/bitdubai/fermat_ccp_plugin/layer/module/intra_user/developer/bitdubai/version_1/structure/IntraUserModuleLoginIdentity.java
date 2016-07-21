package com.bitdubai.fermat_ccp_plugin.layer.module.intra_user.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_ccp_api.all_definition.enums.Frequency;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserLoginIdentity;

import java.io.Serializable;

/**
 * The class <code>com.bitdubai.fermat_dmp_plugin.layer.module.intra_user.developer.bitdubai.version_1.structure.IntraUserModuleLoginIdentity</code>
 * is the implementation of IntraUserLoginIdentity interface.
 * And provides the methods to get the information of an identity a user can use to log in..
 *
 * Created by natalia on 11/08/15.
 */
public class IntraUserModuleLoginIdentity implements IntraUserLoginIdentity,Serializable {

    private String alias;
    private String publicKey;
    private byte[] profileImage;
    private long accurancy;
    private Frequency frequency;
    private Location location;


    /**
     * Constructor
     */

    public IntraUserModuleLoginIdentity(String alias,String publicKey,byte[] profileImage,long accurancy,
           Frequency frequency,
            Location location)
    {
        this.alias = alias;
        this.publicKey = publicKey;
        this.profileImage = (profileImage!=null) ? profileImage.clone() : null;
        this.accurancy = accurancy;
        this.frequency = frequency;
        this.location = location;
    }
    /**
     * That method returns the alias of the intra user identity
     *
     * @return the alias of the intra user
     */
    @Override
    public String getAlias() {
        return this.alias;
    }

    /**
     * That method returns the public key of the intra user identity
     *
     * @return the public key of the intra user
     */
    @Override
    public String getPublicKey() {
        return this.publicKey;
    }


    /**
     * That method returns the profile image of the intra user identity
     *
     * @return the profile image of the intra user
     */
    @Override
    public byte[] getProfileImage() {
        return this.profileImage.clone();
    }

    @Override
    public long getAccuracy() {
        return this.accurancy;
    }

    @Override
    public Frequency getFrequency() {
        return frequency;
    }

    @Override
    public Location getLocation() {
        return location;
    }
}
