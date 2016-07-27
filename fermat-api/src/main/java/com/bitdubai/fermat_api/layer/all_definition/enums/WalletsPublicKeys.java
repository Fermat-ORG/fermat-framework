package com.bitdubai.fermat_api.layer.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by Nerio on 12/03/16.
 */
public enum WalletsPublicKeys implements FermatEnum {

    //TODO BNK Platform
    BNK_BANKING_WALLET("banking_wallet"),
    //TODO CSH Platform
    CSH_MONEY_WALLET("cash_wallet"),
    //TODO CBP Platform
    CBP_CRYPTO_BROKER_WALLET("crypto_broker_wallet"),
    CBP_CRYPTO_CUSTOMER_WALLET("crypto_customer_wallet"),
    //TODO CCP Platform
    CCP_REFERENCE_WALLET("reference_wallet"),
    CCP_FERMAT_WALLET("fermat_wallet"),
    CCP_LOSS_PROTECTED_WALLET("loss_protected_wallet"),
    //TODO CWP Platform

    //TODO DAP Platform
    DAP_ISSUER_WALLET("asset_issuer"),
    DAP_USER_WALLET("asset_user"),
    DAP_REDEEM_WALLET("redeem_point"),

    TKY_FAN_WALLET("fan_wallet");


    private String code;

    WalletsPublicKeys(String code) {
        this.code = code;
    }

    public static WalletsPublicKeys getByCode(String code) throws InvalidParameterException {

        switch (code) {
            //TODO BNK Platform
            case "banking_wallet":
                return BNK_BANKING_WALLET;
            //TODO CSH Platform
            case "cash_wallet":
                return CSH_MONEY_WALLET;
            //TODO CBP Platform
            case "crypto_broker_wallet":
                return CBP_CRYPTO_BROKER_WALLET;
            case "crypto_customer_wallet":
                return CBP_CRYPTO_CUSTOMER_WALLET;
            //TODO CCP Platform
            case "reference_wallet":
                return CCP_REFERENCE_WALLET;
            case "loss_protected_wallet":
                return CCP_LOSS_PROTECTED_WALLET;
            case "fermat_wallet":
                return CCP_FERMAT_WALLET;
            //TODO DAP Platform
            case "asset_issuer":
                return DAP_ISSUER_WALLET;
            case "asset_user":
                return DAP_USER_WALLET;
            case "redeem_point":
                return DAP_REDEEM_WALLET;
            case "fan_wallet":
                return TKY_FAN_WALLET;
            default:
                throw new InvalidParameterException(
                        new StringBuilder().append("Code Received: ").append(code).toString(),
                        "This code is not valid for the RequestProtocolState enum"
                );
        }
    }

    @Override
    public String getCode() {
        return this.code;
    }
}
