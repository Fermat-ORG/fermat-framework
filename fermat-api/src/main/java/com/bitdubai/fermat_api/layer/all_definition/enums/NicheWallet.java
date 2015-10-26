package com.bitdubai.fermat_api.layer.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatWalletEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.CallToGetByCodeOnNONEException;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by eze on 2015.07.19..
 * Modified by Leon Acosta (laion.cj91@gmail.com) on 23/09/2015.
 */
public enum NicheWallet implements FermatWalletEnum {
    /**
     * The value NONE is used to store the information of no wallet
     * <p/>
     * Please for doing the code more readable, keep the elements of the enum ordered.
     */
    NONE           ("NONE", null),
    UNKNOWN_WALLET ("UNKW", ReferenceWallet.BASIC_WALLET_BITCOIN_WALLET);

    private String code;
    private ReferenceWallet referenceWallet;

    NicheWallet(String code, ReferenceWallet referenceWallet) {
        this.code = code;
        this.referenceWallet = referenceWallet;
    }

    public static NicheWallet getByCode(String code) throws InvalidParameterException, CallToGetByCodeOnNONEException {
        switch (code) {
            case "NONE": throw new CallToGetByCodeOnNONEException("method getByCode called by a NONE wallet", null, "", "");
            case "UNKW": return UNKNOWN_WALLET;
            default:
                throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This Code Is Not Valid for the NicheWallet enum");
        }
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public ReferenceWallet getReferenceWallet() {
        return this.referenceWallet;
    }

}
