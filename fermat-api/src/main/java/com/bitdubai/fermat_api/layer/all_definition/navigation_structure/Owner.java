package com.bitdubai.fermat_api.layer.all_definition.navigation_structure;

import java.io.Serializable;

/**
 * Created by Matias Furszyfer on 2016.06.08..
 */
//todo: Esta clase será la que se use para saber el owner y pagar las licencias
public class Owner implements Serializable {

    private String ownerAppPublicKey;

    public Owner() {
    }

    public Owner(String ownerAppPublicKey) {
        this.ownerAppPublicKey = ownerAppPublicKey;
    }

    public String getOwnerAppPublicKey() {
        return ownerAppPublicKey;
    }

    public void setOwnerAppPublicKey(String ownerAppPublicKey) {
        this.ownerAppPublicKey = ownerAppPublicKey;
    }

    @Override
    public String toString() {
        return new StringBuilder().append("ownerAppPublicKey='").append(ownerAppPublicKey).toString();
    }
}
