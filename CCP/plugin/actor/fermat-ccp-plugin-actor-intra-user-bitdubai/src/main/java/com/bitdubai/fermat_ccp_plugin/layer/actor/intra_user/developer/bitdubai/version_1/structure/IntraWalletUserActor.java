package com.bitdubai.fermat_ccp_plugin.layer.actor.intra_user.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.actor_connection.common.enums.ConnectionState;

import java.io.Serializable;

/**
 * The Class <code>IntraWalletUserActor</code>
 * is the implementation of ActorIntraUser interface to provides the methods to consult the information of an Intra Wallet User <p/>
 *
 * Created by Created by natalia on 11/08/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */

public class IntraWalletUserActor implements com.bitdubai.fermat_ccp_api.layer.actor.intra_user.interfaces.IntraWalletUserActor,Serializable {

    private final String          name            ;
    private final String          publicKey       ;
    private final byte[]          profileImage    ;
    private final long            registrationDate;
    private final ConnectionState contactState    ;
    private final String          phrase          ;
    private final String          city          ;
    private final String          country          ;

    public IntraWalletUserActor(final String          name            ,
                                final String          publicKey       ,
                                final byte[]          profileImage    ,
                                final long            registrationDate,
                                final ConnectionState contactState,
                                final String           phrase,
                                final String          city,
                                final String          country) {

        this.name             = name                ;
        this.publicKey        = publicKey           ;
        this.profileImage     = (profileImage!=null) ? profileImage: new byte[0];
        this.registrationDate = registrationDate    ;
        this.contactState     = contactState        ;
        this.phrase           = phrase              ;
        this.country = country;
        this.city  = city;

    }

    @Override
    public String getPublicKey() {
        return this.publicKey;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getPhrase() {
        return this.phrase ;
    }

    @Override
    public long getContactRegistrationDate() {
        return this.registrationDate;
    }

    @Override
    public byte[] getProfileImage() {
        return (profileImage!=null) ? this.profileImage : new byte[0];

    }

    @Override
    public ConnectionState getContactState() {
        return this.contactState;
    }

    @Override
    public String getCity() {
        return city;
    }

    @Override
    public String getCountry() {
        return country;
    }

    @Override
    public String toString() {
        return "IntraWalletUserActor{" +
                "name='" + name + '\'' +
                ", publicKey='" + publicKey + '\'' +
                ", registrationDate=" + registrationDate +
                ", contactState=" + contactState +
                '}';
    }
}
