package org.fermat.fermat_dap_api.layer.dap_middleware.dap_asset_factory.enums;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by franklin on 17/09/15.
 */
public enum AssetBehavior {
    REGENERATION_ASSET("REGA"),
    RECUPERATION_BITCOINS("RECA");

    private String code;

    AssetBehavior(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

    public static AssetBehavior getByCode(String code) throws InvalidParameterException {
        switch (code) {
            case "REGA":
                return AssetBehavior.REGENERATION_ASSET;
            case "RECA":
                return AssetBehavior.RECUPERATION_BITCOINS;
            default:
                throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This Code Is Not Valid for the AssetBehavior enum.");
        }
    }
}
