package com.bitdubai.fermat_ccp_plugin.layer.module.intra_user_identity.developer.bitdubai.version_1.structure;


import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_ccp_api.layer.actor.Actor;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user_identity.interfaces.IntraUserModuleIdentity;

import java.util.UUID;

/**
 * The class <code>com.bitdubai.fermat_ccp_plugin.layer.module.intra_user_identity.developer.bitdubai.version_1.structure.IntraUserIdentityModule</code>
 * is the implementation of IntraUserModuleIdentity interface.
 * And provides the method to extract information about an intra user identity.
 *
 * Created by natalia on 05/01/16.
 */
public class IntraUserIdentityModule implements IntraUserModuleIdentity {

    private String alias;
    private String phrase;
    private String publicKey;
    private byte[] image;
    private Actors actorType;

    /**
     * Constructor
     */
    public IntraUserIdentityModule(String alias, String phrase,String publicKey, byte[] image,Actors actorType) {
        this.alias = alias;
        this.phrase = phrase;
        this.publicKey = publicKey;
        this.image = image;

    }



    @Override
    public String getPhrase() {
        return this.phrase;
    }

    @Override
    public Actors getActorType() {
        return actorType;
    }

    @Override
    public String getAlias() {
        return this.alias;
    }

    @Override
    public byte[] getImage() {
        return this.image;
    }

    @Override
    public String getPublicKey() {
        return  this.publicKey;
    }
}
