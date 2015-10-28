package com.bitdubai.fermat_ccp_plugin.layer.actor.intra_wallet_user.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.ConnectionState;
import com.bitdubai.fermat_ccp_api.layer.actor.intra_wallet_user.interfaces.IntraWalletUser;

/**
 * The Class <code>IntraWalletUserActor</code>
 * is the implementation of ActorIntraUser interface to provides the methods to consult the information of an Intra Wallet User <p/>
 *
 * Created by Created by natalia on 11/08/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */

public class IntraWalletUserActor implements IntraWalletUser {

    private final String name;
    private final String publicKey;
    private final byte[] profileImage;
    private final long registrationDate;
    private final ConnectionState contactState    ;

    public IntraWalletUserActor(final String name,
                                final String publicKey,
                                final byte[] profileImage,
                                final long registrationDate,
                                final ConnectionState contactState    ) {

        this.name             = name                ;
        this.publicKey        = publicKey           ;
        this.profileImage     = (profileImage!=null) ? profileImage.clone(): null;
        this.registrationDate = registrationDate    ;
        this.contactState     = contactState        ;

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
    public long getContactRegistrationDate() {
        return this.registrationDate;
    }

    @Override
    public byte[] getProfileImage() {
        return (profileImage!=null) ? this.profileImage.clone() : null;

    }

    @Override
    public ConnectionState getContactState() {
        return this.contactState;
    }

}
