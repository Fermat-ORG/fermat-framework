package com.bitdubai.fermat_api.layer.all_definition.navigation_structure;

/**
 * Created by Matias Furszyfer on 2016.06.08..
 */
//todo: Esta clase será la que se use para saber el owner y pagar las licencias
public class Owner {

    private String ownerAppPublicKey;

    public Owner() {
    }

    public String getOwnerAppPublicKey() {
        return ownerAppPublicKey;
    }

    public void setOwnerAppPublicKey(String ownerAppPublicKey) {
        this.ownerAppPublicKey = ownerAppPublicKey;
    }

    @Override
    public String toString() {
        return "ownerAppPublicKey='" + ownerAppPublicKey;
    }
}
