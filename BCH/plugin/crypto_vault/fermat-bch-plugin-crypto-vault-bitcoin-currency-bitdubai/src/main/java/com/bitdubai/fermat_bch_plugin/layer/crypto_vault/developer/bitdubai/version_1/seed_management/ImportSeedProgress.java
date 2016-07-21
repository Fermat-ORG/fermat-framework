package com.bitdubai.fermat_bch_plugin.layer.crypto_vault.developer.bitdubai.version_1.seed_management;

/**
 * Created by rodrigo on 7/14/16.
 */
public enum ImportSeedProgress {
    NOT_STARTED("NOT_STARTED"),
    IN_PROGRESS("IN_PROGRESS"),
    COMPLETED("COMPLETED");

    private String code;

    public String getCode(){
        return this.code;
    }

     ImportSeedProgress(String code){
        this.code = code;
    }


    public static ImportSeedProgress getByCode(String code){
        switch (code){
            case "NOT_STARTED":
                return NOT_STARTED;
            case "IN_PROGRESS":
                return IN_PROGRESS;
            case "COMPLETED":
                return COMPLETED;
            default:
                return NOT_STARTED;
        }
    }
}
