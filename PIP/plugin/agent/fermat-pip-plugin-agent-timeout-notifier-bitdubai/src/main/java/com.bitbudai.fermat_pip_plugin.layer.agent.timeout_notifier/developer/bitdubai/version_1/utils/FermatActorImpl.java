package com.bitbudai.fermat_pip_plugin.layer.agent.timeout_notifier.developer.bitdubai.version_1.utils;

import com.bitdubai.fermat_api.layer.actor.FermatActor;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;

/**
 * Created by rodrigo on 3/28/16.
 */
public class FermatActorImpl implements FermatActor {
    String publicKey;
    String name;
    Actors type;

    public FermatActorImpl() {
    }

    @Override
    public String getPublicKey() {
        return publicKey;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Actors getType() {
        return type;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(Actors type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return name + " of type " + type.getCode();
    }
}
