package com.bitdubai.fermat_dap_api.layer.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by Nerio on 22/02/16.
 */
public enum DAPPublicKeys implements FermatEnum {
    //TODO: MUY PROVISORIO Para usar en el bradcaster de notificaciones
    DAP_WALLET_ISSUER             ("asset_issuer"),
    DAP_WALLET_USER               ("asset_user"),
    DAP_WALLET_REDEEM             ("redeem_point"),
    DAP_COMMUNITY_ISSUER          ("public_key_dap_issuer_community"),
    DAP_COMMUNITY_USER            ("public_key_dap_user_community"),
    DAP_COMMUNITY_REDEEM          ("public_key_dap_redeem_point_community"),
    DAP_IDENTITY_ISSUER           ("public_key_dap_asset_issuer_identity"),
    DAP_IDENTITY_USER             ("public_key_dap_asset_user_identity"),
    DAP_IDENTITY_REDEEM           ("public_key_dap_redeem_point_identity"),
    DAP_FACTORY                   ("public_key_dap_factory"),

    ;

    private String code;

    DAPPublicKeys(String code){
        this.code = code;
    }

    public static DAPPublicKeys getByCode(String code) throws InvalidParameterException {

        switch (code){

            case "asset_issuer":                            return DAP_WALLET_ISSUER    ;
            case "asset_user":                              return DAP_WALLET_USER      ;
            case "redeem_point":                            return DAP_WALLET_REDEEM    ;
            case "public_key_dap_issuer_community":         return DAP_COMMUNITY_ISSUER ;
            case "public_key_dap_user_community":           return DAP_COMMUNITY_USER   ;
            case "public_key_dap_redeem_point_community":   return DAP_COMMUNITY_REDEEM ;
            case "public_key_dap_asset_issuer_identity":    return DAP_IDENTITY_ISSUER  ;
            case "public_key_dap_asset_user_identity":      return DAP_IDENTITY_USER    ;
            case "public_key_dap_redeem_point_identity":    return DAP_IDENTITY_REDEEM  ;
            case "public_key_dap_factory":                  return DAP_FACTORY          ;
            default:
                throw new InvalidParameterException(
                        "Code Received: " + code,
                        "This code is not valid for the RequestProtocolState enum"
                );
        }
    }

    @Override
    public String getCode() {
        return this.code;
    }
}
